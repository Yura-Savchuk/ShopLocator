package com.example.shoplocator;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.example.shoplocator.dagger.application.AppComponent;
import com.example.shoplocator.dagger.application.AppModule;
import com.example.shoplocator.dagger.application.DaggerAppComponent;
import com.example.shoplocator.util.ui.progress.ProgressDialogConfig;

import io.realm.Realm;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class App extends Application {

    private static App instance;

    // dagger2 appComponent
    @SuppressWarnings("NullableProblems")
    @NonNull
    private AppComponent appComponent;

    @NonNull
    public static App instance() {
        return instance;
    }

    @NonNull
    public AppComponent applicationComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = prepareAppComponent().build();
        Realm.init(this);
        ProgressDialogConfig.config().setCancelable(true);
    }

    @NonNull
    private DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this));
    }
}
