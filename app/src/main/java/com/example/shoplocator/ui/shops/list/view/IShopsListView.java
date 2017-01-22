package com.example.shoplocator.ui.shops.list.view;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */
public interface IShopsListView {

    void setupShopsList(@NonNull List<ShopModel> shops);

    void showShopDetail(long shopId);
}
