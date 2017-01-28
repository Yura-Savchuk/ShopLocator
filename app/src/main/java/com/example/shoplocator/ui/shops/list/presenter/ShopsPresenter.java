package com.example.shoplocator.ui.shops.list.presenter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.shoplocator.buissines.shopsList.IShopsListInteractor;
import com.example.shoplocator.buissines.shopsList.commands.ChangeItemsCommand;
import com.example.shoplocator.ui.shops.list.view.IShopsListView;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsPresenter implements IShopsListPresenter {

    private final ShopsPresenterCash cash;
    private final IShopsListInteractor shopsInteractor;
    private final RxSchedulersAbs schedulers;

    private IShopsListView view;
    private CompositeSubscription compositeSubscription;

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
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void unbindView() {
        compositeSubscription.clear();
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

    private void handleGetShopsSuccess(@NonNull List<SelectableShopModel> shopModels) {
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
        if (cash.isToolbarInEditState()) {
            SelectableShopModel shop = cash.getShops().get(position);
            shop.setSelected(!shop.isSelected());
            view.notifyItemChanged(position);
        } else {
            view.showShopDetail(cash.getShops().get(position).getId(), itemView);
        }
    }

    @Override
    public void setupToolbarState() {
        view.setToolbarInEditState(cash.isToolbarInEditState());
    }

    @Override
    public void setToolbarInRemoveState() {
        cash.setToolbarInEditState(true);
        setupToolbarState();
    }

    @Override
    public void removeSelectedShops() {
        setToolbarNoInRemoveState();
        Subscription subscription = shopsInteractor.removeSelectedShops(cash.getShops())
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleRemoveSelectedShops);
        compositeSubscription.add(subscription);
    }

    private void handleRemoveSelectedShops(@NonNull ChangeItemsCommand deleteShopsCommand) {
        deleteShopsCommand.executeWithListener(position -> view.notifyItemRemoved(position));
    }

    private void setToolbarNoInRemoveState() {
        cash.setToolbarInEditState(false);
        setupToolbarState();
    }

    @Override
    public void cancelRemoveShops() {
        setToolbarNoInRemoveState();
        deselectSelectedShops();
    }

    private void deselectSelectedShops() {
        ChangeItemsCommand command = shopsInteractor.deselectSelectedShops(cash.getShops());
        command.executeWithListener(position -> view.notifyItemChanged(position));
    }

    @Override
    public void onCreateActionSelection() {
        view.showCreateShopView();
    }

    @Override
    public void addShopById(String shopId) {

    }
}
