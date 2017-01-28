package com.example.shoplocator.ui.shops.detail.presenter;

import com.example.shoplocator.ui.model.ShopModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */
public class ShopDetailPresenterCash {

    private String shopId;
    private ShopModel shopModel;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public ShopModel getShopModel() {
        return shopModel;
    }

    public void setShopModel(ShopModel shopModel) {
        this.shopModel = shopModel;
    }

    public boolean isShopModelExist() {
        return shopModel != null;
    }

}
