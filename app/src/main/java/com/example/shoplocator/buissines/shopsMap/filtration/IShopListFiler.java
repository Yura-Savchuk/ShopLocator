package com.example.shoplocator.buissines.shopsMap.filtration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.Collection;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 26.01.17.
 */

public interface IShopListFiler {

    void filterByQuery(@Nullable String query);

}
