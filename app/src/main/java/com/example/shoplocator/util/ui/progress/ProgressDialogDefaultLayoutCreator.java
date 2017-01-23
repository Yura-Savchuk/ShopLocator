package com.example.shoplocator.util.ui.progress;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import static com.example.shoplocator.util.ui.progress.ProgressDialogConfig.config;
import static com.example.shoplocator.util.ui.progress.ProgressDialogValueConverter.dpToPx;

/**
 * Created by seotm on 05.01.17.
 */

public class ProgressDialogDefaultLayoutCreator {

    private static final float FRAME_LAYOUT_PADDING_IN_DP = 30;

    private final Context context;

    private RelativeLayout container;
    private CardView cardView;

    public ProgressDialogDefaultLayoutCreator(Context context) {
        this.context = context;
    }

    View createLayout() {
        setupContainer();
        setupCardView();
        setupProgressBar();
        return container;
    }

    private void setupContainer() {
        container = new RelativeLayout(context);
    }

    private void setupCardView() {
        cardView = new CardView(context);
        cardView.setRadius(dpToPx(config().cornerRadiusInDP, context));
        cardView.setCardBackgroundColor(config().cardColor);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        container.addView(cardView, params);
    }

    private void setupProgressBar() {
        FrameLayout frameLayout = new FrameLayout(context);
        int frameLayoutPadding = dpToPx(FRAME_LAYOUT_PADDING_IN_DP, context);
        frameLayout.setPadding(frameLayoutPadding, frameLayoutPadding, frameLayoutPadding, frameLayoutPadding);
        cardView.addView(frameLayout);
        ProgressBar progressBar = new ProgressBar(context);
        frameLayout.addView(progressBar);
    }

}
