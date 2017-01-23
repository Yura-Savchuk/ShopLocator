package com.example.shoplocator.ui.shops.list.presenter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.shoplocator.buissines.shopsList.IShopsListInteractor;
import com.example.shoplocator.ui.shops.list.view.IShopsListView;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.CheckableShopModel;
import com.example.shoplocator.util.rx.RxSchedulersAbs;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsPresenter implements IShopsListPresenter {

    private final ShopsPresenterCash cash;
    private final IShopsListInteractor shopsInteractor;
    private final RxSchedulersAbs schedulers;

    private IShopsListView view;

    public ShopsPresenter(@NonNull ShopsPresenterCash cash,
                          @NonNull IShopsListInteractor shopsInteractor,
                          @NonNull RxSchedulersAbs schedulers) {
        this.cash = cash;
        this.shopsInteractor = shopsInteractor;
        this.schedulers = schedulers;
    }

    @Override
    public void bindView(IShopsListView view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void setupShopsList() {
        if (cash.isShopsExist()) {
            setupShopsFromCash();
        } else {
            setupShopsFromInteractor();
        }
    }

    private void setupShopsFromCash() {
        view.setupShopsList(cash.getShops());
    }

    private void setupShopsFromInteractor() {
        view.showProgress(true);
        shopsInteractor.getCheckableShops()
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleGetShopsSuccess, this::handleGetShopsError);
    }

    private void handleGetShopsSuccess(@NonNull List<CheckableShopModel> shopModels) {
        cash.setShops(shopModels);
        setupShopsFromCash();
        view.showProgress(false);
    }

    private void handleGetShopsError(Throwable throwable) {
        //TODO
        view.showProgress(false);
    }

    @Override
    public void onItemClick(int position, View itemView) {
        view.showShopDetail(cash.getShops().get(position).getId(), itemView);
    }
}
