package com.example.shoplocator.dagger.application;

import com.example.shoplocator.data.db.client.DatabaseClient;
import com.example.shoplocator.data.db.client.IDatabaseClient;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by macbookpro on 29.11.16.
 */
@Module
public class DataModule {

//    @Provides
//    @Singleton
//    INetworkManager provideApiConnection(Context context, NetworkUtil networkUtil) {
//        return new NetworkManager(context, Credential.DOMAIN, networkUtil);
//    }

    @Provides
    @Singleton
    FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    IDatabaseClient databaseClient() {
        return new DatabaseClient();
    }

}
