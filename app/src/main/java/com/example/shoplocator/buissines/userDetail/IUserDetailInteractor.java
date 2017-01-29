package com.example.shoplocator.buissines.userDetail;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

import rx.Single;
import rx.Subscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUserDetailInteractor {

    Single<List<ShopModel>> getShopsByUserId(@NonNull String userId, @NonNull String userName);
    Single<ShopModel> getShopById(@NonNull String shopId, @NonNull String userId, @NonNull String userName);

}
