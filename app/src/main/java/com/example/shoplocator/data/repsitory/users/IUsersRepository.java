package com.example.shoplocator.data.repsitory.users;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public interface IUsersRepository {

    Single<List<UserDbModel>> getUsers();
    Single<UserDbModel> getUserById(@NonNull String userId);
}
