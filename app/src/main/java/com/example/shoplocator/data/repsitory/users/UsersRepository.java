package com.example.shoplocator.data.repsitory.users;

import com.example.shoplocator.data.firebaseDb.users.IUsersFDBService;
import com.example.shoplocator.data.model.UserDbModel;

import java.util.List;

import rx.Single;
import rx.functions.Func1;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class UsersRepository implements IUsersRepository {

    private final IUsersFDBService usersFDBService;

    public UsersRepository(IUsersFDBService usersFDBService) {
        this.usersFDBService = usersFDBService;
    }

    @Override
    public Single<List<UserDbModel>> getUsers() {
        return usersFDBService.getUsers();
    }

    @Override
    public Single<UserDbModel> getUserById(long userId) {
        return getUsers()
                .map(userDbModels -> {
                    for (UserDbModel user : userDbModels) {
                        if (user.getId() == userId) {
                            return user;
                        }
                    }
                    return null;
                })
                .onErrorResumeNext(Single.just(null));
    }

}
