package com.example.shoplocator.buissines.shopsMap.filtration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.ui.model.ShopModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 26.01.17.
 */

public class ShopListFilter implements IShopListFiler {

    private final ShopListFilterModel filterModel;

    public ShopListFilter(ShopListFilterModel filterModel) {
        this.filterModel = filterModel;
    }

    @Override
    public void filterByQuery(@Nullable String query) {
        filterModel.getUpdatableShops().clear();
        if (query != null) {
            addSuitableShops(query);
        }
    }

    private void addSuitableShops(@NonNull String query) {
        String formattedQuery = query.toLowerCase().trim();
        for (ShopModel originalShop : filterModel.getOriginalShops()) {
            if (isShopNameContainQuery(originalShop.getName(), formattedQuery)) {
                filterModel.getUpdatableShops().add(originalShop);
            }
        }
    }

    private boolean isShopNameContainQuery(@NonNull String shopName, @NonNull String query) {
        return shopName.toLowerCase().trim().contains(query);
    }

}
