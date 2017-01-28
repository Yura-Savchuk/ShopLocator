package com.example.shoplocator.buissines.createAndEditShop.validation.util;

import android.content.Context;

import com.example.shoplocator.R;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class ValidationUtil extends ValidationUtilAbs {

    private final Context context;

    public ValidationUtil(Context context) {
        this.context = context;
    }

    @Override
    public String emptyNameText() {
        return context.getString(R.string.name_must_be_not_empty);
    }

    @Override
    public String emptyImageUrl() {
        return context.getString(R.string.image_url_must_be_not_empty);
    }

    @Override
    public String imageUrlMustStartWithHttp() {
        return context.getString(R.string.image_url_must_start_with);
    }

    @Override
    public String userMustBeSelected() {
        return context.getString(R.string.user_must_be_selected);
    }

    @Override
    public String emptyPosition() {
        return context.getString(R.string.coord_position_must_be_selected);
    }

    @Override
    public String invalidPosition() {
        return context.getString(R.string.coord_position_must_be_valid_float_number);
    }
}
