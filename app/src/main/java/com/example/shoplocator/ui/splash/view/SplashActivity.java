package com.example.shoplocator.ui.splash.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.shoplocator.App;
import com.example.shoplocator.data.repsitory.ISettingsRepository;
import com.example.shoplocator.ui.shops.ItemListActivity;
import com.example.shoplocator.util.rx.RxSchedulers;
import com.example.shoplocator.util.rx.RxSchedulersAbs;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity implements ISplashView {

    @Inject ISettingsRepository settingsRepository;
    @Inject RxSchedulersAbs schedulers;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().applicationComponent().inject(this);
        if (savedInstanceState == null) {
            startSplashTimer();
        }
    }

    private void startSplashTimer() {
        subscription = Observable.timer(settingsRepository.getSplashDelayInMillis(), TimeUnit.MILLISECONDS)
                .toSingle()
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(aLong -> {
                    startMainActivity();
                    finish();
                });
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, ItemListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            subscription.unsubscribe();
        }
    }
}
