package com.example.shoplocator.util.ui.keyboard;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by seotm on 20.01.17.
 */

public class KeyboardUtil implements IKeyboardUtil {

    private final View dummyFocusableView;

    public KeyboardUtil(@NonNull View dummyFocusableView) {
        this.dummyFocusableView = dummyFocusableView;
    }

    @Override
    public boolean isKeyboardShown(@NonNull Activity activity) {
        View focusableView = activity.getCurrentFocus();
        return focusableView != null && focusableView != dummyFocusableView;
    }

    @Override
    public void hideKeyboardIfShown(@Nullable Activity activity) {
        if (activity != null) clearFocusFromView(activity.getCurrentFocus(), activity);
        dummyFocusableView.requestFocus();
    }

    private void clearFocusFromView(@Nullable View focusableView, @NonNull Activity activity) {
        if (focusableView != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusableView.getWindowToken(), 0);
            focusableView.clearFocus();
        }
    }

}
