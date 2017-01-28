package com.example.shoplocator.buissines.createAndEditShop.validation.field;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class ShopFormInvalidField {

    public static final int FIELD_CODE_SHOP_NAME = 1;
    public static final int FIELD_CODE_IMAGE_URL = 2;
    public static final int FIELD_CODE_OWNER = 3;
    public static final int FIELD_CODE_POSITION_X = 4;
    public static final int FIELD_CODE_POSITION_Y = 5;

    private final int fieldCode;
    private final String message;

    public ShopFormInvalidField(int fieldCode, String message) {
        this.fieldCode = fieldCode;
        this.message = message;
    }

    public int getFieldCode() {
        return fieldCode;
    }

    public String getMessage() {
        return message;
    }
}
