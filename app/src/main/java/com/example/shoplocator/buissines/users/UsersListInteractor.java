package com.example.shoplocator.buissines.users;

import com.example.shoplocator.buissines.mapper.UserMapper;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.ui.users.model.SelectableUserModel;

import java.util.ArrayList;
import java.util.List;

import rx.Single;
import rx.functions.Func1;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersListInteractor implements IUsersListInteractor {

    private final IUsersRepository usersRepository;

    public UsersListInteractor(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Single<List<SelectableUserModel>> getSelectableUsers() {
        return usersRepository.getUsers()
                .map(UserMapper::transformToSelectableUsers);
    }
}
