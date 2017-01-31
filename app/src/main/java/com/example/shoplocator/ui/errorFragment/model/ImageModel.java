package com.example.shoplocator.ui.errorFragment.model;

import android.support.annotation.DrawableRes;

/**
 * Created by seotm on 05.01.17.
 */

public class ImageModel {

    @DrawableRes
    private final int drawableRes;
    private boolean vectorImage;

    public ImageModel(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public boolean isVectorImage() {
        return vectorImage;
    }

    public void setVectorImage(boolean vectorImage) {
        this.vectorImage = vectorImage;
    }
}
