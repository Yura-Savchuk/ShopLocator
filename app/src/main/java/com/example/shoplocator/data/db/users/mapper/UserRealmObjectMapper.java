package com.example.shoplocator.data.db.users.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shoplocator.data.db.users.model.UserRealmObject;
import com.example.shoplocator.data.model.UserDbModel;
import com.example.shoplocator.util.mapper.Mapper;
import com.example.shoplocator.util.mapper.MapperUtil;

import java.util.Collection;
import java.util.List;

/**
 * Created by seotm on 23.01.17.
 */

public class UserRealmObjectMapper {

    @NonNull
    public static List<UserDbModel> mapRealmToDb(@Nullable Collection<UserRealmObject> users) {
        return MapperUtil.transformList(users, UserRealmObjectMapper::mapRealmToDb);
    }

    @NonNull
    public static UserDbModel mapRealmToDb(@NonNull UserRealmObject user) {
        return new UserDbModel(
                user.getId(),
                user.getName()
        );
    }

    @NonNull
    public static List<UserRealmObject> mapDbToRealm(@NonNull Collection<UserDbModel> users) {
        return MapperUtil.transformList(users, UserRealmObjectMapper::mapDbToRealm);
    }

    @NonNull
    public static UserRealmObject mapDbToRealm(@NonNull UserDbModel user) {
        UserRealmObject userRealmObject = new UserRealmObject();
        userRealmObject.setId(user.getId());
        userRealmObject.setName(user.getName());
        return userRealmObject;
    }

}
