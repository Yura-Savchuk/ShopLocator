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

public class EditShopForm implements IShopFormStrategy {

    private final ICreateAndEditShopView view;
    private final ICreateAndEditShopInteractor interactor;
    private final CreateANdEditShopPresenterCash cash;
    private final RxSchedulersAbs rxSchedulers;

    public EditShopForm(ICreateAndEditShopView view, ICreateAndEditShopInteractor interactor, CreateANdEditShopPresenterCash cash, RxSchedulersAbs rxSchedulers) {
        this.view = view;
        this.interactor = interactor;
        this.cash = cash;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    public Subscription prepareForm() {
        if (!cash.isDataPrefilled()) {
            view.showProgress(true);
            return prefillFrom();
        } else {
            view.setupUserSelector(cash.getUsers());
            return Single.just(1).subscribe();
        }
    }

    private Subscription prefillFrom() {
        return Single.zip(getUsers(), getShopForm(), (users, from) -> {
            cash.setUsers(users);
            return from;
        })
                .compose(rxSchedulers.getIOToMainTransformerSingle())
                .subscribe(this::handlePrefillFormSuccess, this::handlePrefillFormError);
    }

    private Single<List<CheckableUserModel>> getUsers() {
        return interactor.getCheckableUsers();
    }

    private Single<ShopFormModel> getShopForm() {
        return interactor.getShopFormById(cash.getShopId());
    }

    private void handlePrefillFormSuccess(ShopFormModel formModel) {
        view.showProgress(false);
        cash.setDataPrefilled(true);
        view.setShopName(formModel.getName());
        view.setImageUrl(formModel.getImageUrl());
        sertupUserModels(formModel);
        view.setPositionX(formModel.getPosX());
        view.setPositionY(formModel.getPosY());
    }

    private void sertupUserModels(ShopFormModel formModel) {
        for (CheckableUserModel userModel : cash.getUsers()) {
            if (userModel.getId().equals(formModel.getUserModel().getId())) {
                userModel.setSelected(true);
                break;
            }
        }
        view.setupUserSelector(cash.getUsers());
    }

    private void handlePrefillFormError(Throwable throwable) {
        view.showProgress(false);
        view.showErrorMessage();
    }

    @Override
    public Single<String> saveFormAndGetId(@NonNull ShopFormModel fromModel) {
        return interactor.updateShopAndGetId(cash.getShopId(), fromModel);
    }
}
