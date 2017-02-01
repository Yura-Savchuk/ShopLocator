package com.example.shoplocator.ui.shops.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.ui.createAndEditShop.CreateAndEditShopActivity;
import com.example.shoplocator.ui.shops.detail.view.IShopDetailView;
import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDetailActivity extends AppCompatActivity {

    public static final String PARAM_SHOP_ID = "shop_id";
    public static final String PARAM_SHOP_HAS_BEEN_EDITED = "shop_has_been_edited";
    public static final String PARAM_SHOP_HAS_BEEN_REMOVED = "shop_has_been_removed";

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
        fragmentRoute.setFragment(this, fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRemove: {
                onRemoveActionSelected();
                break;
            }
            case R.id.actionEdit: {
                onEditActionSelected();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void onRemoveActionSelected() {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopDetailView) {
            ((IShopDetailView) fragment).onRemoveActionSelected();
        }
    }

    private void onEditActionSelected() {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopDetailView) {
            ((IShopDetailView) fragment).onEditActionSelected();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ShopDetailFragment.REQUEST_CODE_EDIT_SHOP: {
                    onEditShopResult(data);
                    break;
                }
            }
        }
    }

    private void onEditShopResult(@NonNull Intent data) {
        String shopId = data.getStringExtra(CreateAndEditShopActivity.PARAM_SHOP_ID);
        if (shopId == null) throw new RuntimeException("Shop ID is missing.");
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopDetailView) {
            ((IShopDetailView) fragment).onEditShopResult(shopId);
        }
        setShopEditedResult(shopId);
    }

    private void setShopEditedResult(@NonNull String shopId) {
        Intent intent = new Intent();
        intent.putExtra(PARAM_SHOP_ID, shopId);
        intent.putExtra(PARAM_SHOP_HAS_BEEN_EDITED, true);
        setResult(RESULT_OK, intent);
    }

}
