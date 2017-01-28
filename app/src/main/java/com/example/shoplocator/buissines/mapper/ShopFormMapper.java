package com.example.shoplocator.buissines.mapper;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.ShopFormDbModel;
import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.model.UserModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class ShopFormMapper {

    public static ShopFormDbModel transform(@NonNull ShopFormModel model) {
        UserDbModel user = UserMapper.transform(model.getUserModel());
        return new ShopFormDbModel(model.getName(), model.getImageUrl(), user, model.getPosX(), model.getPosY());
    }

    public static ShopFormModel transform(@NonNull ShopModel shopModel) {
        UserModel user = shopModel.getOwner();
        ShopCoordinate coordingate = shopModel.getCoordinate();
        String positionX = String.valueOf(coordingate.getX());
        String positionY = String.valueOf(coordingate.getY());
        return new ShopFormModel(shopModel.getName(), shopModel.getImageUrl(), user, positionX, positionY);
    }
}
