package com.example.shoplocator.data.db.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.ShopDbModel;

import java.util.Collection;
import java.util.List;

import rx.Single;

/**
 * Created by seotm on 23.01.17.
 */

public interface IShopsDBService {

    Single<List<ShopDbModel>> getShops();
    Single<ShopDbModel> getShopById(@NonNull String shopId);
    void setShops(@NonNull List<ShopDbModel> shops);

    Single<Object> deleteShopsByIds(@NonNull Collection<String> ids);
    Single<Object> addShop(@NonNull ShopDbModel shop);
    Single<Object> addShops(@NonNull Collection<ShopDbModel> shops);

    Single<List<ShopDbModel>> getShopsByUserId(@NonNull String userId);
}
