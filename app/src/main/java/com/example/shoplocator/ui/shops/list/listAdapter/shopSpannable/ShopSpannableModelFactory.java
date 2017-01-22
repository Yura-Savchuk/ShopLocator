package com.example.shoplocator.ui.shops.list.listAdapter.shopSpannable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.example.shoplocator.R;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.Locale;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopSpannableModelFactory implements IShopSpannableModelFactory {

    private static final float SMALL_TEXT_FACTOR = 0.8f;

    private final Context context;
    private final String ownerPrefixStr;
    private final String coordinateXPrefixStr;
    private final String coordinateYPrefixStr;

    public ShopSpannableModelFactory(@NonNull Context context) {
        this.context = context;
        ownerPrefixStr = context.getString(R.string.item_shop_owner_prefix);
        coordinateXPrefixStr = context.getString(R.string.item_shop_coordinate_x_prefix);
        coordinateYPrefixStr = context.getString(R.string.item_shop_coordinate_y_prefix);
    }

    ShopSpannableModel createSpannable(@NonNull ShopModel shopModel) {
        Spannable ownerSpannable = createOwnerSpannable(shopModel.getOwner().getName());
        Spannable coordsSpannable = createCoordsSpannable(shopModel.getCoordinate());
        return new ShopSpannableModel(ownerSpannable, coordsSpannable);
    }

    @Override
    public Spannable createOwnerSpannable(@NonNull String ownerName) {
        String ownerStr = String.format(Locale.getDefault(), "%s%s", ownerPrefixStr, ownerName);
        Spannable owner = new SpannableString(ownerStr);
        owner.setSpan(createColorSecondaryText(), 0, ownerPrefixStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        owner.setSpan(createSmallTextSpan(), 0, ownerPrefixStr.length(), 0);
        owner.setSpan(createColorPrimaryText(), ownerPrefixStr.length(), ownerStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return owner;
    }

    private ForegroundColorSpan createColorSecondaryText() {
        return new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorSecondaryText));
    }

    private RelativeSizeSpan createSmallTextSpan() {
        return new RelativeSizeSpan(SMALL_TEXT_FACTOR);
    }

    private ForegroundColorSpan createColorPrimaryText() {
        return new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimaryText));
    }

    @Override
    public Spannable createCoordsSpannable(@NonNull ShopCoordinate shopCoordinate) {
        String coordXStr = String.format(Locale.getDefault(), "%.2f", shopCoordinate.getX());
        String coordYStr = String.format(Locale.getDefault(), "%.2f", shopCoordinate.getY());
        String coordinateStr = String.format(Locale.getDefault(), "%s%s%s%s", coordinateXPrefixStr, coordXStr, coordinateYPrefixStr, coordYStr);
        Spannable coordinate = new SpannableString(coordinateStr);

        coordinate.setSpan(createColorSecondaryText(), 0, coordinateXPrefixStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        coordinate.setSpan(createSmallTextSpan(), 0, coordinateXPrefixStr.length(), 0);

        coordinate.setSpan(createColorPrimaryText(), coordinateXPrefixStr.length(), coordinateXPrefixStr.length() + coordXStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        coordinate.setSpan(createColorSecondaryText(), coordinateXPrefixStr.length() + coordXStr.length(), coordinateStr.length() - coordYStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        coordinate.setSpan(createSmallTextSpan(), coordinateXPrefixStr.length() + coordXStr.length(), coordinateStr.length() - coordYStr.length(), 0);

        coordinate.setSpan(createColorPrimaryText(), coordinateStr.length() - coordYStr.length(), coordinateStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return coordinate;
    }

}
