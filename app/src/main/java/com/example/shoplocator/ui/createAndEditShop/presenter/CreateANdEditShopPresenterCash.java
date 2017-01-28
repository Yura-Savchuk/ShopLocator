package com.example.shoplocator.ui.createAndEditShop.presenter;

import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.model.UserModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class CreateANdEditShopPresenterCash {

    private String shopId;
    private List<CheckableUserModel> users;
    private boolean isDataPrefilled;

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {
        return shopId;
    }

    public boolean isUsersExist() {
        return users != null;
    }

    public List<CheckableUserModel> getUsers() {
        return users;
    }

    public void setUsers(List<CheckableUserModel> users) {
        this.users = users;
    }

    public boolean isDataPrefilled() {
        return isDataPrefilled;
    }

    public void setDataPrefilled(boolean dataPrefilled) {
        isDataPrefilled = dataPrefilled;
    }
}
