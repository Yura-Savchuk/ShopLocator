package com.example.shoplocator.util.ui.progress;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by seotm on 05.01.17.
 */

public class ProgressDialogValueConverter {

    static int dpToPx(float dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
