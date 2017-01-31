package com.example.shoplocator.ui.createAndEditShop.presenter.formStrategy;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.createAndEditShop.ICreateAndEditShopInteractor;
import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;
import com.example.shoplocator.ui.createAndEditShop.presenter.CreateANdEditShopPresenterCash;
import com.example.shoplocator.ui.createAndEditShop.view.ICreateAndEditShopView;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import java.util.List;

import rx.Single;
import rx.Subscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class CreateShopForm implements IShopFormStrategy {

    private final ICreateAndEditShopView view;
    private final ICreateAndEditShopInteractor interactor;
    private final CreateANdEditShopPresenterCash cash;
    private final RxSchedulersAbs rxSchedulers;

    public CreateShopForm(ICreateAndEditShopView view, ICreateAndEditShopInteractor interactor, CreateANdEditShopPresenterCash cash, RxSchedulersAbs rxSchedulers) {
        this.view = view;
        this.interactor = interactor;
        this.cash = cash;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    public Subscription prepareForm() {
        if (!cash.isUsersExist()) {
            view.showProgress(true);
            return interactor.getCheckableUsers()
                    .compose(rxSchedulers.getIOToMainTransformerSingle())
                    .subscribe(this::handleGetUsersSuccess, this::handleGetUsersError);
        }
        view.setupUserSelector(cash.getUsers());
        return Single.just(1).subscribe();
    }

    private void handleGetUsersSuccess(List<CheckableUserModel> users) {
        view.showProgress(false);
        cash.setUsers(users);
        view.setupUserSelector(users);
    }

    private void handleGetUsersError(Throwable throwable) {
        view.showProgress(false);
        view.showErrorMessage();
    }

    @Override
    public Single<String> saveFormAndGetId(@NonNull ShopFormModel fromModel) {
        return interactor.addShopAngGetId(fromModel);
    }

}
