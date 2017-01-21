package com.example.shoplocator.dagger.application;

import android.content.Context;

import com.example.shoplocator.util.rx.RxSchedulers;
import com.example.shoplocator.util.rx.RxSchedulersAbs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by macbookpro on 13.11.16.
 */
@Module
public class UtilsModule {

//    @Provides
//    @Singleton
//    RateUtilAbs provideRateUtilAbs() {
//        return new RateUtil();
//    }

    @Provides
    @Singleton
    RxSchedulersAbs provideRxSchedulersAbs() {
        return new RxSchedulers();
    }

//    @Provides
//    @Singleton
//    NetworkUtil provideNetworkUtil() {
//        return new NetworkUtil();
//    }

//    @Provides
//    @Singleton
//    InternetConnectionUtil provideInternetConnectionUtil(Context context) {
//        return new InternetConnectionUtil(context);
//    }

}
