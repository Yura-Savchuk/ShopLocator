package com.example.shoplocator.buissines.createAndEditShop;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.mapper.ShopFormMapper;
import com.example.shoplocator.buissines.mapper.UserMapper;
import com.example.shoplocator.data.model.ShopFormDbModel;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.createAndEditShop.model.ShopFormModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class CreateAndEditShopInterator implements ICreateAndEditShopInteractor {

    private final IShopsRepository shopsRepository;
    private final IUsersRepository usersRepository;

    public CreateAndEditShopInterator(IShopsRepository shopsRepository, IUsersRepository usersRepository) {
        this.shopsRepository = shopsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Single<List<CheckableUserModel>> getCheckableUsers() {
        return usersRepository.getUsers()
                .map(UserMapper::transform);
    }

    @Override
    public Single<Long> addShopAngGetId(@NonNull ShopFormModel fromModel) {
        ShopFormDbModel formDbModel = ShopFormMapper.transform(fromModel);
        return shopsRepository.addShopAndGetId(formDbModel);
    }
}
