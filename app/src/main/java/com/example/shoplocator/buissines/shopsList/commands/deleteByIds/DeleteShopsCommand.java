package com.example.shoplocator.buissines.shopsList.commands.deleteByIds;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.shopsList.commands.CommandExecutedListener;
import com.example.shoplocator.buissines.shopsList.commands.ChangeItemsCommand;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 24.01.17.
 */

public class DeleteShopsCommand implements ChangeItemsCommand {

    private final List<? extends ShopModel> uiShopModels;
    private final Set<String> removedShopIds = new HashSet<>();
    private boolean executed = false;

    public DeleteShopsCommand(List<? extends ShopModel> uiShopModels) {
        this.uiShopModels = uiShopModels;
    }

    public void addRemovedId(String shopId) {
        removedShopIds.add(shopId);
    }

    @Override
    public void executeWithListener(@NonNull CommandExecutedListener listener) {
        if (executed) throw new RuntimeException("DeleteShopsCommand class can be executed only one time.");
        executed = true;
        for (int i=0; i<uiShopModels.size(); i++) {
            ShopModel shop = uiShopModels.get(i);
            if (removedShopIds.contains(shop.getId())) {
                uiShopModels.remove(i);
                listener.onExecutedWithItemAt(i--);
            }
        }
    }

}
