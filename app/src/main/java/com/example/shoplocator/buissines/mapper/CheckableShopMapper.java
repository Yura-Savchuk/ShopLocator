package com.example.shoplocator.buissines.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;
import com.example.shoplocator.util.mapper.MapperUtil;

import java.util.Collection;
import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 23.01.17.
 */

public class CheckableShopMapper {

    @NonNull
    public static List<SelectableShopModel> mapShopsToCheckableShops(@Nullable Collection<ShopModel> shopModels) {
        return MapperUtil.transformList(shopModels, CheckableShopMapper::mapShopToCheckableShop);
    }

    public static SelectableShopModel mapShopToCheckableShop(@NonNull ShopModel shopModel) {
        return new SelectableShopModel(
                shopModel.getId(),
                shopModel.getName(),
                shopModel.getImageUrl(),
                shopModel.getCoordinate().clone(),
                shopModel.getOwner().clone()
        );
    }

}
