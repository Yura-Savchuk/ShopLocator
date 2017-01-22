package com.example.shoplocator.buissines.shopsList;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.mapper.ShopsDbMapper;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

import rx.Single;

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

}
