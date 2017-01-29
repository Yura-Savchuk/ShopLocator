package com.example.shoplocator.ui.users.detail.shopsListAdapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoplocator.R;
import com.example.shoplocator.util.sugar.CompareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.textViewShopName) TextView textViewShopName;
    @BindView(R.id.textViewShopOwner) TextView textViewShopOwner;
    @BindView(R.id.textViewShopCoords) TextView textViewShopCoords;

    public ShopViewHolder(View itemView, @NonNull ShopsRecyclerViewDelegate delegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> delegate.onItemClick(getAdapterPosition()));
    }

}
