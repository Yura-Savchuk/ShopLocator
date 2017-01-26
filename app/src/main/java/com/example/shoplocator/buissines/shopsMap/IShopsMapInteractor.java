package com.example.shoplocator.buissines.shopsMap;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.buissines.shopsMap.filtration.ShopListFilterModel;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

public interface IShopsMapInteractor {

    Single<ShopListFilterModel> getShopListFilterModel();
    void filterShopList(@NonNull ShopListFilterModel listFilterModel, @Nullable String query);

}
