package com.example.shoplocator.ui.shopsMap.presenter;

import com.example.shoplocator.buissines.shopsMap.filtration.ShopListFilterModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */
public class ShopsMapPresenterCash {

    private static final int DEFAULT_SELECTED_SHOP_POSITION = 0;

    private int selectedShopPosition = DEFAULT_SELECTED_SHOP_POSITION;
    private ShopListFilterModel shopListFilterModel;
    private String query;

    public int getSelectedShopPosition() {
        return selectedShopPosition;
    }

    public void setSelectedShopPosition(int selectedShopPosition) {
        this.selectedShopPosition = selectedShopPosition;
    }

    public boolean isShopListFilterModelExist() {
        return shopListFilterModel != null;
    }

    public ShopListFilterModel getShopListFilterModel() {
        return shopListFilterModel;
    }

    public void setShopListFilterModel(ShopListFilterModel shopListFilterModel) {
        this.shopListFilterModel = shopListFilterModel;
    }

    public boolean isQueryExist() {
        return query != null;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
