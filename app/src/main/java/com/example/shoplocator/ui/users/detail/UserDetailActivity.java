package com.example.shoplocator.ui.users.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.ui.users.detail.view.UserDetailFragment;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UserDetailActivity extends AppCompatActivity {

    public static final String PARAM_USER_ID = "user_id";
    public static final String PARAM_USER_NAME = "user_name";

    @Inject FragmentRouteAbs fragmentRoute;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        App.instance().applicationComponent().inject(this);
        setupActionBar();
        if (savedInstanceState == null) {
            setupFragment();
        }
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupFragment() {
        Bundle arguments = new Bundle();
        arguments.putString(PARAM_USER_ID, getIntent().getStringExtra(PARAM_USER_ID));
        arguments.putString(PARAM_USER_NAME, getIntent().getStringExtra(PARAM_USER_NAME));
        Fragment fragment = new UserDetailFragment();
        fragment.setArguments(arguments);
        fragmentRoute.setFragment(this, fragment);
    }
}
