package com.example.shoplocator.ui.createAndEditShop.view;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;

import java.util.List;

/**
 * Created by seotm on 27.01.17.
 */
public interface ICreateAndEditShopView {

    void onSubmitButtonClick();

    void showProgress(boolean progress);

    void returnSuccessResult(long shopId);

    void close();

    @NonNull
    String getShopName();

    @NonNull
    String getImageUrl();

    @NonNull
    String getPosX();

    @NonNull
    String getPosY();

    void setupUserSelector(@NonNull List<CheckableUserModel> users);

    void showErrorView();
}
