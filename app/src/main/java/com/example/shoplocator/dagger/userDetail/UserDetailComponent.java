package com.example.shoplocator.dagger.userDetail;

import com.example.shoplocator.ui.users.detail.view.UserDetailFragment;

import dagger.Subcomponent;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

@Subcomponent(modules = UserDetailModule.class)
@UserDetailScope
public interface UserDetailComponent {

    void inject(UserDetailFragment fragment);

}
