package com.example.shoplocator.ui.users.list.view;

import com.example.shoplocator.ui.users.model.SelectableUserModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUsersListView {

    void setupUsersList(List<SelectableUserModel> users);

    void setProgress(boolean progress);

    void showErrorView();
}
