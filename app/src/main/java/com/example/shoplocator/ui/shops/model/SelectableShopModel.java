package com.example.shoplocator.ui.shops.model;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.model.UserModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 23.01.17.
 */

public class SelectableShopModel extends ShopModel {

    private boolean selected;

    public SelectableShopModel(long id, String name, String imageUrl, ShopCoordinate coordinate, @NonNull UserModel owner) {
        super(id, name, imageUrl, coordinate, owner);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
