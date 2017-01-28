package com.example.shoplocator.ui.createAndEditShop.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.ui.model.UserModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class ShopFormModel {

    private final String name;
    private final String imageUrl;
    private final UserModel userModel;
    private final String posX;
    private final String posY;

    public ShopFormModel(@NonNull String name,
                         @NonNull String imageUrl,
                         @Nullable UserModel userModel,
                         @NonNull String posX,
                         @NonNull String posY) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.userModel = userModel;
        this.posX = posX;
        this.posY = posY;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public String getPosX() {
        return posX;
    }

    public String getPosY() {
        return posY;
    }
}
