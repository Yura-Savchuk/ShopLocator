package com.example.shoplocator.dagger.shopsMap;

import com.example.shoplocator.ui.shopsMap.view.ShopsMapFragment;

import dagger.Subcomponent;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

@Subcomponent(modules = ShopsMapModule.class)
@ShopsMapScope
public interface ShopsMapComponent {

    void inject(ShopsMapFragment fragment);

}
