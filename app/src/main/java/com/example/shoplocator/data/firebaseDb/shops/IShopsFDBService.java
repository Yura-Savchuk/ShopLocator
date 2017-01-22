package com.example.shoplocator.data.firebaseDb.shops;

import com.example.shoplocator.data.model.ShopDbModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public interface IShopsFDBService {

    Single<List<ShopDbModel>> getShops();

}
