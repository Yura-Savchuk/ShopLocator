package com.example.shoplocator.dagger.application;

import com.example.shoplocator.ui.errorFragment.ErrorFragmentConfig;
import com.example.shoplocator.ui.errorFragment.ErrorFragmentFactory;
import com.example.shoplocator.ui.errorFragment.IErrorFragmentFactory;
import com.example.shoplocator.util.fragment.FragmentRoute;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;
import com.example.shoplocator.util.rx.schedulers.RxSchedulers;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;
import com.example.shoplocator.util.rx.validation.RxValidation;

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
    FragmentRouteAbs fragmentRoute() {
        return new FragmentRoute();
    }

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

    @Provides
    @Singleton
    RxValidation provideInternetConnectionUtil() {
        return new RxValidation();
    }

    @Provides
    @Singleton
    IErrorFragmentFactory provideErrorFragmentFactory() {
        return new ErrorFragmentFactory(new ErrorFragmentConfig());
    }

}
