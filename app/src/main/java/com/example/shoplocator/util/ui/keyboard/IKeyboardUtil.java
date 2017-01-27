package com.example.shoplocator.util.ui.keyboard;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by seotm on 20.01.17.
 */

public interface IKeyboardUtil {

    boolean isKeyboardShown(@NonNull Activity activity);
    void hideKeyboardIfShown(@NonNull Activity activity);

}
