package com.example.shoplocator.dagger.createAndEditShop;

import com.example.shoplocator.ui.createAndEditShop.view.CreateAndEditShopFragment;

import dagger.Subcomponent;

/**
 * Created by seotm on 27.01.17.
 */

@Subcomponent(modules = CreateAndEditShopModule.class)
@CreateAndEditShopScope
public interface CreateAndEditShopComponent {

    void inject(CreateAndEditShopFragment fragment);

}
