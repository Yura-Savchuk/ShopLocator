package com.example.shoplocator.ui.errorFragment;

import com.example.shoplocator.R;
import com.example.shoplocator.ui.errorFragment.model.ErrorViewModel;
import com.example.shoplocator.ui.errorFragment.model.ImageModel;

/**
 * Created by seotm on 05.01.17.
 */

public class ErrorFragmentConfig implements IErrorFragmentConfig {
    @Override
    public ErrorViewModel noInternetConnectionModel() {
        ImageModel imageModel = new ImageModel(R.drawable.no_internet_connection);
        return new ErrorViewModel(imageModel, R.string.no_internet_connection_title, R.string.no_internet_connection_description);
    }

    @Override
    public ErrorViewModel undefinedError() {
        throw new RuntimeException("Undefined error is not allowed in this project.");
    }
}
