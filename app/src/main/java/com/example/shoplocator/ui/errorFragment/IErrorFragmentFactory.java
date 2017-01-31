package com.example.shoplocator.ui.errorFragment;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.errorFragment.fragment.ErrorFragment;
import com.example.shoplocator.ui.errorFragment.fragment.RetryButtonListener;

/**
 * Created by seotm on 05.01.17.
 */

public interface IErrorFragmentFactory {

    ErrorFragment createNoInternetConnectionFragment();
    ErrorFragment createUndefinedErrorFragment();

}
