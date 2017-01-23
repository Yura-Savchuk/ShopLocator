package com.example.shoplocator.data.db.users;

import com.example.shoplocator.data.db.client.IDatabaseClient;
import com.example.shoplocator.data.db.users.mapper.UserRealmObjectMapper;
import com.example.shoplocator.data.db.users.model.UserRealmObject;
import com.example.shoplocator.data.model.UserDbModel;

import java.util.List;

import io.realm.Realm;
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
    public Single<UserDbModel> getUserById(long userId) {
        return Single.fromCallable(() -> client.getRealm().where(UserRealmObject.class).equalTo(FIELD_USER_ID, userId).findFirst())
                .flatMap(userRealmObject -> {
                    if (userRealmObject == null) {
                        return Single.error(new RuntimeException("User by id: " + userId + " is missing."));
                    }
                    return Single.just(UserRealmObjectMapper.mapRealmToDb(userRealmObject));
                });
    }

    @Override
    public void setUsers(List<UserDbModel> users) {
        List<UserRealmObject> realmShops = UserRealmObjectMapper.mapDbToRealm(users);
        Realm realm = client.getRealm();
        realm.beginTransaction();
        realm.where(UserRealmObject.class).findAll().deleteAllFromRealm();
        for (UserRealmObject user : realmShops) {
            realm.copyToRealm(user);
        }
        realm.commitTransaction();
    }
}
