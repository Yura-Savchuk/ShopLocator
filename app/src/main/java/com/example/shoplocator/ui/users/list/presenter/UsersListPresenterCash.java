package com.example.shoplocator.ui.users.list.presenter;

import com.example.shoplocator.ui.model.UserModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersListPresenterCash {

    private List<UserModel> users;

    public List<UserModel> getUsers() {
        return users;
    }

    public boolean isUsersExist() {
        return users != null;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
