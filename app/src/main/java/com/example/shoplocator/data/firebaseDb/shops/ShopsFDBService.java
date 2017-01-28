package com.example.shoplocator.data.firebaseDb.shops;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.firebaseDb.RealTimeDatabaseConfig;
import com.example.shoplocator.data.firebaseDb.mapper.ShopMapper;
import com.example.shoplocator.data.firebaseDb.model.ShopFdbModel;
import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.ShopFormDbModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Single;
import rx.SingleSubscriber;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsFDBService implements IShopsFDBService {

    private static final String DATABASE_KEY_SHOPS = "shops";

    private static final String PARAM_ID = "id";
    private static final String PARAM_IMAGE_URL = "image_url";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_COORDINATE = "coordinate";
    private static final String PARAM_X = "x";
    private static final String PARAM_Y = "y";
    private static final String PARAM_OWNER_ID = "owner_id";

    private final DatabaseReference shopsDataRefrence;

    public ShopsFDBService(@NonNull FirebaseDatabase firebaseDatabase) {
        shopsDataRefrence = firebaseDatabase.getReference().child(DATABASE_KEY_SHOPS);
    }

    @Override
    public Single<List<ShopDbModel>> getShops() {
        return Single.create((Single.OnSubscribe<List<ShopDbModel>>) this::loadShopsAndEmit)
                .timeout(RealTimeDatabaseConfig.SECONDS_TIME_OUT, TimeUnit.SECONDS);
    }

    private void loadShopsAndEmit(SingleSubscriber<? super List<ShopDbModel>> subscriber) {
        shopsDataRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShopDbModel> shopDbModels = getShopsFromData(dataSnapshot);
                subscriber.onSuccess(shopDbModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });
    }

    private List<ShopDbModel> getShopsFromData(@NonNull DataSnapshot dataSnapshot) {
        List<ShopDbModel> shops = new ArrayList<>();
        for (DataSnapshot shopData : dataSnapshot.getChildren()) {
            ShopDbModel shop = getShopFromData(shopData);
            shops.add(shop);
        }
        return shops;
    }

    private ShopDbModel getShopFromData(@NonNull DataSnapshot dataSnapshot) {
        long id = dataSnapshot.child(PARAM_ID).getValue(Long.class);
        String imageUrl = dataSnapshot.child(PARAM_IMAGE_URL).getValue(String.class);
        String name = dataSnapshot.child(PARAM_NAME).getValue(String.class);
        DataSnapshot dataCoordinate = dataSnapshot.child(PARAM_COORDINATE);
        float coordX = dataCoordinate.child(PARAM_X).getValue(Float.class);
        float coordY = dataCoordinate.child(PARAM_Y).getValue(Float.class);
        long ownerId = dataSnapshot.child(PARAM_OWNER_ID).getValue(Long.class);
        return new ShopDbModel(id, name, imageUrl, coordX, coordY, ownerId);
    }

    @Override
    public Single<Long> addShopAngGetId(@NonNull ShopFormDbModel formModel) {
        return Single.create(new Single.OnSubscribe<Long>() {
            @Override
            public void call(SingleSubscriber<? super Long> subscriber) {
                String key = shopsDataRefrence.push().getKey();
                ShopFdbModel shopModel = ShopMapper.createFrom(key, formModel);
                shopsDataRefrence.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        subscriber.onSuccess((long)1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        subscriber.onError(databaseError.toException());
                    }
                });
                shopsDataRefrence.child(key).setValue(shopModel);
            }
        });
    }

}
