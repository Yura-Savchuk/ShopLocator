package com.example.shoplocator.buissines.users;

import com.example.shoplocator.ui.users.model.SelectableUserModel;

import java.util.List;

import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface IUsersListInteractor {

    Single<List<SelectableUserModel>> getSelectableUsers();

}
