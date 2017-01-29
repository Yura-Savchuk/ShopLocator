package com.example.shoplocator.data.db.users.model;

import io.realm.RealmObject;

/**
 * Created by seotm on 23.01.17.
 */

public class UserRealmObject extends RealmObject {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
