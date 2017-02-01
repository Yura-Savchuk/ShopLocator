package com.example.shoplocator.ui.users.list.presenter;

import com.example.shoplocator.buissines.usersList.IUsersListInteractor;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.ui.users.list.view.IUsersListView;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersListPresenter implements IUsersListPresenter {

    private final UsersListPresenterCash cash;
    private final IUsersListInteractor interactor;
    private final RxSchedulersAbs rxSchedulers;

    public UsersListPresenter(IUsersListInteractor interactor, RxSchedulersAbs rxSchedulers) {
        cash = new UsersListPresenterCash();
        this.interactor = interactor;
        this.rxSchedulers = rxSchedulers;
    }

    private IUsersListView view;
    private CompositeSubscription compositeSubscription;

    @Override
    public void bindView(IUsersListView view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void unbindView() {
        compositeSubscription.clear();
        view = null;
    }

    @Override
    public void setupUsersList() {
        if (cash.isUsersExist()) {
            setupUsersFromCash();
        } else {
            setupUsersFromInteractor();
        }
    }

    @Override
    public void onItemClick(int position) {
        UserModel user = cash.getUsers().get(position);
        view.showUserDetailView(user.getId(), user.getName());
    }

    private void setupUsersFromCash() {
        view.setupUsersList(cash.getUsers());
    }

    private void setupUsersFromInteractor() {
        view.setProgress(true);
        Subscription subscription = interactor.getSelectableUsers()
                .compose(rxSchedulers.getIOToMainTransformerSingle())
                .subscribe(this::handleGetSelectableUsersSuccess, this::handleGetSelectableUsersError);
        compositeSubscription.add(subscription);
    }

    private void handleGetSelectableUsersSuccess(List<UserModel> users) {
        view.setProgress(false);
        cash.setUsers(users);
        setupUsersFromCash();
    }

    private void handleGetSelectableUsersError(Throwable throwable) {
        view.setProgress(false);
        view.showErrorView(true);
    }

    @Override
    public void onRetryButtonClick() {
        view.showErrorView(false);
        setupUsersFromInteractor();
    }

}
