package com.example.shoplocator.buissines.shopDetail;

import com.example.shoplocator.data.db.shops.IShopsDBService;
import com.example.shoplocator.data.db.users.IUsersDBService;
import com.example.shoplocator.data.fakeFdb.shops.FakeShopsListGenerator;
import com.example.shoplocator.data.fakeFdb.users.FakeUsersListGenerator;
import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.firebaseDb.users.IUsersFDBService;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.data.repsitory.shops.ShopsRepository;
import com.example.shoplocator.data.repsitory.users.UsersRepository;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.util.rx.validation.RxValidation;

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

public class ShopDetailInteractorTest {

    //shops
    private IShopsDBService mockedShopsDBService;
    private IShopsFDBService mockedShopsFDBService;
    private ShopsRepository shopsRepository;

    //users
    private IUsersDBService mockedUsersDBService;
    private IUsersFDBService mockedUsersFDBService;
    private UsersRepository usersRepository;

    private ShopDetailInteractor shopDetailInteractor;

    @Before
    public void beforeEachTest() {
        RxValidation rxValidation = new RxValidation();
        //setup shops repository
        mockedShopsFDBService = mock(IShopsFDBService.class);
        mockedShopsDBService = mock(IShopsDBService.class);
        shopsRepository = new ShopsRepository(mockedShopsFDBService, mockedShopsDBService, rxValidation);
        //setup users repository
        mockedUsersDBService = mock(IUsersDBService.class);
        mockedUsersFDBService = mock(IUsersFDBService.class);
        usersRepository = new UsersRepository(mockedUsersFDBService, mockedUsersDBService, rxValidation);
        //setup interactor
        shopDetailInteractor = new ShopDetailInteractor(shopsRepository, usersRepository);
    }

    @Test
    public void getShopById_Success_test() {
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
        TestSubscriber<ShopModel> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopDetailInteractor.getShopById(targetShop.getId()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check emited data
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        ShopModel formModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(formModel.getImageUrl()).isEqualTo(targetShop.getImageUrl());
        assertThat(formModel.getName()).isEqualTo(targetShop.getName());
        assertThat(formModel.getCoordinate().getX()).isEqualTo(targetShop.getCoordinateX());
        assertThat(formModel.getCoordinate().getY()).isEqualTo(targetShop.getCoordinateY());
        assertThat(formModel.getOwner().getId()).isEqualTo(targetUser.getId());
        assertThat(formModel.getOwner().getName()).isEqualTo(targetUser.getName());
    }

    @Test
    public void getShopById_Error_test() {
        //setup testable data
        Throwable fetchFromRemoteDbError = new RuntimeException("Fetch from Remote database error.");
        Throwable fetchFromLocalDbError = new RuntimeException("Fetch from Local database error.");
        //mock shops
        when(mockedShopsFDBService.getShopsById((any()))).thenReturn(Single.error(fetchFromRemoteDbError));
        when(mockedShopsDBService.getShopById(any())).thenReturn(Single.error(fetchFromLocalDbError));
        //create TestSubscriber
        TestSubscriber<ShopModel> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopDetailInteractor.getShopById("1").subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //test result
        testSubscriber.assertError(fetchFromLocalDbError);
    }

    @Test
    public void deleteShopById_Success_test() {
        //mock shops
        when(mockedShopsDBService.deleteShopsByIds(any(Collection.class))).thenReturn(Single.just(null));
        when(mockedShopsFDBService.deleteShopsByIds(any(Collection.class))).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<Object> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopDetailInteractor.deleteShopById("1").subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check emitted data
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }

    @Test
    public void deleteShopById_Error_test() {
        //setup testable data
        Throwable throwable = new RuntimeException("Not found in database");
        //mock shops
        when(mockedShopsFDBService.deleteShopsByIds(any(Collection.class))).thenReturn(Single.error(throwable));
        //create TestSubscriber
        TestSubscriber<Object> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopDetailInteractor.deleteShopById("1").subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check emitted data
        testSubscriber.assertError(throwable);
    }

}
