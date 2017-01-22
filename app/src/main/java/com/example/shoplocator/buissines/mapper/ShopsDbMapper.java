package com.example.shoplocator.buissines.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.data.model.ShopDbModel;
import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.util.mapper.Mapper;
import com.example.shoplocator.util.mapper.MapperUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public final class ShopsDbMapper {

    private final Collection<ShopDbModel> shops;
    private final Collection<UserDbModel> users;

    public ShopsDbMapper(@Nullable Collection<ShopDbModel> shops, @NonNull Collection<UserDbModel> users) {
        this.shops = shops;
        this.users = users;
    }

    public List<ShopModel> getUiShops() {
        return MapperUtil.transformList(shops, exist -> {
            UserDbModel userDbModel = getUserById(exist.getOwnerId());
            if (userDbModel == null) throw new RuntimeException("User by id: " + exist.getOwnerId() + "undefined.");
            return mapDbToUi(exist, userDbModel);
        });
    }

    @Nullable
    private UserDbModel getUserById(long userId) {
        for (UserDbModel user : users) {
            if (user.getId() == userId) return user;
        }
        return null;
    }

    public static ShopModel mapDbToUi(@NonNull ShopDbModel shopDbModel, UserDbModel userDbModel) {
        ShopCoordinate coordinate = new ShopCoordinate(
                shopDbModel.getCoordinateX(), shopDbModel.getCoordinateY());
        return new ShopModel(
                shopDbModel.getId(),
                shopDbModel.getName(),
                shopDbModel.getImageUrl(),
                coordinate,
                mapDbToUi(userDbModel)
        );
    }

    public static UserModel mapDbToUi(@NonNull UserDbModel user) {
        return new UserModel(user.getId(), user.getName());
    }

    public List<UserModel> getUiUsers() {
        return MapperUtil.transformList(users, ShopsDbMapper::mapDbToUi);
    }

}
