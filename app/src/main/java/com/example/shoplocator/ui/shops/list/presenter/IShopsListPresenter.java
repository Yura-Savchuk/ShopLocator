package com.example.shoplocator.ui.shops.list.presenter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.shoplocator.ui.shops.list.view.IShopsListView;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public interface IShopsListPresenter {

    void bindView(IShopsListView view);
    void unbindView();

    void setupShopsList();

    void onItemClick(int position, View itemView);

    void setupToolbarState();
    void onRemoveActionSelected();

    void onDoneActionSelected();
    void onCancelActionSelected();

    void onCreateActionSelected();

    void addShopById(@NonNull String shopId);
    void onEditShopResult(@NonNull String shopId);

}
