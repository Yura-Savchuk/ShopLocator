package com.example.shoplocator.ui.shops.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.ui.shops.ShopsListActivity;
import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDetailActivity extends AppCompatActivity {

    @Inject FragmentRouteAbs fragmentRoute;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        setupActionBar();
        App.instance().applicationComponent().inject(this);
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
        Intent intent = getIntent();
        arguments.putString(ShopDetailFragment.PARAM_SHOP_ID,
                intent.getStringExtra(ShopDetailFragment.PARAM_SHOP_ID));
        arguments.putString(ShopDetailFragment.PARAM_IMAGE_VIEW_TRANSITION_NAME,
                intent.getStringExtra(ShopDetailFragment.PARAM_IMAGE_VIEW_TRANSITION_NAME));
        ShopDetailFragment fragment = new ShopDetailFragment();
        fragment.setArguments(arguments);
        fragmentRoute.setFragment(this, fragment, R.id.shopDetailContainer);
    }

}
