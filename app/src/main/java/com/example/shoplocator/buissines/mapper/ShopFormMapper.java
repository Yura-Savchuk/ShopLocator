package com.example.shoplocator.buissines.mapper;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.ShopFormDbModel;
import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class ShopFormMapper {

    public static ShopFormDbModel transform(@NonNull ShopFormModel model) {
        UserDbModel user = UserMapper.transform(model.getUserModel());
        return new ShopFormDbModel(model.getName(), model.getImageUrl(), user, model.getPosX(), model.getPosY());
    }

}
