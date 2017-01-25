package com.example.shoplocator.ui.shopsMap.presenter;

import android.os.Bundle;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */
public class ShopsMapPresenterCash {

    private static final int DEFAULT_SELECTED_SHOP_POSITION = 0;

    private static final String STATE_PARAM_SHOP_ITEM_POSITION = "shop_item_position";

    private List<ShopModel> shops;
    private int selectedShopPosition = DEFAULT_SELECTED_SHOP_POSITION;

    public boolean isShopsExist() {
        return shops != null;
    }

    public List<ShopModel> getShops() {
        return shops;
    }

    public void setShops(List<ShopModel> shops) {
        this.shops = shops;
    }

    public int getSelectedShopPosition() {
        return selectedShopPosition;
    }

    public void setSelectedShopPosition(int selectedShopPosition) {
        this.selectedShopPosition = selectedShopPosition;
    }

    public void saveInstanceState(Bundle outState) {
        outState.putInt(STATE_PARAM_SHOP_ITEM_POSITION, selectedShopPosition);
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        selectedShopPosition = savedInstanceState.getInt(STATE_PARAM_SHOP_ITEM_POSITION, DEFAULT_SELECTED_SHOP_POSITION);
    }
}
