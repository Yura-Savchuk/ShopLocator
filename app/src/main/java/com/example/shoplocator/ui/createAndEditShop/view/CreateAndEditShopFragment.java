package com.example.shoplocator.ui.createAndEditShop.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coulcod.selectorview.Checkable;
import com.coulcod.selectorview.OnValuesChangeListener;
import com.coulcod.selectorview.SelectionViewDialog;
import com.coulcod.selectorview.SelectorView;
import com.coulcod.selectorview.SelectorViewAdapter;
import com.coulcod.selectorview.SelectorViewDialogDelegate;
import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.buissines.createAndEditShop.validation.field.ShopFormInvalidField;
import com.example.shoplocator.dagger.createAndEditShop.CreateAndEditShopModule;
import com.example.shoplocator.ui.createAndEditShop.model.CheckableUserModel;
import com.example.shoplocator.ui.createAndEditShop.presenter.ICreateAndEditShopPresenter;
import com.example.shoplocator.util.listenerAdapter.TextWatcherAdapter;
import com.example.shoplocator.util.ui.progress.ProgressDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seotm on 27.01.17.
 */

public class CreateAndEditShopFragment extends Fragment implements ICreateAndEditShopView {

    public static final String PARAM_SHOP_ID = "shop_id";

    private static final String EMPTY_STRING = "";

    @Inject ICreateAndEditShopPresenter presenter;

    @BindView(R.id.textInputLayoutShopName) TextInputLayout textInputLayoutShopName;
    @BindView(R.id.textInputLayoutImageUrl) TextInputLayout textInputLayoutImageUrl;
    @BindView(R.id.textInputLayoutPosX) TextInputLayout textInputLayoutPosX;
    @BindView(R.id.textInputLayoutPosY) TextInputLayout textInputLayoutPosY;
    @BindView(R.id.userNameError) TextView userNameError;

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
        fetchArguments();
    }

    private void fetchArguments() {
        Bundle arguments = getArguments();
        String shopId = arguments.getString(PARAM_SHOP_ID);
        presenter.setShopId(shopId);
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
        presenter.prepareForm();
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void onSubmitButtonClick() {
        presenter.submitForm();
    }

    @Override
    public void showProgress(boolean progress) {
        if (progress) {
            ProgressDialog.showIfHidden(getActivity());
        } else {
            ProgressDialog.hideIfShown();
        }
    }

    @Override
    public void returnSuccessResult(@NonNull String shopId) {
        Intent intent = new Intent();
        intent.putExtra(PARAM_SHOP_ID, shopId);
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @NonNull
    @Override
    public String getShopName() {
        return editTextCardName.getText().toString();
    }

    @NonNull
    @Override
    public String getImageUrl() {
        return editTextImageUrl.getText().toString();
    }

    @NonNull
    @Override
    public String getPosX() {
        return editTextPosX.getText().toString();
    }

    @NonNull
    @Override
    public String getPosY() {
        return editTextPosY.getText().toString();
    }

    @Override
    public void setupUserSelector(@NonNull List<CheckableUserModel> users) {
        SelectorViewAdapter selectorViewAdapter = new SelectorViewAdapter(categoriesDelegate, users);
        selectorViewAdapter.setTitle(getString(R.string.owner));
        selectorViewUserName.setAdapter(selectorViewAdapter);
        selectorViewUserName.setOnValuesChangeListener((selectorView, values) -> {
            if (!userNameError.getText().toString().isEmpty()) {
                userNameError.setText(EMPTY_STRING);
            }
        });
    }

    private final SelectorViewDialogDelegate categoriesDelegate = (values, title, mode, callback) -> {
        if (values != null && values.size() != 0) {
            final AlertDialog dialog = new SelectionViewDialog.Builder(getActivity())
                    .setValues(values)
                    .setTitle(title)
                    .setSelectionMode(mode)
                    .setSelectionDialogDelegate(callback)
                    .setSingleChoiceLayout(R.layout.select_single_choice)
                    .setListViewLayout(R.layout.select_list_view)
                    .create();
            dialog.show();
        }
    };

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), R.string.no_internet_connection_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setInvalidErrors(@NonNull List<ShopFormInvalidField> fields) {
        for (ShopFormInvalidField field : fields) {
            switch (field.getFieldCode()) {
                case ShopFormInvalidField.FIELD_CODE_SHOP_NAME: {
                    setErrorToField(textInputLayoutShopName, field);
                    break;
                }
                case ShopFormInvalidField.FIELD_CODE_IMAGE_URL: {
                    setErrorToField(textInputLayoutImageUrl, field);
                    break;
                }
                case ShopFormInvalidField.FIELD_CODE_OWNER: {
                    setErrorToSelectorView(userNameError, field);
                    break;
                }
                case ShopFormInvalidField.FIELD_CODE_POSITION_X: {
                    setErrorToField(textInputLayoutPosX, field);
                    break;
                }
                case ShopFormInvalidField.FIELD_CODE_POSITION_Y: {
                    setErrorToField(textInputLayoutPosY, field);
                    break;
                }
                default:{
                    throw new RuntimeException("An un handeled field code exist.");
                }
            }
        }
    }

    private void setErrorToField(@NonNull TextInputLayout textInputLayout, @NonNull ShopFormInvalidField field) {
        textInputLayout.setError(field.getMessage());
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcherAdapter(){
                @Override
                public void afterTextChanged(Editable s) {
                    textInputLayout.setError(null);
                    editText.removeTextChangedListener(this);
                }
            });
        }
    }

    private void setErrorToSelectorView(@NonNull TextView selectorErrorText, @NonNull ShopFormInvalidField field) {
        selectorErrorText.setText(field.getMessage());
    }

    @Override
    public void showErrorMessage(@NonNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setShopName(@NonNull String shopName) {
        editTextCardName.setText(shopName);
    }

    @Override
    public void setImageUrl(@NonNull String imageUrl) {
        editTextImageUrl.setText(imageUrl);
    }

    @Override
    public void setPositionX(@NonNull String position) {
        editTextPosX.setText(position);
    }

    @Override
    public void setPositionY(@NonNull String position) {
        editTextPosY.setText(position);
    }
}
