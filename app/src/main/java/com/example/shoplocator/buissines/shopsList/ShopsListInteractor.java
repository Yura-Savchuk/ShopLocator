package com.example.shoplocator.buissines.shopsList;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.mapper.CheckableShopMapper;
import com.example.shoplocator.buissines.mapper.ShopsDbMapper;
import com.example.shoplocator.buissines.shopsList.commands.AddShopCommand;
import com.example.shoplocator.buissines.shopsList.commands.DeleteShopsCommand;
import com.example.shoplocator.buissines.shopsList.commands.ChangeItemsCommand;
import com.example.shoplocator.buissines.shopsList.commands.DeselectShopsCommand;
import com.example.shoplocator.data.firebaseDb.mapper.ShopMapper;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Single;
import rx.functions.Func1;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsListInteractor implements IShopsListInteractor {

    private final IShopsRepository shopsRepository;
    private final IUsersRepository usersRepository;

    public ShopsListInteractor(@NonNull IShopsRepository shopsRepository, IUsersRepository usersRepository) {
        this.shopsRepository = shopsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Single<List<ShopModel>> getShops() {
        return Single.zip(shopsRepository.getShops(), usersRepository.getUsers(), ShopsDbMapper::new)
                .map(ShopsDbMapper::getUiShops);
    }

    @Override
    public Single<List<SelectableShopModel>> getCheckableShops() {
        return getShops()
                .map(CheckableShopMapper::mapShopsToCheckableShops);
    }

    @Override
    public Single<ChangeItemsCommand> removeSelectedShops(@NonNull List<SelectableShopModel> shops) {
        //TODO This code can be written asynchronously
        DeleteShopsCommand deleteShopsCommand = new DeleteShopsCommand(shops);
        Collection<String> idsToRemove = new ArrayList<>();
        for (SelectableShopModel shop : shops) {
            if (shop.isSelected()) {
                idsToRemove.add(shop.getId());
                deleteShopsCommand.addRemovedId(shop.getId());
            }
        }
        return shopsRepository.deleteShopsByIdsFromDb(idsToRemove)
                .map(o -> deleteShopsCommand);
    }

    @Override
    public ChangeItemsCommand deselectSelectedShops(@NonNull List<SelectableShopModel> shops) {
        return new DeselectShopsCommand(shops);
    }

    @Override
    public Single<ChangeItemsCommand> addShopById(List<SelectableShopModel> shops, String shopId) {
        return shopsRepository.getShopById(shopId)
                .flatMap(shopDbModel -> usersRepository.getUserById(shopDbModel.getOwnerId())
                        .map(userDbModel -> ShopsDbMapper.mapDbToUi(shopDbModel, userDbModel)))
                .map(CheckableShopMapper::mapShopToCheckableShop)
                .map(shop -> new AddShopCommand<>(shops, shop));
    }
}
