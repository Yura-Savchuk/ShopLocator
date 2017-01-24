package com.example.shoplocator.data.repsitory.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.Collection;
import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public interface IShopsRepository {

    Single<List<ShopDbModel>> getShops();
    Single<ShopDbModel> getShopById(long shopId);
    Single<Object> deleteShopsByIdsFromDb(@NonNull Collection<Long> ids);

}
