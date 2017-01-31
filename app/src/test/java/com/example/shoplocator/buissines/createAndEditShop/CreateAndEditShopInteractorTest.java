package com.example.shoplocator.buissines.createAndEditShop;

import com.example.shoplocator.buissines.createAndEditShop.exception.ShopFormIsInvalid;
import com.example.shoplocator.buissines.createAndEditShop.validation.ShopFormValidation;
import com.example.shoplocator.data.db.shops.IShopsDBService;
import com.example.shoplocator.data.db.users.IUsersDBService;
import com.example.shoplocator.data.fakeFdb.shops.FakeShopsListGenerator;
import com.example.shoplocator.data.fakeFdb.users.FakeUsersListGenerator;
import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.firebaseDb.users.IUsersFDBService;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.ShopFormDbModel;
import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.data.repsitory.shops.ShopsRepository;
import com.example.shoplocator.data.repsitory.users.UsersRepository;
import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;
import com.example.shoplocator.ui.model.UserModel;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import rx.Single;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class CreateAndEditShopInteractorTest {

    //shops
    private IShopsDBService mockedShopsDBService;
    private IShopsFDBService mockedShopsFDBService;
    private ShopsRepository shopsRepository;

    //users
    private IUsersDBService mockedUsersDBService;
    private IUsersFDBService mockedUsersFDBService;
    private UsersRepository usersRepository;

    //validation
    private ShopFormValidation shopFormValidation;

    private CreateAndEditShopInterator createAndEditShopInterator;

    @Before
    public void beforeEachTest() {
        //setup shops repository
        mockedShopsFDBService = mock(IShopsFDBService.class);
        mockedShopsDBService = mock(IShopsDBService.class);
        shopsRepository = new ShopsRepository(mockedShopsFDBService, mockedShopsDBService, rxValidation);
        //setup users repository
        mockedUsersDBService = mock(IUsersDBService.class);
        mockedUsersFDBService = mock(IUsersFDBService.class);
        usersRepository = new UsersRepository(mockedUsersFDBService, mockedUsersDBService, rxValidation);
        //mock validation messages
        shopFormValidation = new ShopFormValidation(new ValidationUtilTest());
        //setup interactor
        createAndEditShopInterator = new CreateAndEditShopInterator(shopsRepository, usersRepository, shopFormValidation);
    }

    @Test
    public void getCheckabeUsers_Success_test() {
        //setup testable data
        List<UserDbModel> usersFromRemoteServer = new FakeUsersListGenerator().getNewList();
        //mock users
        when(mockedUsersFDBService.getUsers()).thenReturn(Single.just(usersFromRemoteServer));
        when(mockedUsersDBService.setUsers(usersFromRemoteServer)).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<List<CheckableUserModel>> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.getCheckableUsers().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        //check emited data
        List<CheckableUserModel> checkableUserModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(checkableUserModel.size()).isEqualTo(FakeUsersListGenerator.USERS_COUNT);
        for (int i=1; i<FakeUsersListGenerator.USERS_COUNT; i++) {
            CheckableUserModel user = checkableUserModel.get(i-1);
            assertThat(user.getName()).isEqualTo(FakeUsersListGenerator.USER_NAME + i);
            assertThat(user.getId()).isEqualTo(String.valueOf(i));
        }
    }

    @Test
    public void getCheckableUsers_Error_test() {
        //setup testable data
        Throwable fetchFromRemoteDbError = new RuntimeException("Fetch from Remote database error.");
        Throwable fetchFromLocalDbError = new RuntimeException("Fetch from Local database error.");
        //mock users
        when(mockedUsersFDBService.getUsers()).thenReturn(Single.error(fetchFromRemoteDbError));
        when(mockedUsersDBService.getUsers()).thenReturn(Single.error(fetchFromLocalDbError));
        //create TestSubscriber
        TestSubscriber<List<CheckableUserModel>> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.getCheckableUsers().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check emited data
        testSubscriber.assertError(fetchFromLocalDbError);
    }

    @Test
    public void getShopFormById_Success_test() {
        //setup testable data
        List<ShopDbModel> shops = new FakeShopsListGenerator().getNewList(2);
        List<UserDbModel> users = new FakeUsersListGenerator().getNewList();
        ShopDbModel targetShop = shops.get(0);
        UserDbModel targetUser = null;
        for (UserDbModel user : users) {
            if (user.getId().equals(targetShop.getOwnerId())) {
                targetUser = user;
            }
        }
        assertThat(targetUser).isNotNull();
        //mock shops
        when(mockedShopsFDBService.getShopsById(targetShop.getId())).thenReturn(Single.just(targetShop));
        when(mockedShopsDBService.addShops((Collection<ShopDbModel>)notNull())).thenReturn(Single.just(null));
        //mock users
        when(mockedUsersFDBService.getUserById(targetUser.getId())).thenReturn(Single.just(targetUser));
        when(mockedUsersDBService.addUser(targetUser)).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<ShopFormModel> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.getShopFormById(targetShop.getId()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        //check emited data
        ShopFormModel formModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(formModel.getImageUrl()).isEqualTo(targetShop.getImageUrl());
        assertThat(formModel.getName()).isEqualTo(targetShop.getName());
        assertThat(formModel.getPosX()).isEqualTo(String.valueOf(targetShop.getCoordinateX()));
        assertThat(formModel.getPosY()).isEqualTo(String.valueOf(targetShop.getCoordinateY()));
        assertThat(formModel.getUserModel().getId()).isEqualTo(targetUser.getId());
        assertThat(formModel.getUserModel().getName()).isEqualTo(targetUser.getName());
    }

    @Test
    public void getShopFormById_Error_test() {
        //setup testable data
        Throwable fetchFromRemoteDbError = new RuntimeException("Fetch from Remote database error.");
        Throwable fetchFromLocalDbError = new RuntimeException("Fetch from Local database error.");
        //mock shops
        when(mockedShopsFDBService.getShopsById((any()))).thenReturn(Single.error(fetchFromRemoteDbError));
        when(mockedShopsDBService.getShopById(any())).thenReturn(Single.error(fetchFromLocalDbError));
        //create TestSubscriber
        TestSubscriber<ShopFormModel> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.getShopFormById("1").subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        testSubscriber.assertError(fetchFromLocalDbError);
    }

    @Test
    public void addShopAngGetId_Success_test() {
        //setup testable data
        UserModel userModel = new UserModel("1", "User Name");
        ShopFormModel formModel = new ShopFormModel("name", "http:image", userModel, "50", "50");
        ShopDbModel shopDbModel = new ShopDbModel("1", "name", "http:image", 50, 50, "1");
        //mock
        when(mockedShopsFDBService.addShop(any())).thenReturn(Single.just(shopDbModel));
        when(mockedShopsDBService.addShop(any())).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<String> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.addShopAngGetId(formModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(shopDbModel.getId());
    }

    @Test
    public void addShopAngGetId_Error_test() {
        //setup testable data
        UserModel userModel = new UserModel("1", "User Name");
        ShopFormModel formModel = new ShopFormModel("name", "http:image", userModel, "50", "50");
        Throwable throwable = new RuntimeException("No internet connection.");
        //mock
        when(mockedShopsFDBService.addShop(any())).thenReturn(Single.error(throwable));
        //create TestSubscriber
        TestSubscriber<String> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.addShopAngGetId(formModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        testSubscriber.assertError(throwable);
    }

    @Test
    public void updateShopAndGetId_Success_test() {
        //setup testable data
        UserModel userModel = new UserModel("1", "User Name");
        ShopFormModel formModel = new ShopFormModel("name", "http:image", userModel, "50", "50");
        ShopDbModel shopDbModel = new ShopDbModel("1", "name", "http:image", 50, 50, "1");
        //mock
        when(mockedShopsFDBService.updateShop(any(String.class), any(ShopFormDbModel.class))).thenReturn(Single.just(shopDbModel));
        when(mockedShopsDBService.deleteShopsByIds(any())).thenReturn(Single.just(null));
        when(mockedShopsDBService.addShop(any())).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<String> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.updateShopAndGetId(shopDbModel.getId(), formModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(shopDbModel.getId());
    }

    @Test
    public void updateShopAndGetId_Error_test() {
        //setup testable data
        UserModel userModel = new UserModel("1", "User Name");
        ShopFormModel formModel = new ShopFormModel("name", "http:image", userModel, "50", "50");
        ShopDbModel shopDbModel = new ShopDbModel("1", "name", "http:image", 50, 50, "1");
        Throwable throwable = new RuntimeException("No internet connection.");
        //mock
        when(mockedShopsFDBService.updateShop(any(String.class), any(ShopFormDbModel.class))).thenReturn(Single.error(throwable));
        //create TestSubscriber
        TestSubscriber<String> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.updateShopAndGetId(shopDbModel.getId(), formModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        testSubscriber.assertError(throwable);
    }

    @Test
    public void validateForm_Valid_form_test() {
        //prepare data
        UserModel userModel = new UserModel("1", "User name");
        ShopFormModel formModel = new ShopFormModel("Valid name", "http://valid.image.url", userModel, "50", "50");
        //create TestSubscription
        TestSubscriber<ShopFormModel> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.validateForm(formModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(formModel);
    }

    @Test
    public void validateForm_Invalid_form_test() {
        //prepare data
        ShopFormModel formModel = new ShopFormModel("", "invalid.image.url", null, "", "");
        //create TestSubscription
        TestSubscriber<ShopFormModel> testSubscriber = TestSubscriber.create();
        //perform testable action
        createAndEditShopInterator.validateForm(formModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        Throwable throwable = testSubscriber.getOnErrorEvents().get(0);
        assertThat(throwable).isInstanceOf(ShopFormIsInvalid.class);
        ShopFormIsInvalid shopFormIsInvalidError = (ShopFormIsInvalid) throwable;
        assertThat(shopFormIsInvalidError.getInvalidFields().size()).isEqualTo(5);
    }


}
