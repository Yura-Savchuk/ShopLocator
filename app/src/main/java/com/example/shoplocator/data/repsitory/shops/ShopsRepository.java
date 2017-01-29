package com.example.shoplocator.data.repsitory.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.db.shops.IShopsDBService;
import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.ShopFormDbModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import rx.Single;
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
    public Single<ShopDbModel> getShopById(@NonNull String shopId) {
        return shopsDBService.getShopById(shopId)
                .onErrorResumeNext(new Func1<Throwable, Single<ShopDbModel>>() {
                    @Override
                    public Single<ShopDbModel> call(Throwable throwable) {
                        return getShopByIdFromFDBService(shopId);
                    }
                });
    }

    private Single<ShopDbModel> getShopByIdFromFDBService(String shopId) {
        return getShops().map(shopDbModels -> {
            for (ShopDbModel shop : shopDbModels) {
                if (shop.getId().equals(shopId)) {
                    return shop;
                }
            }
            return null;
        });
    }

    @Override
    public Single<Object> deleteShopsByIdsFromDb(@NonNull Collection<String> ids) {
        return shopsDBService.deleteShopsByIds(ids);
    }

    @Override
    public Single<String> addShopAndGetId(@NonNull ShopFormDbModel formDbModel) {
        return shopsFDBService.addShop(formDbModel)
                .flatMap(shopDbModel -> shopsDBService.addShop(shopDbModel)
                        .map(o -> shopDbModel.getId()));
    }

    @Override
    public Single<String> updateShopAndGetId(@NonNull String shopId, @NonNull ShopFormDbModel formDbModel) {
        return shopsFDBService.updateShop(shopId, formDbModel)
                .flatMap(shopDbModel -> {
                    Collection<String> ids = new LinkedList<>();
                    ids.add(shopDbModel.getId());
                    return shopsDBService.deleteShopsByIds(ids)
                            .flatMap(o -> shopsDBService.addShop(shopDbModel));
                })
                .map(o -> shopId);
    }

    @Override
    public Single<List<ShopDbModel>> getShopsByUserId(@NonNull String userId) {
        return getShopsFromFDB(userId)
                .onErrorResumeNext(new Func1<Throwable, Single<? extends List<ShopDbModel>>>() {
                    @Override
                    public Single<? extends List<ShopDbModel>> call(Throwable throwable) {
                        return shopsDBService.getShopsByUserId(userId);
                    }
                });
    }

    private Single<List<ShopDbModel>> getShopsFromFDB(@NonNull String userId) {
        return shopsFDBService.getShopsByUserId(userId)
                .flatMap(shopDbModels -> shopsDBService.addShops(shopDbModels)
                        .map(o -> shopDbModels));
    }
}
