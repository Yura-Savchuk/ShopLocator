package com.example.shoplocator.dagger.application;

import com.example.shoplocator.ui.splash.view.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by macbookpro on 13.11.16.
 */

@Component(modules = {AppModule.class, UtilsModule.class, DataModule.class, BaseRepositoriesModule.class})
@Singleton
public interface AppComponent {

    void inject(SplashActivity activity);

}
