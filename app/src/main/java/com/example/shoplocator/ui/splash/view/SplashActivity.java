package com.example.shoplocator.ui.splash.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.data.repsitory.settings.ISettingsRepository;
import com.example.shoplocator.ui.shops.ShopsListActivity;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

public class SplashActivity extends AppCompatActivity implements ISplashView {

    @Inject ISettingsRepository settingsRepository;
    @Inject RxSchedulersAbs schedulers;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().applicationComponent().inject(this);
        setContentView(R.layout.activity_splash);
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
        Intent intent = new Intent(SplashActivity.this, ShopsListActivity.class);
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
