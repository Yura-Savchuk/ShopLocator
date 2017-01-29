package com.example.shoplocator.ui.users.detail.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.dagger.userDetail.UserDetailModule;
import com.example.shoplocator.ui.model.ShopModel;
import com.example.shoplocator.ui.users.detail.UserDetailActivity;
import com.example.shoplocator.ui.users.detail.presenter.IUserDetailPresenter;
import com.example.shoplocator.ui.users.detail.shopsListAdapter.ShopsRecyclerViewAdapter;
import com.example.shoplocator.util.fragment.FragmentTag;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

@FragmentTag(tag = "Detail_fragment")
public class UserDetailFragment extends Fragment implements IUserDetailView {

    @Inject IUserDetailPresenter presenter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

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
        progressBar.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setupShopsList(@NonNull List<ShopModel> shops) {
        ShopsRecyclerViewAdapter recyclerViewAdapter = new ShopsRecyclerViewAdapter(shops, getContext());
        recyclerViewAdapter.setDelegate(position -> presenter.onItemClick(position));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void shopErrorView() {

    }
}
