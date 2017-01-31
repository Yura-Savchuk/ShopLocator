package com.example.shoplocator.data.repsitory.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.db.shops.IShopsDBService;
import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.ShopFormDbModel;
import com.example.shoplocator.util.rx.validation.RxValidation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
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
    private final RxValidation rxValidation;

    public ShopsRepository(@NonNull IShopsFDBService shopsFDBService, @NonNull IShopsDBService shopsDBService, RxValidation rxValidation) {
        this.shopsFDBService = shopsFDBService;
        this.shopsDBService = shopsDBService;
        this.rxValidation = rxValidation;
    }

    @Override
    public Single<List<ShopDbModel>> getShops() {
        return getShopsFromFDb()
                .onErrorResumeNext(throwable -> getShopsFromDb()
                        .compose(rxValidation.collectionNotEmpty(new RuntimeException("Shops list not exist in db."))));
    }

    private Single<List<ShopDbModel>> getShopsFromFDb() {
        return shopsFDBService.getShops()
                .doOnSuccess(shopsDBService::setShops);
    }

    private Single<List<ShopDbModel>> getShopsFromDb() {
        return shopsDBService.getShops();
    }

    @Override
    public Single<ShopDbModel> getShopById(@NonNull String shopId) {
        return getShopByIdFromFdb(shopId)
                .onErrorResumeNext(throwable -> getShopByIdFromDb(shopId));
    }

    private Single<ShopDbModel> getShopByIdFromFdb(@NonNull String shopId) {
        return shopsFDBService.getShopsById(shopId)
                .flatMap(shopDbModel -> {
                    Collection<ShopDbModel> shops = new ArrayList<>();
                    shops.add(shopDbModel);
                    return shopsDBService.addShops(shops)
                            .map(o -> shopDbModel);
                });
    }

    @NonNull
    private Single<ShopDbModel> getShopByIdFromDb(@NonNull final String shopId) {
        return shopsDBService.getShopById(shopId);
    }

    @Override
    public Single<Object> deleteShopsByIds(@NonNull Collection<String> ids) {
//        return shopsDBService.deleteShopsByIds(ids);
        return shopsFDBService.deleteShopsByIds(ids)
                .flatMap(o -> shopsDBService.deleteShopsByIds(ids));
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
                .onErrorResumeNext(throwable -> shopsDBService.getShopsByUserId(userId));
    }

    private Single<List<ShopDbModel>> getShopsFromFDB(@NonNull String userId) {
        return shopsFDBService.getShopsByUserId(userId)
                .flatMap(shopDbModels -> shopsDBService.addShops(shopDbModels)
                        .map(o -> shopDbModels));
    }

    @Override
    public Single<String> getLocalDbStructure() {
        return shopsDBService.getDbStructure();
    }
}
