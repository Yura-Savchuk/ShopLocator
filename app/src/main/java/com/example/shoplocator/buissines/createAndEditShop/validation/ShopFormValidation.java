package com.example.shoplocator.buissines.createAndEditShop.validation;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.createAndEditShop.exception.ShopFormIsInvalid;
import com.example.shoplocator.buissines.createAndEditShop.validation.field.ShopFormInvalidField;
import com.example.shoplocator.buissines.createAndEditShop.validation.util.ValidationUtilAbs;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;

import java.util.LinkedList;
import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class ShopFormValidation implements IShopFormValidation {

    private final List<ShopFormInvalidField> invalidFields;
    private final ValidationUtilAbs validationUtil;

    public ShopFormValidation(ValidationUtilAbs validationUtil) {
        this.validationUtil = validationUtil;
        invalidFields = new LinkedList<>();
    }

    @Override
    public Single<ShopFormModel> validate(@NonNull ShopFormModel formModel) {
        return Single.create(subscriber -> {
                invalidFields.clear();
                validateForm(formModel);
                if (invalidFields.isEmpty()) {
                    subscriber.onSuccess(formModel);
                } else {
                    subscriber.onError(new ShopFormIsInvalid(invalidFields));
                }
            });
    }

    private void validateForm(@NonNull ShopFormModel formModel) {
        validateShopName(formModel);
        validateImageUrl(formModel);
        validateOwner(formModel);
        validatePositionX(formModel);
        validatePositionY(formModel);
    }

    private void validateShopName(@NonNull ShopFormModel formModel) {
        if (!validationUtil.isValidName(formModel.getName())) {
            addFieldWithMessageFromUtil(ShopFormInvalidField.FIELD_CODE_SHOP_NAME);
        }
    }

    private void addFieldWithMessageFromUtil(int fieldCode) {
        ShopFormInvalidField formInvalidField = new ShopFormInvalidField(fieldCode, validationUtil.getMessage());
        invalidFields.add(formInvalidField);
    }

    private void validateImageUrl(@NonNull ShopFormModel formModel) {
        if (!validationUtil.isValidImageUrl(formModel.getImageUrl())) {
            addFieldWithMessageFromUtil(ShopFormInvalidField.FIELD_CODE_IMAGE_URL);
        }
    }

    private void validateOwner(@NonNull ShopFormModel formModel) {
        if (!validationUtil.isValidUser(formModel.getUserModel())) {
            addFieldWithMessageFromUtil(ShopFormInvalidField.FIELD_CODE_OWNER);
        }
    }

    private void validatePositionX(@NonNull ShopFormModel formModel) {
        if (!validationUtil.isValidPosition(formModel.getPosX())) {
            addFieldWithMessageFromUtil(ShopFormInvalidField.FIELD_CODE_POSITION_X);
        }
    }

    private void validatePositionY(@NonNull ShopFormModel formModel) {
        if (!validationUtil.isValidPosition(formModel.getPosY())) {
            addFieldWithMessageFromUtil(ShopFormInvalidField.FIELD_CODE_POSITION_Y);
        }
    }


}
