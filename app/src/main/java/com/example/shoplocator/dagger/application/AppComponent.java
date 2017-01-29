package com.example.shoplocator.dagger.application;

import com.example.shoplocator.dagger.createAndEditShop.CreateAndEditShopComponent;
import com.example.shoplocator.dagger.createAndEditShop.CreateAndEditShopModule;
import com.example.shoplocator.dagger.shopDetail.ShopDetailComponent;
import com.example.shoplocator.dagger.shopDetail.ShopDetailModule;
import com.example.shoplocator.dagger.shopsList.ShopsComponent;
import com.example.shoplocator.dagger.shopsList.ShopsModule;
import com.example.shoplocator.dagger.shopsMap.ShopsMapComponent;
import com.example.shoplocator.dagger.shopsMap.ShopsMapModule;
import com.example.shoplocator.dagger.userDetail.UserDetailComponent;
import com.example.shoplocator.dagger.userDetail.UserDetailModule;
import com.example.shoplocator.dagger.usersList.UsersListComponent;
import com.example.shoplocator.dagger.usersList.UsersListModule;
import com.example.shoplocator.ui.createAndEditShop.CreateAndEditShopActivity;
import com.example.shoplocator.ui.shops.ShopsListActivity;
import com.example.shoplocator.ui.shops.detail.ShopDetailActivity;
import com.example.shoplocator.ui.splash.view.SplashActivity;
import com.example.shoplocator.ui.users.UsersListActivity;
import com.example.shoplocator.ui.users.detail.UserDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by macbookpro on 13.11.16.
 */

@Component(modules = {AppModule.class, UtilsModule.class, DataModule.class, BaseRepositoriesModule.class})
@Singleton
public interface AppComponent {

    void inject(SplashActivity activity);
    void inject(ShopsListActivity activity);
    void inject(ShopDetailActivity activity);
    void inject(CreateAndEditShopActivity activity);
    void inject(UsersListActivity activity);
    void inject(UserDetailActivity activity);

    ShopsComponent plus(ShopsModule module);
    ShopDetailComponent plus(ShopDetailModule module);
    ShopsMapComponent plus(ShopsMapModule module);
    CreateAndEditShopComponent plus(CreateAndEditShopModule module);
    UsersListComponent plus(UsersListModule module);
    UserDetailComponent plus(UserDetailModule module);

}
