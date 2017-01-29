package com.example.shoplocator.ui.users.list.listAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.shoplocator.R;
import com.example.shoplocator.ui.model.UserModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter {

    private final List<UserModel> users;
    private final LayoutInflater inflater;
    private final UsersRecyclerViewDelegateProxy delegateProxy;

    public UsersRecyclerViewAdapter(List<UserModel> users, Context context) {
        this.users = users;
        inflater = LayoutInflater.from(context);
        delegateProxy = new UsersRecyclerViewDelegateProxy();
    }

    public void setDelegate(@Nullable UsersRecyclerViewDelegate delegate) {
        delegateProxy.setDelegate(delegate);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(inflater.inflate(R.layout.item_user, parent, false), delegateProxy);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserViewHolder vh = (UserViewHolder) holder;
        UserModel model = users.get(position);
        vh.textViewUserName.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
