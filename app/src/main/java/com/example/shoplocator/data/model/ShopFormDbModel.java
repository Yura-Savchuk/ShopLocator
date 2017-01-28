package com.example.shoplocator.data.model;

import com.example.shoplocator.data.model.UserDbModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class ShopFormDbModel {

    private final String name;
    private final String imageUrl;
    private final UserDbModel userModel;
    private final String posX;
    private final String posY;

    public ShopFormDbModel(String name, String imageUrl, UserDbModel userModel, String posX, String posY) {
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

    public UserDbModel getUserModel() {
        return userModel;
    }

    public String getPosX() {
        return posX;
    }

    public String getPosY() {
        return posY;
    }
}
