package com.example.shoplocator.dagger.createAndEditShop;

import com.example.shoplocator.buissines.createAndEditShop.CreateAndEditShopInterator;
import com.example.shoplocator.buissines.createAndEditShop.ICreateAndEditShopInteractor;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.createAndEditShop.presenter.CreateAndEditShopPresenter;
import com.example.shoplocator.ui.createAndEditShop.presenter.ICreateAndEditShopPresenter;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by seotm on 27.01.17.
 */

@Module
public class CreateAndEditShopModule {

    @Provides
    @CreateAndEditShopScope
    ICreateAndEditShopPresenter createAndEditShopPresenter(IShopsRepository shopsRepository,
                                                           IUsersRepository usersRepository,
                                                           RxSchedulersAbs rxSchedulers) {
        ICreateAndEditShopInteractor interactor = new CreateAndEditShopInterator(shopsRepository, usersRepository);
        return new CreateAndEditShopPresenter(interactor, rxSchedulers);
    }

}
