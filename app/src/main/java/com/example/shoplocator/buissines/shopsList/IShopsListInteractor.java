package com.example.shoplocator.buissines.shopsList;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.shopsList.commands.ChangeItemsCommand;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public interface IShopsListInteractor {

    Single<List<SelectableShopModel>> getCheckableShops();
    Single<List<ShopModel>> getShops();

    Single<ChangeItemsCommand> removeSelectedShops(@NonNull List<SelectableShopModel> shops);
    ChangeItemsCommand deselectSelectedShops(@NonNull List<SelectableShopModel> shops);
}
