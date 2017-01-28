package com.example.shoplocator.ui.createAndEditShop.model;

import android.support.annotation.NonNull;

import com.coulcod.selectorview.Checkable;
import com.example.shoplocator.ui.model.UserModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 27.01.17.
 */

public class CheckableUserModel extends UserModel implements Checkable {

    private boolean selected;

    public CheckableUserModel(long id, String name) {
        super(id, name);
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @NonNull
    @Override
    public String getText() {
        return getName();
    }

    @Override
    public Checkable copy() {
        CheckableUserModel userModel = new CheckableUserModel(getId(), getName());
        userModel.setSelected(selected);
        return userModel;
    }
}
