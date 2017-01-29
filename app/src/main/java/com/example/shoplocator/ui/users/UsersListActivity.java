package com.example.shoplocator.ui.users;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;
import com.example.shoplocator.ui.users.list.view.UsersListFragment;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListActivity extends AppCompatActivity {

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
        fragmentRoute.deleteFragmentIfExist(this, ShopDetailFragment.class);
    }

}
