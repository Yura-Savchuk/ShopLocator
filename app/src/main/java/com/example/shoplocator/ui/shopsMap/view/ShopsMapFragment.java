package com.example.shoplocator.ui.shopsMap.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.dagger.shopsMap.ShopsMapModule;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shopsMap.pagerAdapter.TextViewPagerAdapter;
import com.example.shoplocator.ui.shopsMap.presenter.IShopMapPresenter;
import com.example.shoplocator.util.rx.lsitenerToObservable.ListenerToSingle;
import com.example.shoplocator.util.ui.listenerAdapter.OnPagerChangeListenerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Single;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 26.01.17.
 */

public class ShopsMapFragment extends Fragment implements IShopMapView {

    private static final float CAMERA_ZOOM = 17.0f;

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.errorView) View errorView;
    @BindView(R.id.progressView) View progressView;

    @Inject IShopMapPresenter presenter;

    @Nullable
    private GoogleMap mMap;
    private TextViewPagerAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        App.instance().applicationComponent().plus(new ShopsMapModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shops_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.bindView(this);
        setupMap();
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        ListenerToSingle<GoogleMap> listenerToSingle = new ListenerToSingle<>();
        Single<Object> mapAccessability = listenerToSingle
                .getSingle()
                .timeout(1, TimeUnit.SECONDS)
                .map(m -> null);
        presenter.loadShopsAndControlAccessablity(mapAccessability);
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            listenerToSingle.emitNextValue(googleMap);
        });
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        super.onDestroyView();
    }

    @OnClick(R.id.buttonTryAgain) void onTryAgainButtonClick(View view) {
        presenter.onTryAgainButtonClick();
    }

    @Override
    public void shopProgress(boolean progress) {
        progressView.setVisibility(progress ? View.VISIBLE : View.GONE);
    }


    @Override
    public void setupShopsList(List<ShopModel> shops) {
        pagerAdapter = new TextViewPagerAdapter(shops);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pagerChangeListener);
    }

    @Override
    public void setupShopMapkers(List<ShopModel> shops) {
        if (mMap != null) {
            for (ShopModel shop : shops) {
                ShopCoordinate coordinate = shop.getCoordinate();
                LatLng latLng = new LatLng(coordinate.getX(), coordinate.getY());
                mMap.addMarker(new MarkerOptions().position(latLng).title(shop.getName()));
            }
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
        if (mMap != null) {
            LatLng latLng = new LatLng(coordinate.getX(), coordinate.getY());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));
        }
    }

    @Override
    public void setShopByPositionOnPager(int position) {
        viewPager.removeOnPageChangeListener(pagerChangeListener);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(pagerChangeListener);
    }

    @Override
    public void onQueryChanged(@NonNull String query) {
        presenter.onQueryChanged(query);
    }

    @Override
    public void notifyShopsDataChanged() {
        if (pagerAdapter != null) pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView(boolean show) {
        errorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
