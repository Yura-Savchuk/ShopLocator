package com.example.shoplocator.ui.shopsMap.presenter;

import android.os.Bundle;

import com.example.shoplocator.ui.shopsMap.view.IShopMapView;
import com.example.shoplocator.ui.shopsMap.view.ShopsMapActivity;
import com.google.android.gms.maps.GoogleMap;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

public interface IShopMapPresenter {

    void bindView(IShopMapView view);
    void unbindView();

    void loadShopsAndControlAccessablity(Single<Object> single);

    void onShopPositionChanged(int position);
}
