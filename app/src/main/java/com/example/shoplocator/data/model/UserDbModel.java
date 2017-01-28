package com.example.shoplocator.data.model;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class UserDbModel {

    private final String id;
    private final String name;

    public UserDbModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
