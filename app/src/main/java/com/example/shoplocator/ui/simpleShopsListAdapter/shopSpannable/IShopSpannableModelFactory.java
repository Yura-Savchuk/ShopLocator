package com.example.shoplocator.ui.simpleShopsListAdapter.shopSpannable;

import android.support.annotation.NonNull;
import android.text.Spannable;

import com.example.shoplocator.ui.model.ShopCoordinate;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public interface IShopSpannableModelFactory {

    Spannable createOwnerSpannable(@NonNull String ownerName);
    Spannable createCoordsSpannable(@NonNull ShopCoordinate shopCoordinate);

}
