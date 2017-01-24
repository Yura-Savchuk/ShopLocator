package com.example.shoplocator.buissines.shopsList.commands.deselectSelected;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.shopsList.commands.ChangeItemsCommand;
import com.example.shoplocator.buissines.shopsList.commands.CommandExecutedListener;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 24.01.17.
 */

public class DeselectShopsCommand implements ChangeItemsCommand {

    private final List<SelectableShopModel> shops;

    public DeselectShopsCommand(List<SelectableShopModel> shops) {
        this.shops = shops;
    }

    @Override
    public void executeWithListener(@NonNull CommandExecutedListener listener) {
        for (int i=0; i<shops.size(); i++) {
            SelectableShopModel shop = shops.get(i);
            if (shop.isSelected()) {
                shop.setSelected(false);
                listener.onExecutedWithItemAt(i);
            }
        }
    }
}
