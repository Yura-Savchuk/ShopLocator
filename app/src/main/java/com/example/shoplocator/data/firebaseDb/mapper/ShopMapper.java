package com.example.shoplocator.data.firebaseDb.mapper;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.firebaseDb.model.CoordinateFdbModel;
import com.example.shoplocator.data.firebaseDb.model.ShopFdbModel;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.ShopFormDbModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class ShopMapper {

    public static ShopFdbModel createFrom(@NonNull String key, @NonNull ShopFormDbModel shopForm) {
        double posX = Double.parseDouble(shopForm.getPosX());
        double posY = Double.parseDouble(shopForm.getPosY());
        CoordinateFdbModel coordinateFdb = new CoordinateFdbModel(posX, posY);
        String ownerId = String.valueOf(shopForm.getUserModel().getId());
        return new ShopFdbModel(key, shopForm.getName(), shopForm.getImageUrl(), ownerId, coordinateFdb);
    }

    public static ShopDbModel transform(@NonNull ShopFdbModel shopFdbModel) {
        return new ShopDbModel(
                shopFdbModel.id,
                shopFdbModel.name,
                shopFdbModel.image_url,
                (float) shopFdbModel.coordinate.x,
                (float) shopFdbModel.coordinate.y,
                shopFdbModel.owner_id
        );
    }

}
