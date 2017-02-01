package com.example.shoplocator.buissines.shopList;

import com.example.shoplocator.buissines.shopsList.ShopsListInteractor;
import com.example.shoplocator.buissines.shopsList.listTransformation.ChangeItemsCommand;
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
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;
import com.example.shoplocator.util.rx.validation.RxValidation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Single;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class ShopsListInteractorTest {

    //shops
    private IShopsDBService mockedShopsDBService;
    private IShopsFDBService mockedShopsFDBService;
    private ShopsRepository shopsRepository;

    //users
    private IUsersDBService mockedUsersDBService;
    private IUsersFDBService mockedUsersFDBService;
    private UsersRepository usersRepository;

    private ShopsListInteractor shopsListInteractor;

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
        shopsListInteractor = new ShopsListInteractor(shopsRepository, usersRepository);
    }

    @Test
    public void getCheckableShops_Success_test() {
        //prepare data
        List<ShopDbModel> shops = new FakeShopsListGenerator().getNewList(20);
        List<UserDbModel> user = new FakeUsersListGenerator().getNewList();
        //mock shops
        when(mockedShopsFDBService.getShops()).thenReturn(Single.just(shops));
        //mock users
        when(mockedUsersFDBService.getUsers()).thenReturn(Single.just(user));
        when(mockedUsersDBService.setUsers(any(List.class))).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<List<SelectableShopModel>> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopsListInteractor.getCheckableShops().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check emited data
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        List<SelectableShopModel> selectableShops = testSubscriber.getOnNextEvents().get(0);
        assertThat(selectableShops.size()).isEqualTo(20);
    }

    @Test
    public void getCheckableShops_Error_test() {
        //prepare data
        Throwable throwable = new RuntimeException("No internet connection.");
        //mock shops
        when(mockedShopsFDBService.getShops()).thenReturn(Single.error(throwable));
        when(mockedShopsDBService.getShops()).thenReturn(Single.just(null));
        //mock users
        when(mockedUsersFDBService.getUsers()).thenReturn(Single.error(throwable));
        when(mockedUsersDBService.getUsers()).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<List<SelectableShopModel>> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopsListInteractor.getCheckableShops().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check emited data
        assertThat(testSubscriber.getOnErrorEvents().get(0).getMessage()).endsWith("Shops list not exist in db.");
    }

    @Test
    public void removeSelectedShops_test() {
        //prepare data
        List<SelectableShopModel> shops = prepareSelectableShops();
        shops.get(1).setSelected(true);
        shops.get(2).setSelected(true);
        SelectableShopModel shop1 = shops.get(0);
        //mock shops
        when(mockedShopsFDBService.deleteShopsByIds(any(Collection.class))).thenReturn(Single.just(null));
        when(mockedShopsDBService.deleteShopsByIds(any(Collection.class))).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<ChangeItemsCommand> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopsListInteractor.removeSelectedShops(shops).subscribe(testSubscriber);
        //check emited data
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        ChangeItemsCommand removeShopsCommand = testSubscriber.getOnNextEvents().get(0);
        assertThat(shops.size()).isEqualTo(3);
        final int [] removedItemsCount = new int[1];
        removedItemsCount[0] = 0;
        removeShopsCommand.executeWithListener(position -> removedItemsCount[0]++);
        assertThat(removedItemsCount[0]).isEqualTo(2);
        assertThat(shops.size()).isEqualTo(1);
        assertThat(shops.contains(shop1)).isTrue();
    }

    private List<SelectableShopModel> prepareSelectableShops() {
        SelectableShopModel shop1 = new SelectableShopModel("1", "shop1", "http://1", new ShopCoordinate(50, 50), new UserModel("1", "user1"));
        SelectableShopModel shop2 = new SelectableShopModel("2", "shop2", "http://2", new ShopCoordinate(50, 50), new UserModel("2", "user2"));
        SelectableShopModel shop3 = new SelectableShopModel("3", "shop3", "http://3", new ShopCoordinate(50, 50), new UserModel("3", "user3"));
        List<SelectableShopModel> shops = new ArrayList<>();
        shops.add(shop1);
        shops.add(shop2);
        shops.add(shop3);
        return shops;
    }

    @Test
    public void deselectSelectedShops_test() {
        //prepare data
        List<SelectableShopModel> shops = prepareSelectableShops();
        shops.get(1).setSelected(true);
        shops.get(2).setSelected(true);
        //perform testable action
        ChangeItemsCommand command = shopsListInteractor.deselectSelectedShops(shops);
        //check result
        assertThat(shops.get(0).isSelected()).isFalse();
        assertThat(shops.get(1).isSelected()).isTrue();
        assertThat(shops.get(2).isSelected()).isTrue();
        final int [] changedItemsCount = new int[1];
        changedItemsCount[0] = 0;
        command.executeWithListener(position -> {
            changedItemsCount[0]++;
        });
        assertThat(changedItemsCount[0]).isEqualTo(2);
        assertThat(shops.get(0).isSelected()).isFalse();
        assertThat(shops.get(1).isSelected()).isFalse();
        assertThat(shops.get(2).isSelected()).isFalse();
    }

    @Test
    public void addShopById_test() {
        //prepare data
        List<SelectableShopModel> shops = prepareSelectableShops();
        String shopId = "1";
        String userId = "1";
        ShopDbModel shopDbModel = new ShopDbModel(shopId, "shop1", "http://", 50, 50, userId);
        UserDbModel userDbModel = new UserDbModel("1", "user1");
        //mock shops
        when(mockedShopsFDBService.getShopsById(shopId)).thenReturn(Single.just(shopDbModel));
        when(mockedShopsDBService.addShops(any(Collection.class))).thenReturn(Single.just(null));
        //mock users
        when(mockedUsersFDBService.getUserById(userId)).thenReturn(Single.just(userDbModel));
        when(mockedUsersDBService.addUser(userDbModel)).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<ChangeItemsCommand> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopsListInteractor.addShopById(shops, shopId).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check result
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        ChangeItemsCommand addShopCommand = testSubscriber.getOnNextEvents().get(0);
        assertThat(shops.size()).isEqualTo(3);
        final int [] changedItemsCount = new int[1];
        changedItemsCount[0] = 0;
        addShopCommand.executeWithListener(position -> changedItemsCount[0]++);
        assertThat(changedItemsCount[0]).isEqualTo(1);
        assertThat(shops.size()).isEqualTo(4);
    }

    @Test
    public void getCheckableShopById_test() {
        //prepare data
        List<SelectableShopModel> shops = prepareSelectableShops();
        String shopId = "1";
        String userId = "1";
        ShopDbModel shopDbModel = new ShopDbModel(shopId, "new shop1", "http://", 50, 50, userId);
        UserDbModel userDbModel = new UserDbModel("1", "user1");
        //mock shops
        when(mockedShopsFDBService.getShopsById(shopId)).thenReturn(Single.just(shopDbModel));
        when(mockedShopsDBService.addShops(any(Collection.class))).thenReturn(Single.just(null));
        //mock users
        when(mockedUsersFDBService.getUserById(userId)).thenReturn(Single.just(userDbModel));
        when(mockedUsersDBService.addUser(userDbModel)).thenReturn(Single.just(null));
        //create TestSubscriber
        TestSubscriber<ChangeItemsCommand> testSubscriber = TestSubscriber.create();
        //perform testable action
        shopsListInteractor.updateShopById(shops, shopId).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //check result
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        ChangeItemsCommand addShopCommand = testSubscriber.getOnNextEvents().get(0);
        assertThat(shops.size()).isEqualTo(3);
        final int [] changedItemsCount = new int[1];
        changedItemsCount[0] = 0;
        addShopCommand.executeWithListener(position -> changedItemsCount[0]++);
        assertThat(changedItemsCount[0]).isEqualTo(1);
        assertThat(shops.size()).isEqualTo(3);
        assertThat(shops.get(0).getName()).isEqualTo("new shop1");
    }

}
