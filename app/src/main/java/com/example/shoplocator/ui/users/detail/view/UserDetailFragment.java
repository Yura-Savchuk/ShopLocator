package com.example.shoplocator.ui.users.detail.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.dagger.userDetail.UserDetailModule;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.shops.detail.ShopDetailActivity;
import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;
import com.example.shoplocator.ui.simpleShopsListAdapter.ShopsRecyclerViewDelegate;
import com.example.shoplocator.ui.users.detail.UserDetailActivity;
import com.example.shoplocator.ui.users.detail.presenter.IUserDetailPresenter;
import com.example.shoplocator.ui.simpleShopsListAdapter.ShopsRecyclerViewAdapter;
import com.example.shoplocator.util.fragment.FragmentTag;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

@FragmentTag(tag = "Detail_fragment")
public class UserDetailFragment extends Fragment implements IUserDetailView {

    private static final int REQUEST_CODE_SHOP_DETAIL = 1;

    @Inject IUserDetailPresenter presenter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.errorView) View errorView;
    @BindView(R.id.progressView) View progressView;

    private ShopsRecyclerViewAdapter shopsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().applicationComponent().plus(new UserDetailModule()).inject(this);
        fetchArguments();
    }

    private void fetchArguments() {
        Bundle arguments = getArguments();
        String userId = arguments.getString(UserDetailActivity.PARAM_USER_ID);
        String userName = arguments.getString(UserDetailActivity.PARAM_USER_NAME);
        if (userId == null || userName == null) throw new RuntimeException("Param user_id or user_name is missing.");
        presenter.setUserId(userId);
        presenter.setUserName(userName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.bindView(this);
        presenter.setupTitle();
        presenter.setupUserShops();
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
                case REQUEST_CODE_SHOP_DETAIL: {
                    onShopDetailResult(data);
                    break;
                }
                default:{
                    throw new RuntimeException("Unexpected request code.");
                }
            }
        }
    }

    private void onShopDetailResult(@NonNull Intent data) {
        String shopId = data.getStringExtra(ShopDetailActivity.PARAM_SHOP_ID);
        if (shopId == null) throw new RuntimeException("Shop ID is missing.");
        if (data.getBooleanExtra(ShopDetailActivity.PARAM_SHOP_HAS_BEEN_REMOVED, false)) {
            presenter.onShopHasBeenRemovedFromDetailView(shopId);
        } else if (data.getBooleanExtra(ShopDetailActivity.PARAM_SHOP_HAS_BEEN_EDITED, false)) {
            presenter.onShopHasBeenEditedFromDetailView(shopId);
        }
    }

    @OnClick(R.id.buttonTryAgain) void onTryAgainButtonClick(View view) {
        presenter.onRetryButtonClick();
    }

    @Override
    public void setTitle(@NonNull String title) {
        FragmentActivity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public void setProgress(boolean progress) {
        progressView.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setupShopsList(@NonNull List<ShopModel> shops) {
        shopsAdapter = new ShopsRecyclerViewAdapter(shops, getContext());
        shopsAdapter.setDelegate((position, itemView) -> presenter.onItemClick(position, itemView));
        recyclerView.setAdapter(shopsAdapter);
    }

    @Override
    public void shopErrorView(boolean show) {
        errorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showDetailShopView(@NonNull String shopId, View itemView) {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra(ShopDetailActivity.PARAM_SHOP_ID, shopId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            View imageView = itemView.findViewById(R.id.imageView);
            String transitionName = imageView.getTransitionName();
            intent.putExtra(ShopDetailFragment.PARAM_IMAGE_VIEW_TRANSITION_NAME, transitionName);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), imageView, transitionName);
            startActivityForResult(intent, REQUEST_CODE_SHOP_DETAIL, options.toBundle());
        } else {
            startActivityForResult(intent, REQUEST_CODE_SHOP_DETAIL);
        }
    }

    @Override
    public void notifyShopItemRemoved(int position) {
        shopsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyShopItemChanged(int position) {
        shopsAdapter.notifyItemChanged(position);
    }
}
