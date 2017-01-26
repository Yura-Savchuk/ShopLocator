package com.example.shoplocator.buissines.shopsMap;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.buissines.mapper.ShopsDbMapper;
import com.example.shoplocator.buissines.shopsMap.filtration.ShopListFilter;
import com.example.shoplocator.buissines.shopsMap.filtration.ShopListFilterModel;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

public class ShopsMapInteractor implements IShopsMapInteractor {

    private final IShopsRepository shopsRepository;
    private final IUsersRepository usersRepository;

    public ShopsMapInteractor(@NonNull IShopsRepository shopsRepository, IUsersRepository usersRepository) {
        this.shopsRepository = shopsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Single<ShopListFilterModel> getShopListFilterModel() {
        return Single.zip(shopsRepository.getShops(), usersRepository.getUsers(), ShopsDbMapper::new)
                .map(ShopsDbMapper::getUiShops)
                .map(ShopListFilterModel::new);
    }

    @Override
    public void filterShopList(@NonNull ShopListFilterModel listFilterModel, @Nullable String query) {
        new ShopListFilter(listFilterModel).filterByQuery(query);
    }
}
