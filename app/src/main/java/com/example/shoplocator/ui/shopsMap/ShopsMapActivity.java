package com.example.shoplocator.ui.shopsMap;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shoplocator.R;
import com.example.shoplocator.ui.shopsMap.view.IShopMapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopsMapActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Nullable
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_map);
        ButterKnife.bind(this);
        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_shops_map_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    setNewQueryToShopMapFragment(newText);
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setNewQueryToShopMapFragment(@NonNull String query) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentShopsMap);
        if (fragment instanceof IShopMapView) {
            ((IShopMapView) fragment).onQueryChanged(query);
        }
    }
}
