package com.example.shoplocator.ui.model;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class UserModel {

    private final long id;
    private final String name;

    public UserModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
