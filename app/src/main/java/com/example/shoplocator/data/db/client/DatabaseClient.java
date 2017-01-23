package com.example.shoplocator.data.db.client;

import com.example.shoplocator.BuildConfig;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by seotm on 23.01.17.
 */

public class DatabaseClient implements IDatabaseClient {

    private final RealmConfiguration configuration;

    public DatabaseClient() {
        configuration = new RealmConfiguration.Builder()
                .schemaVersion(BuildConfig.DATA_BASE_VERSION)
                .build();
    }

    @Override
    public Realm getRealm() {
        return Realm.getInstance(configuration);
    }
}
