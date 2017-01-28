package com.example.shoplocator.buissines.createAndEditShop.exception;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.createAndEditShop.validation.field.ShopFormInvalidField;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class ShopFormIsInvalid extends Throwable {

    private final List<ShopFormInvalidField> invalidFields;

    public ShopFormIsInvalid(@NonNull List<ShopFormInvalidField> invalidFields) {
        this.invalidFields = invalidFields;
    }

    public List<ShopFormInvalidField> getInvalidFields() {
        return invalidFields;
    }
}
