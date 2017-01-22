package com.example.shoplocator.dagger.application;

import android.content.Context;

import com.example.shoplocator.data.firebaseDb.shops.IShopsFDBService;
import com.example.shoplocator.data.firebaseDb.shops.ShopsFDBService;
import com.example.shoplocator.data.firebaseDb.users.IUsersFDBService;
import com.example.shoplocator.data.firebaseDb.users.UsersFDBService;
import com.example.shoplocator.data.repsitory.settings.ISettingsRepository;
import com.example.shoplocator.data.repsitory.settings.SettingsRepository;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.shops.ShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.data.repsitory.users.UsersRepository;
import com.example.shoplocator.data.sharedPreference.settings.ISettingsPrefService;
import com.example.shoplocator.data.sharedPreference.settings.SettingsPrefService;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 16.01.17.
 */

@Module
public class BaseRepositoriesModule {

    @Provides
    @Singleton
    ISettingsRepository settingsRepository(Context context) {
        ISettingsPrefService prefService = new SettingsPrefService(context);
        return new SettingsRepository(prefService);
    }

    @Provides
    @Singleton
    IUsersRepository usersRepository(FirebaseDatabase firebaseDatabase) {
        IUsersFDBService usersFDBService = new UsersFDBService(firebaseDatabase);
        return new UsersRepository(usersFDBService);
    }

    @Provides
    @Singleton
    IShopsRepository shopsRepository(FirebaseDatabase firebaseDatabase) {
        IShopsFDBService shopsFDBService = new ShopsFDBService(firebaseDatabase);
        return new ShopsRepository(shopsFDBService);
    }

}
