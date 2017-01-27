package com.example.shoplocator.ui.createAndEditShop.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coulcod.selectorview.SelectorView;
import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.dagger.createAndEditShop.CreateAndEditShopModule;
import com.example.shoplocator.ui.createAndEditShop.presenter.ICreateAndEditShopPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seotm on 27.01.17.
 */

public class CreateAndEditShopFragment extends Fragment implements ICreateAndEditShopView {

    public static final String PARAM_SHOP_ID = "shop_id";

    @Inject ICreateAndEditShopPresenter presenter;

    @BindView(R.id.editTextCardName) TextInputEditText editTextCardName;
    @BindView(R.id.editTextImageUrl) TextInputEditText editTextImageUrl;
    @BindView(R.id.editTextPosX) TextInputEditText editTextPosX;
    @BindView(R.id.editTextPosY) TextInputEditText editTextPosY;
    @BindView(R.id.selectorViewUserName) SelectorView selectorViewUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        App.instance().applicationComponent().plus(new CreateAndEditShopModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_and_edit_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.bindView(this);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void onSubmitButtonClick() {

    }
}
