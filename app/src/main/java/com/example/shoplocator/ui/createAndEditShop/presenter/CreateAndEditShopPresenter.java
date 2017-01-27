package com.example.shoplocator.ui.createAndEditShop.presenter;

import com.example.shoplocator.ui.createAndEditShop.view.ICreateAndEditShopView;

/**
 * Created by seotm on 27.01.17.
 */

public class CreateAndEditShopPresenter implements ICreateAndEditShopPresenter {

    private ICreateAndEditShopView view;

    @Override
    public void bindView(ICreateAndEditShopView view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        view = null;
    }
}
