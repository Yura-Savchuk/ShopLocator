package com.example.shoplocator.ui.users.list.listAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shoplocator.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textViewUserName) TextView textViewUserName;

    public UserViewHolder(@NonNull View itemView, @NonNull UsersRecyclerViewDelegate delegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> delegate.onItemClick(getAdapterPosition()));
    }
}
