package com.example.shoplocator.ui.shops.detail.presenter;

import com.example.shoplocator.ui.shops.detail.view.IShopDetailView;
import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public interface IShopDetailPresenter {

    void setShopId(String shopId);

    void bindView(IShopDetailView view);
    void unbindView();

    void setupShopDetails();
}
