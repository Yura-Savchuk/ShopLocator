package com.example.shoplocator.ui.createAndEditShop.presenter.formStrategy;

import com.example.shoplocator.buissines.createAndEditShop.ICreateAndEditShopInteractor;
import com.example.shoplocator.ui.createAndEditShop.CreatAndEditShopActivity;
import com.example.shoplocator.ui.createAndEditShop.presenter.CreateANdEditShopPresenterCash;
import com.example.shoplocator.ui.createAndEditShop.view.ICreateAndEditShopView;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class ShopFormFactory {

    private final ICreateAndEditShopView view;
    private final ICreateAndEditShopInteractor interactor;
    private final CreateANdEditShopPresenterCash cash;
    private final RxSchedulersAbs rxSchedulers;

    public ShopFormFactory(ICreateAndEditShopView view, ICreateAndEditShopInteractor interactor, CreateANdEditShopPresenterCash cash, RxSchedulersAbs rxSchedulers) {
        this.view = view;
        this.interactor = interactor;
        this.cash = cash;
        this.rxSchedulers = rxSchedulers;
    }

    public IShopFormStrategy createShopFormStrategy() {
        if (cash.getShopId() == CreatAndEditShopActivity.EMPTY_VALUE) {
            return new CreateShopForm(view, interactor, cash, rxSchedulers);
        }
        return new EditShopForm(view, interactor, cash);
    }

}
