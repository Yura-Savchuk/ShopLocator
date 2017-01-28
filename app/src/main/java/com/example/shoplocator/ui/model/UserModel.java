package com.example.shoplocator.ui.model;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class UserModel implements Cloneable {

    private final String id;
    private final String name;

    public UserModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public UserModel clone() {
        try {
            return (UserModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
