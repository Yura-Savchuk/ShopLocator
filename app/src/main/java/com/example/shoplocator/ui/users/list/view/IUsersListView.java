package com.example.shoplocator.ui.users.list.view;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.UserModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUsersListView {

    void setupUsersList(List<UserModel> users);

    void setProgress(boolean progress);

    void showErrorView(boolean show);

    void showUserDetailView(@NonNull String userId, @NonNull String userName);
}
