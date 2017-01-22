package com.example.shoplocator.ui.shops.list.presenter;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.shopsList.IShopsListInteractor;
import com.example.shoplocator.ui.shops.list.view.IShopsListView;
import com.example.shoplocator.ui.model.ShopModel;
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
        shopsInteractor.getShops()
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleGetShopsSuccess, this::handleGetShopsError);
    }

    private void handleGetShopsSuccess(@NonNull List<ShopModel> shopModels) {
        cash.setShops(shopModels);
        setupShopsFromCash();
    }

    private void handleGetShopsError(Throwable throwable) {
        //TODO
    }

    @Override
    public void onItemClick(int position) {
        view.showShopDetail(cash.getShops().get(position).getId());
    }
}
