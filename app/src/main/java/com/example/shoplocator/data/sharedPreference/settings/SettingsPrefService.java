package com.example.shoplocator.data.sharedPreference.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.shoplocator.BuildConfig;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class SettingsPrefService implements ISettingsPrefService {

    private static final String PREFERENCE_NAME = "SETTINGS";
    private static final String PARAM_SPLASH_DELAY = "splash_delay";

    private final Context context;

    public SettingsPrefService(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int getSplashDelayInMillis() {
        return getIntValue(PARAM_SPLASH_DELAY, BuildConfig.DEFAULT_SPLASH_DELAY_MILLIS);
    }

    private int getIntValue(@NonNull String paramName, int defaultValue) {
        return getPrefs().getInt(paramName, defaultValue);
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setSplashDelayInMillis(int millis) {
        setInt(PARAM_SPLASH_DELAY, millis);
    }

    private void setInt(String paramName, int value) {
        getPrefs().edit()
                .putInt(paramName, value)
                .apply();
    }
}
