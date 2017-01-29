package com.example.shoplocator.ui.users.list.presenter;

import com.example.shoplocator.ui.users.model.SelectableUserModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersListPresenterCash {

    private List<SelectableUserModel> users;

    public List<SelectableUserModel> getUsers() {
        return users;
    }

    public boolean isUsersExist() {
        return users != null;
    }

    public void setUsers(List<SelectableUserModel> users) {
        this.users = users;
    }
}
