package com.example.shoplocator.ui.shops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shoplocator.App;
import com.example.shoplocator.R;

import com.example.shoplocator.ui.createAndEditShop.CreateAndEditShopActivity;
import com.example.shoplocator.ui.shops.detail.ShopDetailActivity;
import com.example.shoplocator.ui.shops.detail.view.IShopDetailView;
import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;
import com.example.shoplocator.ui.shops.list.view.IShopsListView;
import com.example.shoplocator.ui.shops.list.view.ShopsListFragment;
import com.example.shoplocator.ui.shopsMap.view.ShopsMapActivity;
import com.example.shoplocator.ui.users.UsersListActivity;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopsListActivity extends AppCompatActivity implements ShopListDelegate {

    private static final int REQUEST_CODE_SHOP_DETAIL = 3;

    @Inject FragmentRouteAbs fragmentRoute;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;

    private MenuItem actionDone;
    private MenuItem actionCancel;
    private MenuItem actionRemove;
    private MenuItem actionCreate;
    private MenuItem actionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_list_drawer);
        ButterKnife.bind(this);
        App.instance().applicationComponent().inject(this);
        setupActionBar();
        setupNavigation();
        twoPane = findViewById(R.id.shopDetailContainer) != null;
        if (savedInstanceState == null) {
            setupFragment();
        } else {
            deleteReduantDetailFragment();
        }
    }

    private void setupFragment() {
        fragmentRoute.setFragment(this, new ShopsListFragment());
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void setupNavigation() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private final NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.nav_shops_list:{
                        onNavToShopsListSelected();
                        break;
                    }
                    case R.id.nav_shops_map: {
                        onNavToShopsMapSelected();
                        break;
                    }
                    case R.id.nav_users_list: {
                        onNavToUsersListSelected();
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            };

    private void onNavToShopsListSelected() {

    }

    private void onNavToShopsMapSelected() {
        Intent intent = new Intent(this, ShopsMapActivity.class);
        startActivity(intent);
    }

    private void onNavToUsersListSelected() {
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }

    private void deleteReduantDetailFragment() {
        fragmentRoute.deleteFragmentIfExist(this, ShopDetailFragment.class);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_list, menu);
        actionDone = menu.findItem(R.id.actionDone);
        actionCancel = menu.findItem(R.id.actionCancel);
        actionRemove = menu.findItem(R.id.actionRemove);
        actionCreate = menu.findItem(R.id.actionCreate);
        actionEdit = menu.findItem(R.id.actionEdit);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRemove: {
                onActionRemoveSelected();
                break;
            }
            case R.id.actionCancel: {
                onActionCancelSelected();
                break;
            }
            case R.id.actionDone: {
                onActionDoneSelected();
                break;
            }
            case R.id.actionCreate: {
                onActionCreateSelected();
                break;
            }
            case R.id.actionEdit: {
                onActionEditSelected();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void onActionRemoveSelected() {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopsListView) {
            ((IShopsListView) fragment).onRemoveActionSelected();
        }
    }

    private void onActionCancelSelected() {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopsListView) {
            ((IShopsListView) fragment).onCancelActionSelected();
        }
    }

    private void onActionDoneSelected() {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopsListView) {
            ((IShopsListView) fragment).onDoneActionSelected();
        }
    }

    private void onActionCreateSelected() {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopsListView) {
            ((IShopsListView) fragment).onCreateActionSelected();
        }
    }

    private void onActionEditSelected() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.shopDetailContainer);
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
                case REQUEST_CODE_SHOP_DETAIL: {
                    onShopDetailResult(data);
                    break;
                }
            }
        }
    }

    private void onEditShopResult(@NonNull Intent data) {
        String shopId = data.getStringExtra(CreateAndEditShopActivity.PARAM_SHOP_ID);
        if (shopId == null) throw new RuntimeException("Shop ID is missing.");
        if (twoPane) {
            sendEditResultToShopDetailFragment(shopId);
        }
        sendEditResultToShopListFragment(shopId);
    }

    private void sendEditResultToShopDetailFragment(@NonNull String shopId) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.shopDetailContainer);
        if (fragment instanceof IShopDetailView) {
            ((IShopDetailView) fragment).onEditShopResult(shopId);
        }
    }

    private void sendEditResultToShopListFragment(@NonNull String shopId) {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopsListView) {
            ((IShopsListView) fragment).onEditShopResult(shopId);
        }
    }

    private void onShopDetailResult(@NonNull Intent data) {
        String shopId = data.getStringExtra(ShopDetailActivity.PARAM_SHOP_ID);
        if (shopId == null) throw new RuntimeException("Shop ID is missing.");
        if (data.getBooleanExtra(ShopDetailActivity.PARAM_SHOP_HAS_BEEN_REMOVED, false)) {
            sendDeleteResultToShopListFragment(shopId);
        } else if (data.getBooleanExtra(ShopDetailActivity.PARAM_SHOP_HAS_BEEN_EDITED, false)) {
            sendEditResultToShopListFragment(shopId);
        }
    }

    private void sendDeleteResultToShopListFragment(@NonNull String shopId) {
        Fragment fragment = fragmentRoute.getCurrentFragment(this);
        if (fragment instanceof IShopsListView) {
            ((IShopsListView) fragment).onDeleteShopResult(shopId);
        }
    }

    @Override
    public void showShopDetail(@NonNull String shopId, View itemView) {
        Bundle arguments = new Bundle();
        arguments.putString(ShopDetailFragment.PARAM_SHOP_ID, shopId);
        if (twoPane) {
            ShopDetailFragment fragment = new ShopDetailFragment();
            fragment.setArguments(arguments);
            fragmentRoute.replaceFragment(this, fragment, R.id.shopDetailContainer);
        } else {
            shopShopDetailActivity(itemView, arguments);
        }
    }

    private void shopShopDetailActivity(View itemView, Bundle arguments) {
        Intent intent = new Intent(this, ShopDetailActivity.class);
        intent.putExtras(arguments);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            View imageView = itemView.findViewById(R.id.imageView);
            String transitionName = imageView.getTransitionName();
            intent.putExtra(ShopDetailFragment.PARAM_IMAGE_VIEW_TRANSITION_NAME, transitionName);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, imageView, transitionName);
            startActivityForResult(intent, REQUEST_CODE_SHOP_DETAIL, options.toBundle());
        } else {
            startActivityForResult(intent, REQUEST_CODE_SHOP_DETAIL);
        }
    }

    @Override
    public void setEditState(boolean editState) {
        actionCancel.setVisible(editState);
        actionDone.setVisible(editState);
        actionRemove.setVisible(!editState);
        actionCreate.setVisible(!editState);
    }

    public void setEditButtonVisible(boolean visible) {
        if (actionEdit != null) {
            actionEdit.setVisible(visible);
        }
    }

}
