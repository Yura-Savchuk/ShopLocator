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
        return getUsersFromFDb()
                .onErrorResumeNext(throwable -> getUsersFromDb());
    }

    private Single<List<UserDbModel>> getUsersFromFDb() {
        return usersFDBService.getUsers()
                .flatMap(userDbModels -> usersDBService.setUsers(userDbModels)
                        .map(o -> userDbModels));
    }

    private Single<List<UserDbModel>> getUsersFromDb() {
        return usersDBService.getUsers();
    }

    @Override
    public Single<UserDbModel> getUserById(@NonNull String userId) {
        return getUserByIdFromFDBService(userId)
                .onErrorResumeNext(throwable -> getUserByIdsFromDb(userId));
    }

    @NonNull
    private Single<UserDbModel> getUserByIdsFromDb(@NonNull final String userId) {
        return usersDBService.getUserById(userId);
    }

    private Single<UserDbModel> getUserByIdFromFDBService(String userId) {
        return usersFDBService.getUserById(userId)
                .flatMap(userDbModel -> usersDBService.addUser(userDbModel)
                        .map(o -> userDbModel));
    }

    @Override
    public Single<String> getLocalDbStructure() {
        return usersDBService.getDbStructure();
    }

}
