package com.example.shoplocator.ui.shops.list.view;

import android.app.Activity;
import android.content.Intent;
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
import com.example.shoplocator.ui.createAndEditShop.CreateAndEditShopActivity;
import com.example.shoplocator.ui.shops.ShopListDelegate;
import com.example.shoplocator.ui.shops.list.listAdapter.CheckableShopsRecyclerViewAdapter;
import com.example.shoplocator.ui.shops.list.presenter.IShopsListPresenter;
import com.example.shoplocator.ui.shops.model.SelectableShopModel;
import com.example.shoplocator.util.ui.progress.ProgressDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopsListFragment extends Fragment implements IShopsListView {

    private static final int CREATE_SHOP_REQUEST_CODE = 1;

    @Inject IShopsListPresenter presenter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private CheckableShopsRecyclerViewAdapter recyclerViewAdapter;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CREATE_SHOP_REQUEST_CODE: {
                    onCreateShopResult(data);
                    break;
                }
                default:{
                    throw new RuntimeException("Unexpected request code.");
                }
            }
        }
    }

    private void onCreateShopResult(@NonNull Intent intent) {
        String shopId = intent.getStringExtra(CreateAndEditShopActivity.PARAM_SHOP_ID);
        if (shopId == null) throw new RuntimeException("Shop id is missing.");
        presenter.addShopById(shopId);
    }

    @Override
    public void setupShopsList(@NonNull List<SelectableShopModel> shops) {
        recyclerViewAdapter = new CheckableShopsRecyclerViewAdapter(shops, getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setDelegate(presenter::onItemClick);
    }

    @Override
    public void showShopDetail(@NonNull String shopId, View itemView) {
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
        presenter.onRemoveActionSelected();
    }

    @Override
    public void onDoneActionSelected() {
        presenter.onDoneActionSelected();
    }

    @Override
    public void onCancelActionSelected() {
        presenter.onCancelActionSelected();
    }

    @Override
    public void onCreateActionSelected() {
        presenter.onCreateActionSelected();
    }

    @Override
    public void setToolbarInEditState(boolean editState) {
        Activity activity = getActivity();
        if (activity instanceof ShopListDelegate) {
            ((ShopListDelegate) activity).setEditState(editState);
        }
    }

    @Override
    public void notifyItemRemoved(int position) {
        recyclerViewAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemChanged(int position) {
        recyclerViewAdapter.notifyItemChanged(position);
    }

    @Override
    public void notifyItemInserted(int position) {
        recyclerViewAdapter.notifyItemInserted(position);
    }

    @Override
    public void showCreateShopView() {
        Intent intent = new Intent(getActivity(), CreateAndEditShopActivity.class);
        startActivityForResult(intent, CREATE_SHOP_REQUEST_CODE);
    }

    @Override
    public void onEditShopResult(@NonNull String shopId) {
        presenter.onEditShopResult(shopId);
    }

    @Override
    public void onDeleteShopResult(@NonNull String shopId) {
        presenter.onDeleteShopResult(shopId);
    }
}
