package com.example.shoplocator.ui.errorFragment.model;

import android.support.annotation.StringRes;

/**
 * Created by seotm on 05.01.17.
 */

public class ErrorViewModel {

    private final ImageModel imageModel;
    @StringRes
    private final int titleStringRes;
    @StringRes
    private final int descriptionStringRes;

    public ErrorViewModel(ImageModel imageModel, int titleStringRes, int descriptionStringRes) {
        this.imageModel = imageModel;
        this.titleStringRes = titleStringRes;
        this.descriptionStringRes = descriptionStringRes;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public int getTitleStringRes() {
        return titleStringRes;
    }

    public int getDescriptionStringRes() {
        return descriptionStringRes;
    }
}
