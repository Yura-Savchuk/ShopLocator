package com.example.shoplocator.buissines.createAndEditShop.validation.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.util.validation.ValidationUtilHelper;

/**
 * Created by macbookpro on 04.12.16.
 */

public abstract class ValidationUtilAbs {

    private static final String HTTP = "http";

    public abstract String emptyNameText();
    public abstract String emptyImageUrl();
    public abstract String imageUrlMustStartWithHttp();
    public abstract String userMustBeSelected();
    public abstract String emptyPosition();
    public abstract String invalidPosition();

    private String message;

    public String getMessage() {
        return message;
    }

    public boolean isValidName(@NonNull String name) {
        if (name.isEmpty()) {
            message = emptyNameText();
            return false;
        }
        return true;
    }

    public boolean isValidImageUrl(@NonNull String imageUrl) {
        if (imageUrl.isEmpty()) {
            message = emptyImageUrl();
            return false;
        }
        if (!imageUrl.toLowerCase().trim().startsWith(HTTP)) {
            message = imageUrlMustStartWithHttp();
            return false;
        }
        return true;
    }

    public boolean isValidUser(@Nullable UserModel userModel) {
        if (userModel == null) {
            message = userMustBeSelected();
            return false;
        }
        return true;
    }

    public boolean isValidPosition(@NonNull String position) {
        if (position.isEmpty()) {
            message = emptyPosition();
            return false;
        }
        if (!ValidationUtilHelper.isValidDouble(position)) {
            message = invalidPosition();
            return false;
        }
        return true;
    }

}
