package com.example.shoplocator.buissines.userDetail;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.repsitory.shops.IShopsRepository;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.util.mapper.MapperUtil;

import java.util.List;

import rx.Single;

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
        return MapperUtil.transformList(shopDbModels, exist -> {
            ShopCoordinate coordinate = new ShopCoordinate(exist.getCoordinateX(), exist.getCoordinateY());
            return new ShopModel(
                    exist.getId(),
                    exist.getName(),
                    exist.getImageUrl(),
                    coordinate,
                    userModel.clone()
            );
        });
    }

}
