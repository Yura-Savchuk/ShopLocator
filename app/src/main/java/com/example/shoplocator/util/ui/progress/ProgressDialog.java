package com.example.shoplocator.util.ui.progress;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import static com.example.shoplocator.util.ui.progress.ProgressDialogConfig.config;

/**
 * Created by seotm on 05.01.17.
 */

public class ProgressDialog extends DialogFragment {

    private static final String TAG = "tag_progress_dialog";
    private static ProgressDialog mInstance;

    public static void showIfHidden(@NonNull FragmentActivity activity){
        showIfHidden(activity, config().cancelable);
    }

    public static void showIfHidden(@NonNull FragmentActivity activity, boolean cancelable) {
        mInstance = new ProgressDialog();
        mInstance.setCancelable(cancelable);
        mInstance.show(activity.getSupportFragmentManager(), TAG);
    }

    public static void hideIfShown() {
        if (mInstance != null) {
            /**
             * NullPointerException could be if parent class {@link DialogFragment}
             * is detached from {@link FragmentActivity}.
             */
            try {
                mInstance.dismissAllowingStateLoss();
            } catch (NullPointerException e) {
                mInstance = null;
            }
        }
    }

    public static boolean isShown() {
        boolean shown = false;
        if (mInstance != null) {
            shown = mInstance.shown;
        }
        return shown;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
    }

    private boolean shown = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setupWindow();
        return createLayout();
    }

    private View createLayout() {
        return new ProgressDialogDefaultLayoutCreator(getContext()).createLayout();
    }

    private void setupWindow() {
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (shown) return;
        super.show(manager, tag);
        shown = true;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (shown) return 0;
        int result = super.show(transaction, tag);
        shown = true;
        return result;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        shown = false;
        mInstance = null;
    }

}
