package com.example.shoplocator.ui.model;

import android.support.annotation.NonNull;
import android.text.Spannable;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopModel {

    private final String id;
    private final String name;
    private final String imageUrl;
    private final ShopCoordinate coordinate;
    private final UserModel owner;

    public ShopModel(String id, String name, String imageUrl, ShopCoordinate coordinate, @NonNull UserModel owner) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.coordinate = coordinate;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ShopCoordinate getCoordinate() {
        return coordinate;
    }

    public UserModel getOwner() {
        return owner;
    }
}
