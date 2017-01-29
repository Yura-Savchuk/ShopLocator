package com.example.shoplocator.buissines.usersList;

import com.example.shoplocator.buissines.mapper.UserMapper;
import com.example.shoplocator.data.repsitory.users.IUsersRepository;
import com.example.shoplocator.ui.model.UserModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersListInteractor implements IUsersListInteractor {

    private final IUsersRepository usersRepository;

    public UsersListInteractor(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Single<List<UserModel>> getSelectableUsers() {
        return usersRepository.getUsers()
                .map(UserMapper::transform);
    }
}
