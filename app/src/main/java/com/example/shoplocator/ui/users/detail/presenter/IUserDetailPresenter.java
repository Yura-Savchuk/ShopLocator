package com.example.shoplocator.ui.users.detail.presenter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.shoplocator.ui.users.detail.view.IUserDetailView;
import com.example.shoplocator.ui.users.detail.view.UserDetailFragment;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUserDetailPresenter {

    void setUserId(String userId);
    void setUserName(String userName);

    void bindView(IUserDetailView view);
    void unbindView();

    void setupTitle();
    void setupUserShops();

    void onItemClick(int position, View itemView);

    void onShopHasBeenRemovedFromDetailView(@NonNull String shopId);
    void onShopHasBeenEditedFromDetailView(@NonNull String shopId);

    void onRetryButtonClick();
}
