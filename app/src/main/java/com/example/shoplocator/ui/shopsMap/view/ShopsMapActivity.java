package com.example.shoplocator.ui.shopsMap.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.dagger.shopsMap.ShopsMapModule;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shopsMap.pagerAdapter.TextViewPagerAdapter;
import com.example.shoplocator.ui.shopsMap.presenter.IShopMapPresenter;
import com.example.shoplocator.util.rx.lsitenerToObservable.ListenerToSingle;
import com.example.shoplocator.util.ui.listenerAdapter.OnPagerChangeListenerAdapter;
import com.example.shoplocator.util.ui.progress.ProgressDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopsMapActivity extends AppCompatActivity implements IShopMapView {

    private static final float CAMERA_ZOOM = 17.0f;

    private GoogleMap mMap;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewPager) ViewPager viewPager;

    @Inject IShopMapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().applicationComponent().plus(new ShopsMapModule()).inject(this);
        setContentView(R.layout.activity_shops_map);
        ButterKnife.bind(this);
        presenter.bindView(this);
        setupMap();
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

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        ListenerToSingle<GoogleMap> listenerToSingle = new ListenerToSingle<>();
        presenter.loadShopsAndControlAccessablity(listenerToSingle.getSingle().map(m -> null));
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            listenerToSingle.emitNextValue(googleMap);
        });
    }

    @Override
    protected void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.restoreInstanceState(savedInstanceState);
    }

    @Override
    public void shopProgress(boolean progress) {
        if (progress) {
            ProgressDialog.showIfHidden(this);
        } else {
            ProgressDialog.hideIfShown();
        }
    }

    @Override
    public void setupShopsList(List<ShopModel> shops) {
        TextViewPagerAdapter adapter = new TextViewPagerAdapter(shops);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pagerChangeListener);
    }

    @Override
    public void setupShopMapkers(List<ShopModel> shops) {
        for (ShopModel shop : shops) {
            ShopCoordinate coordinate = shop.getCoordinate();
            LatLng latLng = new LatLng(coordinate.getX(), coordinate.getY());
            mMap.addMarker(new MarkerOptions().position(latLng).title(shop.getName()));
        }
    }

    private final OnPagerChangeListenerAdapter pagerChangeListener = new OnPagerChangeListenerAdapter() {
        @Override
        public void onPageSelected(int position) {
            presenter.onShopPositionChanged(position);
        }
    };

    @Override
    public void setMapCursorToCoordinate(ShopCoordinate coordinate) {
        LatLng latLng = new LatLng(coordinate.getX(), coordinate.getY());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));
    }

    @Override
    public void setShopByPositionOnPager(int position) {
        viewPager.removeOnPageChangeListener(pagerChangeListener);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(pagerChangeListener);
    }
}
