package com.example.shoplocator.buissines.shopDetail;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.mapper.ShopsDbMapper;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.model.ShopModel;

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
    public Single<ShopModel> getShopById(long shopId) {
        return shopsRepository.getShopById(shopId)
                .flatMap(shopDbModel -> usersRepository.getUserById(shopDbModel.getOwnerId())
                        .map(userDbModel -> ShopsDbMapper.mapDbToUi(shopDbModel, userDbModel)));
    }
}
