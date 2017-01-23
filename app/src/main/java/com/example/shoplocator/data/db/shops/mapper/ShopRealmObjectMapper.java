package com.example.shoplocator.data.db.shops.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.data.db.shops.model.ShopRealmObject;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.util.mapper.Mapper;
import com.example.shoplocator.util.mapper.MapperUtil;

import java.util.Collection;
import java.util.List;

/**
 * Created by seotm on 23.01.17.
 */

public class ShopRealmObjectMapper {

    @NonNull
    public static List<ShopDbModel> mapRealmToDb(@Nullable Collection<ShopRealmObject> shopRealmObjects) {
        return MapperUtil.transformList(shopRealmObjects, ShopRealmObjectMapper::mapRealmToDb);
    }

    @NonNull
    public static ShopDbModel mapRealmToDb(@NonNull ShopRealmObject shopRealmObject) {
        return new ShopDbModel(
                shopRealmObject.getId(),
                shopRealmObject.getName(),
                shopRealmObject.getImageUrl(),
                shopRealmObject.getCoordinateX(),
                shopRealmObject.getCoordinateY(),
                shopRealmObject.getOwnerId()
        );
    }

    @NonNull
    public static List<ShopRealmObject> mapDbToRealm(@Nullable List<ShopDbModel> dbShops) {
        return MapperUtil.transformList(dbShops, ShopRealmObjectMapper::mapDbToRealm);
    }

    @NonNull
    private static ShopRealmObject mapDbToRealm(@NonNull ShopDbModel dbModel) {
        ShopRealmObject shopRealmObject = new ShopRealmObject();
        shopRealmObject.setId(dbModel.getId());
        shopRealmObject.setName(dbModel.getName());
        shopRealmObject.setImageUrl(dbModel.getImageUrl());
        shopRealmObject.setOwnerId(dbModel.getOwnerId());
        shopRealmObject.setCoordinateX(dbModel.getCoordinateX());
        shopRealmObject.setCoordinateY(dbModel.getCoordinateY());
        return shopRealmObject;
    }

}
