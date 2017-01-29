package com.example.shoplocator.data.firebaseDb.users;

import com.example.shoplocator.data.model.UserDbModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public interface IUsersFDBService {

    Single<List<UserDbModel>> getUsers();
    Single<UserDbModel> getUserById(String userId);

}
