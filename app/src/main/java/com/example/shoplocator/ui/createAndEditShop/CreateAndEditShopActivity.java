package com.example.shoplocator.ui.createAndEditShop;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.ui.createAndEditShop.view.CreateAndEditShopFragment;
import com.example.shoplocator.ui.createAndEditShop.view.ICreateAndEditShopView;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;
import com.example.shoplocator.util.ui.keyboard.KeyboardUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAndEditShopActivity extends AppCompatActivity {

    public static final String PARAM_SHOP_ID = "shop_id";
    public static final long EMPTY_VALUE = -1;

    @Inject FragmentRouteAbs fragmentRouteAbs;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.dummyFocusableView) View dummyFocusableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_and_edit_shop);
        ButterKnife.bind(this);
        App.instance().applicationComponent().inject(this);
        setupActionBar();
        setupFragment(savedInstanceState);
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            CreateAndEditShopFragment fragment = new CreateAndEditShopFragment();
            Bundle arguments = new Bundle();
            arguments.putString(CreateAndEditShopFragment.PARAM_SHOP_ID, getIntent().getStringExtra(PARAM_SHOP_ID));
            fragment.setArguments(arguments);
            fragmentRouteAbs.setFragment(this, fragment);
        }
    }

    @Override
    public void onBackPressed() {
        hideIfShownKeyboard();
        super.onBackPressed();
    }

    private void hideIfShownKeyboard() {
        new KeyboardUtil(dummyFocusableView).hideKeyboardIfShown(this);
    }

    @Override
    public void finish() {
        hideIfShownKeyboard();
        super.finish();
    }
}
