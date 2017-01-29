package com.example.shoplocator.data.fakeFdb.users;

import com.example.shoplocator.data.firebaseDb.users.IUsersFDBService;
import com.example.shoplocator.data.model.UserDbModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class FakeUsersFDBService implements IUsersFDBService {

    private static List<UserDbModel> users;

    public FakeUsersFDBService() {
        if (users == null)  {
            users = new FakeUsersListGenerator().getNewList();
        }
    }

    @Override
    public Single<List<UserDbModel>> getUsers() {
        return Single.just(users);
    }

    @Override
    public Single<UserDbModel> getUserById(String userId) {
        return Single.fromCallable(() -> {
            for (UserDbModel item : users) {
                if (item.getId().equals(userId)) {
                    return item;
                }
            }
            throw new RuntimeException("User by id: " + userId + "undefined.");
        });
    }
}
