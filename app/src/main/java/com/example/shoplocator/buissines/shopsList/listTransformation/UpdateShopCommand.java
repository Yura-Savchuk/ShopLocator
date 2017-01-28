package com.example.shoplocator.buissines.shopsList.listTransformation;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class UpdateShopCommand<T extends ShopModel> implements ChangeItemsCommand {

    private final List<T> uiShopModels;
    private final T shopModel;

    public UpdateShopCommand(@NonNull List<T> uiShopModels, @NonNull T shopModel) {
        this.uiShopModels = uiShopModels;
        this.shopModel = shopModel;
    }

    @Override
    public void executeWithListener(@NonNull CommandExecutedListener listener) {
        for (int i=0; i<uiShopModels.size(); i++) {
            T item = uiShopModels.get(i);
            if (item.getId().equals(shopModel.getId())) {
                uiShopModels.set(i, shopModel);
                listener.onExecutedWithItemAt(i);
                break;
            }
        }

    }
}
