package com.example.shoplocator.buissines.createAndEditShop;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public interface ICreateAndEditShopInteractor {

    Single<List<CheckableUserModel>> getCheckableUsers();
    Single<String> addShopAngGetId(@NonNull ShopFormModel fromModel);

    Single<ShopFormModel> validateForm(ShopFormModel shopForm);
}
