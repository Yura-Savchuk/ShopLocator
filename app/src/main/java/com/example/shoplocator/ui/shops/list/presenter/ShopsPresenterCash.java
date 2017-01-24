package com.example.shoplocator.ui.shops.list.presenter;

import com.example.shoplocator.ui.shops.model.SelectableShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopsPresenterCash {

    private List<SelectableShopModel> shops;
    private boolean toolbarInEditState;

    public List<SelectableShopModel> getShops() {
        return shops;
    }

    public boolean isShopsExist() {
        return shops != null;
    }

    public void setShops(List<SelectableShopModel> shops) {
        this.shops = shops;
    }

    public void setToolbarInEditState(boolean toolbarInEditState) {
        this.toolbarInEditState = toolbarInEditState;
    }

    public boolean isToolbarInEditState() {
        return toolbarInEditState;
    }
}
