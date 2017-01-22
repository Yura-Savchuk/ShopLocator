package com.example.shoplocator.dagger.shopDetail;

import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;

import dagger.Subcomponent;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

@Subcomponent(modules = ShopDetailModule.class)
@ShopDetailScope
public interface ShopDetailComponent {

    void inject(ShopDetailFragment fragment);

}
