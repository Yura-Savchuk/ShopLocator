package com.example.shoplocator.data.db.users.model;

import io.realm.RealmObject;

/**
 * Created by seotm on 23.01.17.
 */

public class UserRealmObject extends RealmObject {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
