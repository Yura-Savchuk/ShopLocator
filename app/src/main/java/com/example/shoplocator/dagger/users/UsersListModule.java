package com.example.shoplocator.dagger.users;

import com.example.shoplocator.buissines.users.IUsersListInteractor;
import com.example.shoplocator.buissines.users.UsersListInteractor;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.users.list.presenter.IUsersListPresenter;
import com.example.shoplocator.ui.users.list.presenter.UsersListPresenter;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

@Module
public class UsersListModule {

    @Provides
    @UsersListScope
    IUsersListPresenter usersListPresenter(IUsersRepository usersRepository, RxSchedulersAbs rxSchedulers) {
        IUsersListInteractor interactor = new UsersListInteractor(usersRepository);
        return new UsersListPresenter(interactor, rxSchedulers);
    }

}
