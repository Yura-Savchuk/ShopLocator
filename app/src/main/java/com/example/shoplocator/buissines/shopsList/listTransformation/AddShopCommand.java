package com.example.shoplocator.buissines.shopsList.listTransformation;

import android.support.annotation.NonNull;

import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

public class AddShopCommand<T extends ShopModel> implements ChangeItemsCommand {

    private final List<T> uiShopModels;
    private final T shopModel;

    public AddShopCommand(List<T> uiShopModels, T shopModel) {
        this.uiShopModels = uiShopModels;
        this.shopModel = shopModel;
    }

    @Override
    public void executeWithListener(@NonNull CommandExecutedListener listener) {
        uiShopModels.add(shopModel);
        for (int i=0; i<uiShopModels.size(); i++) {
            T item = uiShopModels.get(i);
            if (item == shopModel) {
                listener.onExecutedWithItemAt(i);
                break;
            }
        }

    }
}
