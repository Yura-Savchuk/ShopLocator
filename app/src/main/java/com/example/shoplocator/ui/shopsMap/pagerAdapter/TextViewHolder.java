package com.example.shoplocator.ui.shopsMap.pagerAdapter;

import android.view.View;
import android.widget.TextView;

import com.example.shoplocator.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 26.01.17.
 */

public class TextViewHolder {

    @BindView(R.id.textViewShopName) TextView textViewShopName;

    public TextViewHolder(View itemView) {
        ButterKnife.bind(this, itemView);
    }

}
