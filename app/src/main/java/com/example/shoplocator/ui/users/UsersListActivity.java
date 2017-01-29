package com.example.shoplocator.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.ui.users.detail.UserDetailActivity;
import com.example.shoplocator.ui.users.detail.view.UserDetailFragment;
import com.example.shoplocator.ui.users.list.view.UsersListFragment;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListActivity extends AppCompatActivity implements UserListDelegate {

    @Inject FragmentRouteAbs fragmentRoute;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list_drawer);
        ButterKnife.bind(this);
        App.instance().applicationComponent().inject(this);
        setupActionBar();
        twoPane = findViewById(R.id.shopDetailContainer) != null;
        if (savedInstanceState == null) {
            setupFragment();
        } else {
            deleteReduantDetailFragment();
        }
    }

    private void setupFragment() {
        fragmentRoute.setFragment(this, new UsersListFragment());
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void deleteReduantDetailFragment() {
        fragmentRoute.deleteFragmentIfExist(this, UserDetailFragment.class);
    }

    @Override
    public void showUserDetail(@NonNull String userId, @NonNull String userName) {
        Bundle arguments = new Bundle();
        arguments.putString(UserDetailActivity.PARAM_USER_ID, userId);
        arguments.putString(UserDetailActivity.PARAM_USER_NAME, userName);
        if (twoPane) {
            UserDetailFragment fragment = new UserDetailFragment();
            fragment.setArguments(arguments);
            fragmentRoute.replaceFragment(this, fragment, R.id.shopDetailContainer);
        } else {
            shopUserDetailActivity(arguments);
        }
    }

    private void shopUserDetailActivity(@NonNull Bundle arguments) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtras(arguments);
        startActivity(intent);
    }
}
