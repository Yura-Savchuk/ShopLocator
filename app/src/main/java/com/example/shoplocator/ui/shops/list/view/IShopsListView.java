package com.example.shoplocator.ui.shops.list.view;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.shoplocator.ui.shops.model.SelectableShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */
public interface IShopsListView {

    void setupShopsList(@NonNull List<SelectableShopModel> shops);
    void showShopDetail(long shopId, View itemView);
    void showProgress(boolean show);

    void onRemoveActionSelected();
    void onDoneActionSelection();
    void onCancelActionSelection();
    void onCreateActionSelection();

    void setToolbarInEditState(boolean editState);

    void notifyItemRemoved(int position);
    void notifyItemChanged(int position);

    void showCreateShopView();
}
