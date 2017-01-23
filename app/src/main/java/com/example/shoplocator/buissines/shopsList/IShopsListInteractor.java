package com.example.shoplocator.buissines.shopsList;

import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.CheckableShopModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public interface IShopsListInteractor {

    Single<List<CheckableShopModel>> getCheckableShops();

}
