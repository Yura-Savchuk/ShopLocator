package com.example.shoplocator.ui.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.data.repsitory.settings.ISettingsRepository;
import com.example.shoplocator.ui.shops.ShopsListActivity;
import com.example.shoplocator.util.rx.schedulers.RxSchedulersAbs;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

/**
 * Created by seotm on 01.02.17.
 */

public class SplashFragment extends Fragment {

    @Inject ISettingsRepository settingsRepository;
    @Inject RxSchedulersAbs schedulers;

    private Subscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().applicationComponent().inject(this);
        setRetainInstance(true);
        startSplashTimer();
    }

    private void startSplashTimer() {
        subscription = Observable.timer(settingsRepository.getSplashDelayInMillis(), TimeUnit.MILLISECONDS)
                .toSingle()
                .compose(schedulers.getIOToMainTransformerSingle())
                .subscribe(aLong -> {
                    startMainActivity();
                    getActivity().finish();
                });
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), ShopsListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}
