package com.example.shoplocator.data.fakeFdb.users;

import com.example.shoplocator.data.model.UserDbModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class FakeUsersListGenerator {

    public static final int USERS_COUNT = 5;
    public static final String USER_NAME = "User ";

    private final List<UserDbModel> userDbModels = new LinkedList<>();

    public List<UserDbModel> getNewList() {
        userDbModels.clear();
        for (int i=1; i<=USERS_COUNT; i++) {
            UserDbModel user = generateUser(i);
            userDbModels.add(user);
        }
        return userDbModels;
    }

    private UserDbModel generateUser(int index) {
        return new UserDbModel(String.valueOf(index), USER_NAME + index);
    }

}
