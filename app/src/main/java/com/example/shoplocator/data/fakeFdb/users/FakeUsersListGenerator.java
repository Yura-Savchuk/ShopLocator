package com.example.shoplocator.data.fakeFdb.users;

import com.example.shoplocator.data.model.UserDbModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class FakeUsersListGenerator {

    private final List<UserDbModel> userDbModels = new LinkedList<UserDbModel>() {{
        add(new UserDbModel("1", "User 1"));
        add(new UserDbModel("2", "User 2"));
        add(new UserDbModel("3", "User 3"));
        add(new UserDbModel("4", "User 4"));
        add(new UserDbModel("5", "User 5"));
    }};

    public List<UserDbModel> getNewList() {
        return userDbModels;
    }

}
