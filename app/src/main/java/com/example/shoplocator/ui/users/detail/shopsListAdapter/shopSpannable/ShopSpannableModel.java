package com.example.shoplocator.ui.users.detail.shopsListAdapter.shopSpannable;

import android.text.Spannable;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 22.01.17.
 */

public class ShopSpannableModel {

    private final Spannable owner;
    private final Spannable coordinate;

    public ShopSpannableModel(Spannable owner, Spannable coordinate) {
        this.owner = owner;
        this.coordinate = coordinate;
    }

    public Spannable getOwner() {
        return owner;
    }

    public Spannable getCoordinate() {
        return coordinate;
    }
}
