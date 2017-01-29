package com.example.shoplocator.ui.users.detail.shopsListAdapter.shopSpannable;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.HashMap;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopSpannableModelsPool {

    private final HashMap<String, ShopSpannableModel> shopSpannableModels;
    private final ShopSpannableModelFactory factory;

    public ShopSpannableModelsPool(Context context) {
        this.shopSpannableModels = new HashMap<>();
        this.factory = new ShopSpannableModelFactory(context);
    }

    public ShopSpannableModel getSpannableModel(@NonNull ShopModel shopModel) {
        ShopSpannableModel spannableModel = shopSpannableModels.get(shopModel.getId());
        if (spannableModel == null) {
            spannableModel = factory.createSpannable(shopModel);
            shopSpannableModels.put(shopModel.getId(), spannableModel);
        }
        return spannableModel;
    }

}
