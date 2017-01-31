package com.example.shoplocator.ui.users.list.view;

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
import com.example.shoplocator.dagger.usersList.UsersListModule;
import com.example.shoplocator.ui.errorFragment.ShowErrorFragmentDelegate;
import com.example.shoplocator.ui.model.UserModel;
import com.example.shoplocator.ui.users.UserListDelegate;
import com.example.shoplocator.ui.users.list.listAdapter.UsersRecyclerViewAdapter;
import com.example.shoplocator.ui.users.list.presenter.IUsersListPresenter;
import com.example.shoplocator.util.ui.progress.ProgressDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class UsersListFragment extends Fragment implements IUsersListView {

    @Inject IUsersListPresenter presenter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private UsersRecyclerViewAdapter usersAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        App.instance().applicationComponent().plus(new UsersListModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.bindView(this);
        presenter.setupUsersList();
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void setupUsersList(List<UserModel> users) {
        usersAdapter = new UsersRecyclerViewAdapter(users, getContext());
        usersAdapter.setDelegate(position -> presenter.onItemClick(position));
        recyclerView.setAdapter(usersAdapter);
    }

    @Override
    public void setProgress(boolean progress) {
        if (progress) {
            ProgressDialog.showIfHidden(getActivity());
        } else {
            ProgressDialog.hideIfShown();
        }
    }

    @Override
    public void showErrorView() {
        Activity activity = getActivity();
        if (activity instanceof ShowErrorFragmentDelegate) {
            ((ShowErrorFragmentDelegate) activity).showNoInternetConnectionError();
        }
    }

    @Override
    public void showUserDetailView(@NonNull String userId, @NonNull String userName) {
        Activity activity = getActivity();
        if (activity instanceof UserListDelegate) {
            ((UserListDelegate) activity).showUserDetail(userId, userName);
        }
    }
}
