package com.example.shoplocator.buissines.shopDetail;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.mapper.ShopsDbMapper;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.Collection;
import java.util.LinkedList;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopDetailInteractor implements IShopDetailInteractor {

    private final IShopsRepository shopsRepository;
    private final IUsersRepository usersRepository;

    public ShopDetailInteractor(@NonNull IShopsRepository shopsRepository, @NonNull IUsersRepository usersRepository) {
        this.shopsRepository = shopsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Single<ShopModel> getShopById(@NonNull String shopId) {
        return shopsRepository.getShopByIdFromDb(shopId)
                .flatMap(shopDbModel -> usersRepository.getUserByIdFromDb(shopDbModel.getOwnerId())
                        .map(userDbModel -> ShopsDbMapper.mapDbToUi(shopDbModel, userDbModel)));
    }

    @Override
    public Single<Object> deleteShopById(@NonNull String shopId) {
        Collection<String> ids = new LinkedList<>();
        ids.add(shopId);
        return shopsRepository.deleteShopsByIds(ids);
    }
}
