package com.example.shoplocator.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.shoplocator.BuildConfig;
import com.example.shoplocator.R;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(BuildConfig.APP_PREFERENCE_FILE_NAME);
        addPreferencesFromResource(R.xml.settings_preferences);
    }

}