package com.example.shoplocator.ui.errorFragment;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.errorFragment.fragment.ErrorFragment;
import com.example.shoplocator.ui.errorFragment.fragment.RetryButtonListener;

/**
 * Created by seotm on 05.01.17.
 */

public class ErrorFragmentFactory implements IErrorFragmentFactory {

    private final IErrorFragmentConfig config;

    public ErrorFragmentFactory(IErrorFragmentConfig config) {
        this.config = config;
    }

    @Override
    public ErrorFragment createNoInternetConnectionFragment() {
        return ErrorFragment.create(config.noInternetConnectionModel());
    }

    @Override
    public ErrorFragment createUndefinedErrorFragment() {
        return ErrorFragment.create(config.undefinedError());
    }
}
