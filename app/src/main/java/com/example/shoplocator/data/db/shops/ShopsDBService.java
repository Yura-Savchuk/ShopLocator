package com.example.shoplocator.data.db.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.db.client.IDatabaseClient;
import com.example.shoplocator.data.db.shops.mapper.ShopRealmObjectMapper;
import com.example.shoplocator.data.db.shops.model.ShopRealmObject;
import com.example.shoplocator.data.model.ShopDbModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import rx.Single;
import rx.functions.Func1;

/**
 * Created by seotm on 23.01.17.
 */

public class ShopsDBService implements IShopsDBService {

    private static final String FIELD_SHOP_ID = "id";

    private final IDatabaseClient client;

    public ShopsDBService(IDatabaseClient client) {
        this.client = client;
    }

    @Override
    public Single<List<ShopDbModel>> getShops() {
        return Single.fromCallable(() -> client.getRealm().where(ShopRealmObject.class).findAll())
                .map(ShopRealmObjectMapper::mapRealmToDb);
    }

    @Override
    public Single<ShopDbModel> getShopById(long shopId) {
        return Single.fromCallable(() -> client.getRealm().where(ShopRealmObject.class).equalTo(FIELD_SHOP_ID, shopId).findFirst())
                .flatMap(shopRealmObject -> {
                    if (shopRealmObject == null) {
                        return Single.error(new RuntimeException("Shop by id: " + shopId + " is missing."));
                    }
                    return Single.just(ShopRealmObjectMapper.mapRealmToDb(shopRealmObject));
                });
    }

    @Override
    public void setShops(@NonNull List<ShopDbModel> shops) {
        List<ShopRealmObject> realmShops = ShopRealmObjectMapper.mapDbToRealm(shops);
        Realm realm = client.getRealm();
        realm.beginTransaction();
        realm.where(ShopRealmObject.class).findAll().deleteAllFromRealm();
        for (ShopRealmObject shop : realmShops) {
            realm.copyToRealm(shop);
        }
        realm.commitTransaction();
    }

    @Override
    public Single<Object> deleteShopsByIds(@NonNull Collection<Long> ids) {
        return Single.fromCallable(() -> {
            Realm realm = client.getRealm();
            Collection<ShopRealmObject> shops = getShopsFromRealmByIds(realm, ids);
            realm.beginTransaction();
            for (ShopRealmObject shop : shops) {
                shop.deleteFromRealm();
            }
            realm.commitTransaction();
            return null;
        });
    }

    @NonNull
    private Collection<ShopRealmObject> getShopsFromRealmByIds(@NonNull Realm realm, @NonNull Collection<Long> ids) {
        Collection<ShopRealmObject> realmObjects = new ArrayList<>();
        for (Long id : ids) {
            ShopRealmObject realmObject = realm.where(ShopRealmObject.class)
                    .equalTo(FIELD_SHOP_ID, id).findFirst();
            if (realmObject != null) realmObjects.add(realmObject);
        }
        return realmObjects;
    }
}
