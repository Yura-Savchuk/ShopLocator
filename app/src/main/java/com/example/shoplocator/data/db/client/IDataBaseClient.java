package com.example.shoplocator.data.db.client;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by seotm on 23.01.17.
 */

public interface IDatabaseClient {

    Realm getRealm();

}
