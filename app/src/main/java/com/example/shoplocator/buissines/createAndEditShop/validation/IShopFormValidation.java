package com.example.shoplocator.buissines.createAndEditShop.validation;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public interface IShopFormValidation {

    Single<ShopFormModel> validate(@NonNull ShopFormModel formModel);

}
