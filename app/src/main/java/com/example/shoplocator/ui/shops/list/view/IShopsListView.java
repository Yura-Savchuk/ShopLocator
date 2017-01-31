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
    void showShopDetail(String shopId, View itemView);
    void showProgress(boolean show);

    void onRemoveActionSelected();
    void onDoneActionSelected();
    void onCancelActionSelected();
    void onCreateActionSelected();

    void setToolbarInEditState(boolean editState);

    void notifyItemRemoved(int position);
    void notifyItemChanged(int position);
    void notifyItemInserted(int position);

    void showCreateShopView();
    void onEditShopResult(@NonNull String shopId);
    void onDeleteShopResult(@NonNull String shopId);

    void showErrorView();
    void onRetryButtonClick(View view);
}
