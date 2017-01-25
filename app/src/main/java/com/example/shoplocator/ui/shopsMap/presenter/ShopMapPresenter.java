package com.example.shoplocator.ui.shopsMap.presenter;

import android.os.Bundle;

import com.example.shoplocator.buissines.shopsList.IShopsListInteractor;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shopsMap.view.IShopMapView;

import java.util.List;

import rx.Single;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

public class ShopMapPresenter implements IShopMapPresenter {

    private final ShopsMapPresenterCash cash;
    private final IShopsListInteractor shopsListInteractor;

    private IShopMapView view;
    private CompositeSubscription compositeSubscription;

    public ShopMapPresenter(IShopsListInteractor shopsListInteractor) {
        this.shopsListInteractor = shopsListInteractor;
        cash = new ShopsMapPresenterCash();
    }

    @Override
    public void bindView(IShopMapView view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void unbindView() {
        compositeSubscription.clear();
        view = null;
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        cash.saveInstanceState(outState);
    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
        cash.restoreInstanceState(savedInstanceState);
    }

    @Override
    public void loadShopsAndControlAccessablity(Single<Object> single) {
        view.shopProgress(true);
        Subscription subscription = Single.zip(getShops(), single, (shops, o) -> shops)
                .subscribe(this::handleLoadShopsSuccess, this::handleLoadShopsError);
        compositeSubscription.add(subscription);
    }

    private Single<List<ShopModel>> getShops() {
        if (cash.isShopsExist()) {
            return Single.just(cash.getShops());
        }
        return shopsListInteractor.getShops();
    }

    private void handleLoadShopsError(Throwable throwable) {
        view.shopProgress(false);
    }

    private void handleLoadShopsSuccess(List<ShopModel> shops) {
        cash.setShops(shops);
        view.shopProgress(false);
        view.setupShopsList(shops);
        view.setupShopMapkers(shops);
        setMapCursorToSelectedPosition();
        view.setShopByPositionOnPager(cash.getSelectedShopPosition());
    }

    private void setMapCursorToSelectedPosition() {
        ShopModel shopModel = cash.getShops().get(cash.getSelectedShopPosition());
        view.setMapCursorToCoordinate(shopModel.getCoordinate());
    }

    @Override
    public void onShopPositionChanged(int position) {
        cash.setSelectedShopPosition(position);
        setMapCursorToSelectedPosition();
    }
}
