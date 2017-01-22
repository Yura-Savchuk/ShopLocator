package com.example.shoplocator.dagger.shopsList;

import com.example.shoplocator.ui.shops.list.view.ShopsListFragment;

import dagger.Subcomponent;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

@Subcomponent(modules = {ShopsModule.class})
@ShopsScope
public interface ShopsComponent {

    void inject(ShopsListFragment shopsListFragment);

}
