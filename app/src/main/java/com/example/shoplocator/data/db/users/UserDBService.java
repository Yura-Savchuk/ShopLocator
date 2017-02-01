package com.example.shoplocator.data.db.users;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.db.ListPrinter;
import com.example.shoplocator.data.db.client.IDatabaseClient;
import com.example.shoplocator.data.db.users.mapper.UserRealmObjectMapper;
import com.example.shoplocator.data.db.users.model.UserRealmObject;
import com.example.shoplocator.data.model.UserDbModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Single;

/**
 * Created by seotm on 23.01.17.
 */

public class UserDBService implements IUsersDBService {

    private static final String FIELD_USER_ID = "id";

    private final IDatabaseClient client;

    public UserDBService(IDatabaseClient client) {
        this.client = client;
    }

    @Override
    public Single<List<UserDbModel>> getUsers() {
        return Single.fromCallable(() -> client.getRealm().where(UserRealmObject.class).findAll())
                .map(UserRealmObjectMapper::mapRealmToDb);
    }

    @Override
    public Single<UserDbModel> getUserById(@NonNull String userId) {
        return Single.fromCallable(() -> client.getRealm().where(UserRealmObject.class).equalTo(FIELD_USER_ID, userId).findFirst())
                .flatMap(userRealmObject -> {
                    if (userRealmObject == null) {
                        return Single.error(new RuntimeException("User by id: " + userId + " is missing."));
                    }
                    return Single.just(UserRealmObjectMapper.mapRealmToDb(userRealmObject));
                });
    }

    @Override
    public Single<Object> setUsers(@NonNull List<UserDbModel> users) {
        return Single.fromCallable(() -> {
            List<UserRealmObject> realmShops = UserRealmObjectMapper.mapDbToRealm(users);
            Realm realm = client.getRealm();
            realm.beginTransaction();
            realm.where(UserRealmObject.class).findAll().deleteAllFromRealm();
            for (UserRealmObject user : realmShops) {
                realm.copyToRealm(user);
            }
            realm.commitTransaction();
            return null;
        });
    }

    @Override
    public Single<String> getDbStructure() {
        return Single.fromCallable(() -> {
            Realm realm = client.getRealm();
            RealmResults<UserRealmObject> users = realm.where(UserRealmObject.class).findAll();
            return new ListPrinter(users).toString();
        });
    }

    @Override
    public Single<Object> addUser(@NonNull UserDbModel userDbModel) {
        return Single.fromCallable(() -> {
            Realm realm = client.getRealm();
            UserRealmObject oldUser = realm.where(UserRealmObject.class).findFirst();
            UserRealmObject newUser = UserRealmObjectMapper.mapDbToRealm(userDbModel);
            realm.beginTransaction();
            if (oldUser != null) oldUser.deleteFromRealm();
            realm.copyToRealm(newUser);
            realm.commitTransaction();
            return null;
        });
    }
}
