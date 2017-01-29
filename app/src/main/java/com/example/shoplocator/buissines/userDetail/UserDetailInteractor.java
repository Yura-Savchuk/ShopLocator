package com.example.shoplocator.buissines.userDetail;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.util.mapper.Mapper;
import com.example.shoplocator.util.mapper.MapperUtil;

import java.util.List;

import rx.Single;
import rx.functions.Func1;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UserDetailInteractor implements IUserDetailInteractor {

    private final IShopsRepository shopsRepository;

    public UserDetailInteractor(IShopsRepository shopsRepository) {
        this.shopsRepository = shopsRepository;
    }

    @Override
    public Single<List<ShopModel>> getShopsByUserId(@NonNull String userId, @NonNull String userName) {
        return shopsRepository.getShopsByUserId(userId)
                .map(shopDbModels -> mapShopDbByUserName(shopDbModels, new UserModel(userId, userName)));
    }

    private List<ShopModel> mapShopDbByUserName(List<ShopDbModel> shopDbModels, @NonNull UserModel userModel) {
        return MapperUtil.transformList(shopDbModels, exist -> mapShopDbByUserModel(exist, userModel));
    }

    private ShopModel mapShopDbByUserModel(@NonNull ShopDbModel shopDbModel, @NonNull UserModel userMode) {
        ShopCoordinate coordinate = new ShopCoordinate(shopDbModel.getCoordinateX(), shopDbModel.getCoordinateY());
        return new ShopModel(
                shopDbModel.getId(),
                shopDbModel.getName(),
                shopDbModel.getImageUrl(),
                coordinate,
                userMode.clone()
        );
    }

    @Override
    public Single<ShopModel> getShopById(@NonNull String shopId, @NonNull String userId, @NonNull String userName) {
        return shopsRepository.getShopById(shopId)
                .map(shopDbModel -> mapShopDbByUserModel(shopDbModel, new UserModel(userId, userName)));
    }

}
