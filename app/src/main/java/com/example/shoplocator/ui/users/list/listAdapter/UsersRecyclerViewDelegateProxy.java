package com.example.shoplocator.ui.users.list.listAdapter;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersRecyclerViewDelegateProxy implements UsersRecyclerViewDelegate {

    private UsersRecyclerViewDelegate delegate;

    public void setDelegate(UsersRecyclerViewDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onItemClick(int position) {
        if (delegate != null) delegate.onItemClick(position);
    }
}
