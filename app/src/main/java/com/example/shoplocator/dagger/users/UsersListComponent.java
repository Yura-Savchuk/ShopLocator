package com.example.shoplocator.dagger.users;

import com.example.shoplocator.ui.users.list.view.UsersListFragment;

import dagger.Subcomponent;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

@Subcomponent(modules = UsersListModule.class)
@UsersListScope
public interface UsersListComponent {

    void inject(UsersListFragment fragment);

}
