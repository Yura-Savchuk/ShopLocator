package com.example.shoplocator.buissines.createAndEditShop;

import android.support.annotation.NonNull;

import com.example.shoplocator.buissines.createAndEditShop.validation.IShopFormValidation;
import com.example.shoplocator.buissines.mapper.ShopFormMapper;
import com.example.shoplocator.buissines.mapper.ShopsDbMapper;
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
    private final IShopFormValidation shopFormValidation;

    public CreateAndEditShopInterator(IShopsRepository shopsRepository, IUsersRepository usersRepository, IShopFormValidation shopFormValidation) {
        this.shopsRepository = shopsRepository;
        this.usersRepository = usersRepository;
        this.shopFormValidation = shopFormValidation;
    }

    @Override
    public Single<List<CheckableUserModel>> getCheckableUsers() {
        return usersRepository.getUsers()
                .map(UserMapper::transformToCheckableUsers);
    }

    @Override
    public Single<ShopFormModel> getShopFormById(@NonNull String shopId) {
        return shopsRepository.getShopById(shopId)
                .flatMap(shopDbModel -> usersRepository.getUserById(shopDbModel.getOwnerId())
                        .map(userDbModel -> ShopsDbMapper.mapDbToUi(shopDbModel, userDbModel)))
                .map(ShopFormMapper::transform);
    }

    @Override
    public Single<String> addShopAngGetId(@NonNull ShopFormModel fromModel) {
        ShopFormDbModel formDbModel = ShopFormMapper.transform(fromModel);
        return shopsRepository.addShopAndGetId(formDbModel);
    }

    @Override
    public Single<String> updateShopAndGetId(@NonNull String shopId, @NonNull ShopFormModel fromModel) {
        ShopFormDbModel formDbModel = ShopFormMapper.transform(fromModel);
        return shopsRepository.updateShopAndGetId(shopId, formDbModel);
    }

    @Override
    public Single<ShopFormModel> validateForm(ShopFormModel shopForm) {
        return shopFormValidation.validate(shopForm);
    }
}
