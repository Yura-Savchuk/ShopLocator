package com.example.shoplocator.ui.errorFragment.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoplocator.R;
import com.example.shoplocator.ui.errorFragment.ShowErrorFragmentDelegate;
import com.example.shoplocator.ui.errorFragment.model.ErrorViewModel;
import com.example.shoplocator.ui.errorFragment.model.ImageModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by seotm on 05.01.17.
 */

public class ErrorFragment extends Fragment {

    public static ErrorFragment create(@NonNull ErrorViewModel errorViewModel) {
        ErrorFragment fragment = new ErrorFragment();
        fragment.viewModel = errorViewModel;
        return fragment;
    }

    @SuppressWarnings("NullableProblems")
    @NonNull
    private ErrorViewModel viewModel;

    @BindView(R.id.imageViewConnectionIcon) ImageView imageViewConnectionIcon;
    @BindView(R.id.textViewTitle) TextView textViewTitle;
    @BindView(R.id.textViewDescription) TextView textViewDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error, container, false);
        ButterKnife.bind(this, view);
        setupImage();
        setupTitleAndDescription();
        return view;
    }

    private void setupImage() {
        imageViewConnectionIcon.setImageDrawable(getImageDrawable());
    }

    private Drawable getImageDrawable() {
        ImageModel imageModel = viewModel.getImageModel();
        int drawableRes = imageModel.getDrawableRes();
        if (!imageModel.isVectorImage()) {
            return ContextCompat.getDrawable(getContext(), drawableRes);
        }
        return VectorDrawableCompat.create(getResources(), drawableRes, null);
    }

    private void setupTitleAndDescription() {
        textViewTitle.setText(viewModel.getTitleStringRes());
        textViewDescription.setText(viewModel.getDescriptionStringRes());
    }

    @OnClick(R.id.buttonTryAgain) void onTryAgainButtonClick(View view) {
        Activity activity = getActivity();
        if (activity instanceof ShowErrorFragmentDelegate) {
            ((ShowErrorFragmentDelegate) activity).handleErrorViewRetryButtonClick();
        }
    }

}
