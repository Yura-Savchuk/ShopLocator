package com.example.shoplocator.ui.shops.list.presenter;

import com.example.shoplocator.ui.shops.list.view.IShopsListView;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public interface IShopsListPresenter {

    void bindView(IShopsListView view);
    void unbindView();

    void setupShopsList();

    void onItemClick(int position);
}
