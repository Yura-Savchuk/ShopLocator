package com.example.shoplocator.ui.shops.list.presenter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.shoplocator.buissines.shopsList.IShopsListInteractor;
import com.example.shoplocator.buissines.shopsList.listTransformation.ChangeItemsCommand;
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
        Subscription subscription = shopsInteractor.getCheckableShops()
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleGetShopsSuccess, this::handleGetShopsError);
        compositeSubscription.add(subscription);
    }

    private void handleGetShopsSuccess(@NonNull List<SelectableShopModel> shopModels) {
        cash.setShops(shopModels);
        setupShopsFromCash();
        view.showProgress(false);
    }

    private void handleGetShopsError(Throwable throwable) {
        view.showProgress(false);
        view.showErrorView();
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
    public void onRemoveActionSelected() {
        cash.setToolbarInEditState(true);
        setupToolbarState();
    }

    @Override
    public void onDoneActionSelected() {
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
    public void onCancelActionSelected() {
        setToolbarNoInRemoveState();
        deselectSelectedShops();
    }

    private void deselectSelectedShops() {
        ChangeItemsCommand command = shopsInteractor.deselectSelectedShops(cash.getShops());
        command.executeWithListener(position -> view.notifyItemChanged(position));
    }

    @Override
    public void onCreateActionSelected() {
        view.showCreateShopView();
    }

    @Override
    public void addShopById(@NonNull String shopId) {
        Subscription subscription = shopsInteractor.addShopById(cash.getShops(), shopId)
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleAddShopByIdSuccess, throwable -> {});
        compositeSubscription.add(subscription);
    }

    private void handleAddShopByIdSuccess(ChangeItemsCommand command) {
        command.executeWithListener(position -> view.notifyItemInserted(position));
    }

    @Override
    public void onEditShopResult(@NonNull String shopId) {
        Subscription subscription = shopsInteractor.updateShopById(cash.getShops(), shopId)
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleUpdateShopByIdSuccess, throwable -> {});
        compositeSubscription.add(subscription);
    }

    private void handleUpdateShopByIdSuccess(ChangeItemsCommand command) {
        command.executeWithListener(position -> view.notifyItemChanged(position));
    }

    @Override
    public void onDeleteShopResult(@NonNull String shopId) {
        List<SelectableShopModel> shops = cash.getShops();
        for (int i=0; i<shops.size(); i++) {
            SelectableShopModel item = shops.get(i);
            if (item.getId().equals(shopId)) {
                shops.remove(i);
                view.notifyItemRemoved(i);
                break;
            }
        }
    }

}


