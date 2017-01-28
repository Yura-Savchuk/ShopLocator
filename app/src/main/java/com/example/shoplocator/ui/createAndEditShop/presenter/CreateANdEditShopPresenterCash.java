package com.example.shoplocator.ui.createAndEditShop.presenter;

import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.model.UserModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class CreateANdEditShopPresenterCash {

    private long shopId;
    private List<CheckableUserModel> users;

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public long getShopId() {
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
}
