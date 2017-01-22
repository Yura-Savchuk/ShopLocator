package com.example.shoplocator.dagger.shopDetail;

import com.example.shoplocator.buissines.shopDetail.IShopDetailInteractor;
import com.example.shoplocator.buissines.shopDetail.ShopDetailInteractor;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.shops.detail.presenter.IShopDetailPresenter;
import com.example.shoplocator.ui.shops.detail.presenter.ShopDetailPresenter;
import com.example.shoplocator.util.rx.RxSchedulersAbs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

@Module
public class ShopDetailModule {

    @Provides
    @ShopDetailScope
    IShopDetailPresenter shopDetailPresenter(IShopDetailInteractor shopDetailInteractor, RxSchedulersAbs rxSchedulers) {
        return new ShopDetailPresenter(shopDetailInteractor, rxSchedulers);
    }

    @Provides
    @ShopDetailScope
    IShopDetailInteractor shopDetailInteractor(IShopsRepository shopsRepository, IUsersRepository usersRepository) {
        return new ShopDetailInteractor(shopsRepository, usersRepository);
    }

}
