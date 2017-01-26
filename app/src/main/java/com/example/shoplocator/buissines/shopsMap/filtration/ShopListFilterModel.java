package com.example.shoplocator.buissines.shopsMap.filtration;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 26.01.17.
 */

public class ShopListFilterModel {

    private final List<ShopModel> updatableShops;
    private final List<ShopModel> originalShops;

    public ShopListFilterModel(List<ShopModel> originalShops) {
        this.originalShops = originalShops;
        updatableShops = new LinkedList<>();
        updatableShops.addAll(originalShops);
    }

    public List<ShopModel> getUpdatableShops() {
        return updatableShops;
    }

    public List<ShopModel> getOriginalShops() {
        return originalShops;
    }
}
