package com.example.shoplocator.dagger.shopsMap;

import com.example.shoplocator.buissines.shopsList.IShopsListInteractor;
import com.example.shoplocator.buissines.shopsList.ShopsListInteractor;
import com.example.shoplocator.buissines.shopsMap.IShopsMapInteractor;
import com.example.shoplocator.buissines.shopsMap.ShopsMapInteractor;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.data.repsitory.users.UsersRepository;
import com.example.shoplocator.ui.shopsMap.presenter.IShopMapPresenter;
import com.example.shoplocator.ui.shopsMap.presenter.ShopMapPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

@Module
public class ShopsMapModule {

    @Provides
    @ShopsMapScope
    IShopMapPresenter shopMapPresenter(IShopsRepository shopRepository, IUsersRepository usersRepository) {
        IShopsMapInteractor interactor = new ShopsMapInteractor(shopRepository, usersRepository);
        return new ShopMapPresenter(interactor);
    }

}
