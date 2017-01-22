package com.example.shoplocator.ui.shops.detail.view;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopCoordinate;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public interface IShopDetailView {

    void setTitle(@NonNull String name);
    void setImage(@NonNull String imageUrl);
    void setOwner(@NonNull String name);
    void setCoordinate(@NonNull ShopCoordinate coordinate);
}
