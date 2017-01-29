package com.example.shoplocator.buissines.usersList;

import com.example.shoplocator.ui.model.UserModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUsersListInteractor {

    Single<List<UserModel>> getSelectableUsers();

}
