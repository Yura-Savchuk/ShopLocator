package com.example.shoplocator.data.db.users;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.UserDbModel;

import java.util.List;

import rx.Single;

/**
 * Created by seotm on 23.01.17.
 */

public interface IUsersDBService {

    Single<List<UserDbModel>> getUsers();
    Single<UserDbModel> getUserById(@NonNull String userId);
    void setUsers(@NonNull List<UserDbModel> users);

    Single<String> getDbStructure();
}
