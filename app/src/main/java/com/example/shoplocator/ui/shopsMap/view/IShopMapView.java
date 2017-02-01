package com.example.shoplocator.ui.shopsMap.view;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

public interface IShopMapView {

    void shopProgress(boolean progress);

    void setupShopsList(List<ShopModel> shops);
    void setupShopMapkers(List<ShopModel> shops);

    void setMapCursorToCoordinate(ShopCoordinate coordinate);

    void setShopByPositionOnPager(int position);

    void onQueryChanged(@NonNull String query);

    void notifyShopsDataChanged();

    void showErrorView(boolean show);
}
