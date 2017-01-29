package com.example.shoplocator.dagger.userDetail;

import com.example.shoplocator.buissines.userDetail.IUserDetailInteractor;
import com.example.shoplocator.buissines.userDetail.UserDetailInteractor;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.ui.users.detail.presenter.IUserDetailPresenter;
import com.example.shoplocator.ui.users.detail.presenter.UserDetailPresenter;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

@Module
public class UserDetailModule {

    @Provides
    @UserDetailScope
    IUserDetailPresenter userDetailPresenter(IShopsRepository shopsRepository, RxSchedulersAbs rxSchedulers) {
        IUserDetailInteractor iUserDetailInteractor = new UserDetailInteractor(shopsRepository);
        return new UserDetailPresenter(iUserDetailInteractor, rxSchedulers);
    }

}
