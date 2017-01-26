package com.example.shoplocator.dagger.shopsList;

import com.example.shoplocator.buissines.shopsList.IShopsListInteractor;
import com.example.shoplocator.buissines.shopsList.ShopsListInteractor;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.shops.list.presenter.IShopsListPresenter;
import com.example.shoplocator.ui.shops.list.presenter.ShopsPresenter;
import com.example.shoplocator.ui.shops.list.presenter.ShopsPresenterCash;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

@Module
public class ShopsModule {

    @Provides
    @ShopsScope
    IShopsListPresenter shopsListPresenter(IShopsListInteractor shopsInteractor, RxSchedulersAbs rxSchedulers) {
        ShopsPresenterCash cash = new ShopsPresenterCash();
        return new ShopsPresenter(cash, shopsInteractor, rxSchedulers);
    }

    @Provides
    @ShopsScope
    IShopsListInteractor shopsInteractor(IShopsRepository shopsRepository, IUsersRepository usersRepository) {
        return new ShopsListInteractor(shopsRepository, usersRepository);
    }

}
