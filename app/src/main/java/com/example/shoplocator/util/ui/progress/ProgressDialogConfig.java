package com.example.shoplocator.util.ui.progress;

import android.graphics.Color;

/**
 * Created by seotm on 05.01.17.
 */

public class ProgressDialogConfig {

    private static final float DEFAULT_RADIUS_IN_DP = 5;
    private static final boolean DEFAULT_CANCELABLE = false;
    private static final int DEFAULT_CARD_COLOR = Color.WHITE;

    private static ProgressDialogConfig config;

    public synchronized static ProgressDialogConfig config() {
        if (config == null) {
            config = new ProgressDialogConfig();
        }
        return config;
    }

    float cornerRadiusInDP = DEFAULT_RADIUS_IN_DP;
    boolean cancelable = DEFAULT_CANCELABLE;
    int cardColor = DEFAULT_CARD_COLOR;

    public ProgressDialogConfig setCornerRadiusInDP(float cornerRadiusInDP) {
        this.cornerRadiusInDP = cornerRadiusInDP;
        return this;
    }

    public ProgressDialogConfig setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public ProgressDialogConfig setCardColor(int cardColor) {
        this.cardColor = cardColor;
        return this;
    }
}
