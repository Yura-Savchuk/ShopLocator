package com.example.shoplocator.ui.shops;

import android.view.View;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public interface ShopListDelegate {

    void showShopDetail(long shopId, View itemView);

    void setEditState(boolean editState);

}
