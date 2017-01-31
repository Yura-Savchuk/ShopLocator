package com.example.shoplocator.ui.users.detail.view;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUserDetailView {

    void setTitle(@NonNull String title);

    void setProgress(boolean progress);

    void setupShopsList(@NonNull List<ShopModel> shops);

    void shopErrorView();

    void showDetailShopView(@NonNull String shopId, View itemView);

    void notifyShopItemRemoved(int position);
    void notifyShopItemChanged(int position);
}
