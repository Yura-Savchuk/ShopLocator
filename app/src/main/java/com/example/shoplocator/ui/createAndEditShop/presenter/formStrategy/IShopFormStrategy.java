package com.example.shoplocator.ui.createAndEditShop.presenter.formStrategy;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;

import rx.Single;
import rx.Subscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public interface IShopFormStrategy {

    Subscription prepareForm();
    Single<Long> saveShopAndGetId(@NonNull ShopFormModel fromModel);

}
