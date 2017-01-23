package com.example.shoplocator.ui.shops.list.presenter;

import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.CheckableShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopsPresenterCash {

    private List<CheckableShopModel> shops;

    public List<CheckableShopModel> getShops() {
        return shops;
    }

    public boolean isShopsExist() {
        return shops != null;
    }

    public void setShops(List<CheckableShopModel> shops) {
        this.shops = shops;
    }
}
