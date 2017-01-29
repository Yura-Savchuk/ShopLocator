package com.example.shoplocator.data.repsitory.users;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.db.users.IUsersDBService;
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
    private final IUsersDBService usersDBService;

    public UsersRepository(@NonNull IUsersFDBService usersFDBService, @NonNull IUsersDBService usersDBService) {
        this.usersFDBService = usersFDBService;
        this.usersDBService = usersDBService;
    }

    @Override
    public Single<List<UserDbModel>> getUsers() {
        return usersDBService.getUsers()
                .flatMap(userDbModels -> {
                    if (userDbModels == null || userDbModels.isEmpty()) {
                        return usersFDBService.getUsers()
                                .doOnSuccess(usersDBService::setUsers);
                    }
                    return Single.just(userDbModels);
                });
    }

    @Override
    public Single<UserDbModel> getUserById(@NonNull String userId) {
        return usersDBService.getUserById(userId)
                .onErrorResumeNext(new Func1<Throwable, Single<UserDbModel>>() {
                    @Override
                    public Single<UserDbModel> call(Throwable throwable) {
                        return getUserByIdFromFDBService(userId);
                    }
                });
    }

    private Single<UserDbModel> getUserByIdFromFDBService(String userId) {
        return getUsers().map(userDbModels -> {
            for (UserDbModel user : userDbModels) {
                if (user.getId().equals(userId)) {
                    return user;
                }
            }
            return null;
        });
    }

    @Override
    public Single<String> getLocalDbStructure() {
        return usersDBService.getDbStructure();
    }

}
