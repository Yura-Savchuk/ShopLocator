package com.example.shoplocator.buissines.mapper;

import android.support.annotation.NonNull;

import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.util.mapper.MapperUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class UserMapper {

    public static ArrayList<CheckableUserModel> transform(List<UserDbModel> userDbModels) {
        return MapperUtil.transformList(userDbModels,
                exist -> new CheckableUserModel(exist.getId(), exist.getName()));
    }

    public static UserDbModel transform(@NonNull UserModel user) {
        return new UserDbModel(user.getId(), user.getName());
    }

}
