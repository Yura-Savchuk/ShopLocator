package com.example.shoplocator.dagger.createAndEditShop;

import android.content.Context;

import com.example.shoplocator.buissines.createAndEditShop.CreateAndEditShopInterator;
import com.example.shoplocator.buissines.createAndEditShop.ICreateAndEditShopInteractor;
import com.example.shoplocator.buissines.createAndEditShop.validation.IShopFormValidation;
import com.example.shoplocator.buissines.createAndEditShop.validation.ShopFormValidation;
import com.example.shoplocator.buissines.createAndEditShop.validation.util.ValidationUtil;
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
                                                           RxSchedulersAbs rxSchedulers,
                                                           Context context) {
        IShopFormValidation validation = new ShopFormValidation(new ValidationUtil(context));
        ICreateAndEditShopInteractor interactor = new CreateAndEditShopInterator(shopsRepository, usersRepository, validation);
        return new CreateAndEditShopPresenter(interactor, rxSchedulers, context);
    }

}
