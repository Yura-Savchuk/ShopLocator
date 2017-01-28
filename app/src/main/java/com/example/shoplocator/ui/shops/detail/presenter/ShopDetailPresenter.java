package com.example.shoplocator.ui.shops.detail.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.shoplocator.buissines.shopDetail.IShopDetailInteractor;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.detail.view.IShopDetailView;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopDetailPresenter implements IShopDetailPresenter {

    private final ShopDetailPresenterCash cash;
    private final IShopDetailInteractor shopDetailInteractor;
    private final RxSchedulersAbs rxSchedulers;

    private IShopDetailView view;
    private CompositeSubscription compositeSubscription;

    public ShopDetailPresenter(IShopDetailInteractor shopDetailInteractor, RxSchedulersAbs rxSchedulers) {
        this.shopDetailInteractor = shopDetailInteractor;
        this.rxSchedulers = rxSchedulers;
        this.cash = new ShopDetailPresenterCash();
    }

    @Override
    public void setShopId(String shopId) {
        cash.setShopId(shopId);
    }

    @Override
    public void bindView(IShopDetailView view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void unbindView() {
        compositeSubscription.clear();
        view = null;
    }

    @Override
    public void setupShopDetails() {
        if (cash.isShopModelExist()) {
            setupShopDetailsFromCash();
        } else {
            getShopFromInteractor();
        }
    }

    private void setupShopDetailsFromCash() {
        ShopModel shopModel = cash.getShopModel();
        view.setTitle(shopModel.getName());
        view.setImage(shopModel.getImageUrl());
        view.setOwner(shopModel.getOwner().getName());
        view.setCoordinate(shopModel.getCoordinate());
    }

    private void getShopFromInteractor() {
        shopDetailInteractor.getShopById(cash.getShopId())
                .compose(rxSchedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleGetShopByIdSuccess, this::handleShopByIdError);
    }

    private void handleGetShopByIdSuccess(@NonNull ShopModel shopModel) {
        cash.setShopModel(shopModel);
        setupShopDetailsFromCash();
    }

    private void handleShopByIdError(Throwable throwable) {
        //TODO
        Log.d("TAG", "handleShopByIdError: " + throwable);
    }

    @Override
    public void onEditActionSelected() {
        view.showEditView(cash.getShopId());
    }

    @Override
    public void onRemoveActionSelected() {

    }

    @Override
    public void onEditShopResult(String shopId) {

    }
}
