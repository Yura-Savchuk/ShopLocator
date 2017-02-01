package com.example.shoplocator.ui.createAndEditShop.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.R;
import com.example.shoplocator.buissines.createAndEditShop.ICreateAndEditShopInteractor;
import com.example.shoplocator.buissines.createAndEditShop.exception.ShopFormIsInvalid;
import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;
import com.example.shoplocator.ui.createAndEditShop.presenter.formStrategy.IShopFormStrategy;
import com.example.shoplocator.ui.createAndEditShop.presenter.formStrategy.ShopFormFactory;
import com.example.shoplocator.ui.createAndEditShop.view.ICreateAndEditShopView;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by seotm on 27.01.17.
 */

public class CreateAndEditShopPresenter implements ICreateAndEditShopPresenter {

    private final CreateANdEditShopPresenterCash cash;
    private final ICreateAndEditShopInteractor interactor;
    private final RxSchedulersAbs rxSchedulers;
    private final Context context;

    private ICreateAndEditShopView view;
    private CompositeSubscription compositeSubscription;
    private IShopFormStrategy shopFormStrategy;

    public CreateAndEditShopPresenter(@NonNull ICreateAndEditShopInteractor interactor,
                                      @NonNull RxSchedulersAbs rxSchedulers,
                                      @NonNull Context context) {
        this.interactor = interactor;
        this.rxSchedulers = rxSchedulers;
        this.context = context;
        cash = new CreateANdEditShopPresenterCash();
    }

    @Override
    public void setShopId(String shopId) {
        cash.setShopId(shopId);
    }

    @Override
    public void bindView(ICreateAndEditShopView view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void unbindView() {
        compositeSubscription.clear();
        view = null;
    }

    @Override
    public void prepareForm() {
        shopFormStrategy = new ShopFormFactory(view, interactor, cash, rxSchedulers).createShopFormStrategy();
        compositeSubscription.add(shopFormStrategy.prepareForm());
    }

    @Override
    public void submitForm() {
        view.showProgress(true);
        ShopFormModel shopForm = getShopForm();
        Subscription subscription = interactor.validateForm(shopForm)
                .flatMap(shopFormStrategy::saveFormAndGetId)
                .compose(rxSchedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleSaveShopAndGetIdSuccess, this::handleSaveShopAndGetIdFailure);
        compositeSubscription.add(subscription);
    }

    private ShopFormModel getShopForm() {
        String shopName = view.getShopName();
        String imageUrl = view.getImageUrl();
        UserModel userModel = getSelectedUserModel();
        String posX = view.getPosX();
        String posY = view.getPosY();
        return new ShopFormModel(shopName, imageUrl, userModel, posX, posY);
    }

    @Nullable
    private UserModel getSelectedUserModel() {
        for (CheckableUserModel user : cash.getUsers()) {
            if (user.isSelected()) {
                return user;
            }
        }
        return null;
    }

    private void handleSaveShopAndGetIdSuccess(String shopId) {
        view.showProgress(false);
        view.returnSuccessResult(shopId);
        view.close();
    }

    private void handleSaveShopAndGetIdFailure(Throwable throwable) {
        view.showProgress(false);
        if (throwable instanceof ShopFormIsInvalid) {
            view.setInvalidErrors(((ShopFormIsInvalid) throwable).getInvalidFields());
            view.showErrorMessage(context.getString(R.string.one_or_more_fields_are_invalid));
        } else {
            view.showErrorMessage(context.getString(R.string.un_error_ocurred));
        }
    }

    @Override
    public void onTryAgainButtonClick() {
        view.showErrorView(false);
        prepareForm();
    }

}
