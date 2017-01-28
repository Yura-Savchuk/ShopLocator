package com.example.shoplocator.buissines.shopDetail;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopModel;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public interface IShopDetailInteractor {

    Single<ShopModel> getShopById(@NonNull String shopId);
    Single<Object> deleteShopById(@NonNull String shopId);

}
