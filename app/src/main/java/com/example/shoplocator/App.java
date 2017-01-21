package com.example.shoplocator;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.shoplocator.dagger.application.AppComponent;
import com.example.shoplocator.dagger.application.AppModule;
import com.example.shoplocator.dagger.application.DaggerAppComponent;

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
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = prepareAppComponent().build();
    }

    @NonNull
    private DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this));
    }
}
