package com.example.shoplocator.buissines.createAndEditShop;

import com.example.shoplocator.buissines.createAndEditShop.validation.util.ValidationUtilAbs;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 30.01.17.
 */

public class ValidationUtilTest extends ValidationUtilAbs {
    @Override
    public String emptyNameText() {
        return "Empty name";
    }

    @Override
    public String emptyImageUrl() {
        return "Empty Url";
    }

    @Override
    public String imageUrlMustStartWithHttp() {
        return "Image url must be whith http";
    }

    @Override
    public String userMustBeSelected() {
        return "User must be selected";
    }

    @Override
    public String emptyPosition() {
        return "Empty position";
    }

    @Override
    public String invalidPosition() {
        return "Invalid position";
    }
}
