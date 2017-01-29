package com.example.shoplocator.ui.users;

import android.support.annotation.NonNull;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public interface UserListDelegate {

    void showUserDetail(@NonNull String userId, @NonNull String userName);

}
