package com.example.shoplocator.dagger.application;

import com.example.shoplocator.dagger.createAndEditShop.CreateAndEditShopComponent;
import com.example.shoplocator.dagger.createAndEditShop.CreateAndEditShopModule;
import com.example.shoplocator.dagger.shopDetail.ShopDetailComponent;
import com.example.shoplocator.dagger.shopDetail.ShopDetailModule;
import com.example.shoplocator.dagger.shopsList.ShopsComponent;
import com.example.shoplocator.dagger.shopsList.ShopsModule;
import com.example.shoplocator.dagger.shopsMap.ShopsMapComponent;
import com.example.shoplocator.dagger.shopsMap.ShopsMapModule;
import com.example.shoplocator.ui.createAndEditShop.CreateAndEditShopActivity;
import com.example.shoplocator.ui.shops.ShopsListActivity;
import com.example.shoplocator.ui.shops.detail.ShopDetailActivity;
import com.example.shoplocator.ui.splash.view.SplashActivity;

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

    ShopsComponent plus(ShopsModule module);
    ShopDetailComponent plus(ShopDetailModule module);
    ShopsMapComponent plus(ShopsMapModule module);
    CreateAndEditShopComponent plus(CreateAndEditShopModule module);
}
