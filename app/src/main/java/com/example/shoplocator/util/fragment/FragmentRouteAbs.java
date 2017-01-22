package com.example.shoplocator.util.fragment;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by macbookpro on 13.11.16.
 */

public abstract class FragmentRouteAbs {

    protected abstract int fragmentContainer();

    public void addFragment(@NonNull FragmentActivity activity, @NonNull Fragment fragment) {
        String fragmentTag = getFragmentTag(fragment.getClass());
        activity.getSupportFragmentManager().beginTransaction()
                .add(fragmentContainer(), fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

    public void setFragment(@NonNull FragmentActivity activity, @NonNull Fragment fragment, @IdRes int fragmentContainer) {
        String fragmentTag = getFragmentTag(fragment.getClass());
        activity.getSupportFragmentManager().beginTransaction()
                .add(fragmentContainer, fragment, fragmentTag)
                .commit();
    }

    public void setFragment(@NonNull FragmentActivity activity, @NonNull Fragment fragment) {
        setFragment(activity, fragment, fragmentContainer());
    }

    @Nullable
    private static <T> String getFragmentTag(@NonNull Class<T> tClass) {
        if (tClass.isAnnotationPresent(FragmentTag.class)) {
            return tClass.getAnnotation(FragmentTag.class).tag();
        }
        return null;
    }

    @Nullable
    public Fragment getCurrentFragment(@NonNull FragmentActivity activity) {
        return activity.getSupportFragmentManager().findFragmentById(fragmentContainer());
    }

    public void replaceFragment(@NonNull FragmentActivity activity, @NonNull Fragment fragment, @IdRes int containerId) {
        String fragmentTag = getFragmentTag(fragment.getClass());
        activity.getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, fragmentTag)
                .commit();
    }

    public void replaceFragmentWithBackStack(@NonNull FragmentActivity activity, @NonNull Fragment fragment) {
        String fragmentTag = getFragmentTag(fragment.getClass());
        activity.getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer(), fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

}
