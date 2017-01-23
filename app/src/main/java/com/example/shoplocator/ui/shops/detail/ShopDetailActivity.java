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
//        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFragment() {
        Bundle arguments = new Bundle();
        arguments.putLong(ShopDetailFragment.PARAM_SHOP_ID,
                getIntent().getLongExtra(ShopDetailFragment.PARAM_SHOP_ID, 0));
        ShopDetailFragment fragment = new ShopDetailFragment();
        fragment.setArguments(arguments);
        fragmentRoute.setFragment(this, fragment, R.id.shopDetailContainer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ShopsListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
