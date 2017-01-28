package com.example.shoplocator.ui.createAndEditShop.presenter.formStrategy;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.createAndEditShop.ICreateAndEditShopInteractor;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;
import com.example.shoplocator.ui.createAndEditShop.presenter.CreateANdEditShopPresenterCash;
import com.example.shoplocator.ui.createAndEditShop.view.ICreateAndEditShopView;

import rx.Single;
import rx.Subscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class EditShopForm implements IShopFormStrategy {

    private final ICreateAndEditShopView view;
    private final ICreateAndEditShopInteractor interactor;
    private final CreateANdEditShopPresenterCash cash;

    public EditShopForm(ICreateAndEditShopView view, ICreateAndEditShopInteractor interactor, CreateANdEditShopPresenterCash cash) {
        this.view = view;
        this.interactor = interactor;
        this.cash = cash;
    }

    @Override
    public Subscription prepareForm() {
        return null;
    }

    @Override
    public Single<Long> saveShopAndGetId(@NonNull ShopFormModel fromModel) {
        return null;
    }
}
