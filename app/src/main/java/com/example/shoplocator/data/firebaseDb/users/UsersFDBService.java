package com.example.shoplocator.data.firebaseDb.users;

import com.example.shoplocator.data.firebaseDb.RealTimeDatabaseConfig;
import com.example.shoplocator.data.model.UserDbModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Single;
import rx.SingleSubscriber;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class UsersFDBService implements IUsersFDBService {

    private static final String DATABASE_KEY_USERS = "persons";

    private static final String PARAM_ID = "id";
    private static final String PARAM_NAME = "name";

    private final DatabaseReference shopsDataRefrence;

    public UsersFDBService(FirebaseDatabase firebaseDatabase) {
        this.shopsDataRefrence = firebaseDatabase.getReference().child(DATABASE_KEY_USERS);
    }

    @Override
    public Single<List<UserDbModel>> getUsers() {
        return Single.create((Single.OnSubscribe<List<UserDbModel>>)this::loadUsersAndEmit)
                .timeout(RealTimeDatabaseConfig.SECONDS_TIME_OUT, TimeUnit.SECONDS);
    }

    private void loadUsersAndEmit(SingleSubscriber<? super List<UserDbModel>> subscriber) {
        shopsDataRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserDbModel> shopDbModels = getUsersFromData(dataSnapshot);
                subscriber.onSuccess(shopDbModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });
    }

    private List<UserDbModel> getUsersFromData(DataSnapshot dataSnapshot) {
        List<UserDbModel> users = new ArrayList<>();
        for (DataSnapshot userData : dataSnapshot.getChildren()) {
            UserDbModel shop = getUserFromData(userData);
            users.add(shop);
        }
        return users;
    }

    private UserDbModel getUserFromData(DataSnapshot dataSnapshot) {
        String id = dataSnapshot.child(PARAM_ID).getValue(String.class);
        String name = dataSnapshot.child(PARAM_NAME).getValue(String.class);
        return new UserDbModel(id, name);
    }

}
