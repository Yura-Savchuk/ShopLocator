package com.example.shoplocator.ui.shops.list.presenter;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopsPresenterCash {

    private List<ShopModel> shops;

    public List<ShopModel> getShops() {
        return shops;
    }

    public boolean isShopsExist() {
        return shops != null;
    }

    public void setShops(List<ShopModel> shops) {
        this.shops = shops;
    }
}
