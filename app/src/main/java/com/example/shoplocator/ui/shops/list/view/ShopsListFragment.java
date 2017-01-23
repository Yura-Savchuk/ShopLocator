package com.example.shoplocator.ui.shops.list.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.dagger.shopsList.ShopsModule;
import com.example.shoplocator.ui.shops.ShopListDelegate;
import com.example.shoplocator.ui.shops.list.listAdapter.ShopsRecyclerViewAdapter;
import com.example.shoplocator.ui.shops.list.presenter.IShopsListPresenter;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.model.CheckableShopModel;
import com.example.shoplocator.util.ui.progress.ProgressDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsListFragment extends Fragment implements IShopsListView {

    @Inject IShopsListPresenter presenter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private ShopsRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        App.instance().applicationComponent().plus(new ShopsModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shops_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        presenter.bindView(this);
        presenter.setupShopsList();
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void setupShopsList(@NonNull List<CheckableShopModel> shops) {
        recyclerViewAdapter = new ShopsRecyclerViewAdapter(shops, getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setDelegate(presenter::onItemClick);
    }

    @Override
    public void showShopDetail(long shopId, View itemView) {
        Activity activity = getActivity();
        if (activity instanceof ShopListDelegate) {
            ((ShopListDelegate) activity).showShopDetail(shopId, itemView);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            ProgressDialog.showIfHidden(getActivity());
        } else {
            ProgressDialog.hideIfShown();
        }
    }

    @Override
    public void onRemoveActionSelected() {

    }
}
