package com.example.shoplocator.data.db.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.ShopDbModel;

import java.util.List;

import rx.Single;

/**
 * Created by seotm on 23.01.17.
 */

public interface IShopsDBService {

    Single<List<ShopDbModel>> getShops();
    Single<ShopDbModel> getShopById(long shopId);
    void setShops(@NonNull List<ShopDbModel> shops);

}
