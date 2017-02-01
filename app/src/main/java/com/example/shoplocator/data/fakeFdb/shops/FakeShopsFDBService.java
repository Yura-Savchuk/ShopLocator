package com.example.shoplocator.data.fakeFdb.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.fakeFdb.GeneratorId;
import com.example.shoplocator.data.firebaseDb.mapper.ShopMapper;
import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.ShopFormDbModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class FakeShopsFDBService implements IShopsFDBService {

    private static final int EMPTY_POSITION = -1;

    private static final int COUNT_FOR_SHOPS_GENERATION = 20;

    private static List<ShopDbModel> shops;

    public FakeShopsFDBService() {
        if (shops == null) {
            shops = new FakeShopsListGenerator().getNewList(COUNT_FOR_SHOPS_GENERATION);
        }
    }

    @Override
    public Single<List<ShopDbModel>> getShops() {
        return Single.just(shops);
    }

    @Override
    public Single<ShopDbModel> addShop(@NonNull ShopFormDbModel formModel) {
        return Single.fromCallable(() -> ShopMapper.createFrom(getNewId(), formModel))
                .map(ShopMapper::transform)
                .doOnSuccess(this::addToFakeStorge);
    }

    private String getNewId() {
        while (true) {
            String id = new GeneratorId().getGeneratedId();
            boolean idIsUnical = true;
            for (ShopDbModel item : shops) {
                if (item.getId().equals(id)) {
                    idIsUnical = false;
                }
            }
            if (idIsUnical) return id;
        }
    }

    private void addToFakeStorge(ShopDbModel shopDbModel) {
        deleteShopByIdAndGetPosition(shopDbModel.getId());
        shops.add(shopDbModel);
    }

    private synchronized int deleteShopByIdAndGetPosition(@NonNull String shopId) {
        try {
            for (int i = 0; i < shops.size(); i++) {
                if (shops.get(i).getId().equals(shopId)) {
                    shops.remove(i);
                    return i;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return deleteShopByIdAndGetPosition(shopId);
        }
        return EMPTY_POSITION;
    }

    @Override
    public Single<ShopDbModel> updateShop(@NonNull String shopId, @NonNull ShopFormDbModel formDbModel) {
        return Single.fromCallable(() -> ShopMapper.createFrom(getNewId(), formDbModel))
                .map(ShopMapper::transform)
                .doOnSuccess(shopDbModel -> {
                    int position = deleteShopByIdAndGetPosition(shopId);
                    if (position != EMPTY_POSITION) {
                        shops.add(position, shopDbModel);
                    } else {
                        shops.add(shopDbModel);
                    }
                });
    }

    @Override
    public Single<Object> deleteShopsByIds(@NonNull Collection<String> ids) {
        return Single.fromCallable(() -> {
            for (int i=0; i<shops.size(); i++) {
                ShopDbModel shop = shops.get(i);
                if (ids.contains(shop.getId())) {
                    shops.remove(i--);
                }
            }
            return null;
        });
    }

    @Override
    public Single<List<ShopDbModel>> getShopsByUserId(String userId) {
        return Single.fromCallable(() -> {
            List<ShopDbModel> shopsByUserId = new LinkedList<>();
            for (ShopDbModel item : shops) {
                if (item.getOwnerId().equals(userId)) {
                    shopsByUserId.add(item);
                }
            }
            return shopsByUserId;
        });
    }

    @Override
    public Single<ShopDbModel> getShopsById(String shopId) {
        return Single.fromCallable(() -> {
            for (ShopDbModel item : shops) {
                if (item.getId().equals(shopId)) {
                    return item;
                }
            }
            throw new RuntimeException("Shop by id not found.");
        });
    }
}
