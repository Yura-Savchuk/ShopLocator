package com.example.shoplocator.ui.users.detail.presenter;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UserDetailPresenterCash {

    private List<ShopModel> shops;
    private String userId;
    private String userName;

    public List<ShopModel> getShops() {
        return shops;
    }

    public boolean isShopsExist() {
        return shops != null;
    }

    public void setShops(List<ShopModel> shops) {
        this.shops = shops;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
