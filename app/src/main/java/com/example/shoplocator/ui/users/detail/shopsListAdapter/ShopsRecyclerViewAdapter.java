package com.example.shoplocator.ui.users.detail.shopsListAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.shoplocator.R;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;
import com.example.shoplocator.ui.users.detail.shopsListAdapter.shopSpannable.ShopSpannableModel;
import com.example.shoplocator.ui.users.detail.shopsListAdapter.shopSpannable.ShopSpannableModelsPool;
import com.example.shoplocator.util.picasso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopsRecyclerViewAdapter extends RecyclerView.Adapter {

    private final List<ShopModel> shops;
    private final LayoutInflater inflater;
    private final ShopSpannableModelsPool spannableModelsPool;
    private final ShopsRecyclerViewDelegateProxy delegateProxy;

    public ShopsRecyclerViewAdapter(@NonNull List<ShopModel> shops, Context context) {
        this.shops = shops;
        inflater = LayoutInflater.from(context);
        spannableModelsPool = new ShopSpannableModelsPool(context);
        delegateProxy = new ShopsRecyclerViewDelegateProxy();
    }

    public void setDelegate(ShopsRecyclerViewDelegate delegate) {
        delegateProxy.setRecyclerViewDelegate(delegate);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopViewHolder(inflater.inflate(R.layout.item_shop, parent, false), delegateProxy);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShopViewHolder vh = (ShopViewHolder) holder;
        ShopModel shopModel = shops.get(position);
        setupImageView(vh, shopModel);
        vh.textViewShopName.setText(shopModel.getName());
        ShopSpannableModel spannableModel = spannableModelsPool.getSpannableModel(shopModel);
        vh.textViewShopOwner.setText(spannableModel.getOwner());
        vh.textViewShopCoords.setText(spannableModel.getCoordinate());
    }

    private void setupImageView(ShopViewHolder vh, ShopModel shopModel) {
        Picasso.with(vh.itemView.getContext())
                .load(shopModel.getImageUrl())
                .transform(new CircleTransform())
                .into(vh.imageView);
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }
}
