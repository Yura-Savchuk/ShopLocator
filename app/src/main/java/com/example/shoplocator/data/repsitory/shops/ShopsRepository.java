package com.example.shoplocator.data.repsitory.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.db.shops.IShopsDBService;
import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.ShopFormDbModel;

import java.util.Collection;
import java.util.List;

import rx.Single;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsRepository implements IShopsRepository {

    private final IShopsFDBService shopsFDBService;
    private final IShopsDBService shopsDBService;

    public ShopsRepository(@NonNull IShopsFDBService shopsFDBService, @NonNull IShopsDBService shopsDBService) {
        this.shopsFDBService = shopsFDBService;
        this.shopsDBService = shopsDBService;
    }

    @Override
    public Single<List<ShopDbModel>> getShops() {
        return shopsDBService.getShops()
                .flatMap(shopDbModels -> {
                    if (shopDbModels == null || shopDbModels.isEmpty()) {
                        return shopsFDBService.getShops()
                                .doOnSuccess(shopsDBService::setShops);
                    }
                    return Single.just(shopDbModels);
                });
    }

    @Override
    public Single<ShopDbModel> getShopById(long shopId) {
        return shopsDBService.getShopById(shopId)
                .onErrorResumeNext(new Func1<Throwable, Single<ShopDbModel>>() {
                    @Override
                    public Single<ShopDbModel> call(Throwable throwable) {
                        return getShopByIdFromFDBService(shopId);
                    }
                });
    }

    private Single<ShopDbModel> getShopByIdFromFDBService(long shopId) {
        return getShops().map(shopDbModels -> {
            for (ShopDbModel shop : shopDbModels) {
                if (shop.getId() == shopId) {
                    return shop;
                }
            }
            return null;
        });
    }

    @Override
    public Single<Object> deleteShopsByIdsFromDb(@NonNull Collection<Long> ids) {
        return shopsDBService.deleteShopsByIds(ids);
    }

    @Override
    public Single<Long> addShopAndGetId(@NonNull ShopFormDbModel formDbModel) {
        return shopsFDBService.addShopAngGetId(formDbModel);
    }
}
