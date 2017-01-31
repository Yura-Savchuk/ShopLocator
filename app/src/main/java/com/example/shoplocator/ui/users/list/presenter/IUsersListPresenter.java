package com.example.shoplocator.ui.users.list.presenter;

import com.example.shoplocator.ui.users.list.view.IUsersListView;
import com.example.shoplocator.ui.users.list.view.UsersListFragment;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUsersListPresenter {

    void bindView(IUsersListView view);
    void unbindView();

    void setupUsersList();

    void onItemClick(int position);

}
