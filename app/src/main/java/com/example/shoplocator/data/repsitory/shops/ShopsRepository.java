package com.example.shoplocator.data.repsitory.shops;

import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.model.ShopDbModel;

import java.util.List;

import rx.Single;
import rx.functions.Func1;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsRepository implements IShopsRepository {

    private final IShopsFDBService shopsFDBService;

    public ShopsRepository(IShopsFDBService shopsFDBService) {
        this.shopsFDBService = shopsFDBService;
    }

    @Override
    public Single<List<ShopDbModel>> getShops() {
        return shopsFDBService.getShops();
    }

    @Override
    public Single<ShopDbModel> getShopById(long shopId) {
        return getShops()
                .map(shopDbModels -> {
                    for (ShopDbModel shop : shopDbModels) {
                        if (shop.getId() == shopId) {
                            return shop;
                        }
                    }
                    return null;
                })
                .onErrorResumeNext(Single.just(null)); //TODO
    }
}
