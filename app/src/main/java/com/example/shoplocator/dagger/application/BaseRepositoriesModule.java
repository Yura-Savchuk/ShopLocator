package com.example.shoplocator.dagger.application;

import android.content.Context;

import com.example.shoplocator.data.repsitory.ISettingsRepository;
import com.example.shoplocator.data.repsitory.SettingsRepository;
import com.example.shoplocator.data.sharedPreference.settings.ISettingsPrefService;
import com.example.shoplocator.data.sharedPreference.settings.SettingsPrefService;

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

}
