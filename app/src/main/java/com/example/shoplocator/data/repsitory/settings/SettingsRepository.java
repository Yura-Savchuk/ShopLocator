package com.example.shoplocator.data.repsitory.settings;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.sharedPreference.settings.ISettingsPrefService;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class SettingsRepository implements ISettingsRepository {

    private final ISettingsPrefService settingsPrefService;

    public SettingsRepository(@NonNull ISettingsPrefService settingsPrefService) {
        this.settingsPrefService = settingsPrefService;
    }

    @Override
    public int getSplashDelayInMillis() {
        return settingsPrefService.getSplashDelayInMillis();
    }

    @Override
    public void setSplashDelayInMillis(int millis) {
        settingsPrefService.setSplashDelayInMillis(millis);
    }
}
