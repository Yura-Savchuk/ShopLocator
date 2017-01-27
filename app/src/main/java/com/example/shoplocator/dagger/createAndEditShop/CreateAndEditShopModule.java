package com.example.shoplocator.dagger.createAndEditShop;

import com.example.shoplocator.ui.createAndEditShop.presenter.CreateAndEditShopPresenter;
import com.example.shoplocator.ui.createAndEditShop.presenter.ICreateAndEditShopPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by seotm on 27.01.17.
 */

@Module
public class CreateAndEditShopModule {

    @Provides
    @CreateAndEditShopScope
    ICreateAndEditShopPresenter createAndEditShopPresenter() {
        ICreateAndEditShopPresenter presenter = new CreateAndEditShopPresenter();
        return presenter;
    }

}
