package com.example.shoplocator.ui.shops.model;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.model.UserModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 23.01.17.
 */

public class CheckableShopModel extends ShopModel {

    private boolean checked;

    public CheckableShopModel(long id, String name, String imageUrl, ShopCoordinate coordinate, @NonNull UserModel owner) {
        super(id, name, imageUrl, coordinate, owner);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
