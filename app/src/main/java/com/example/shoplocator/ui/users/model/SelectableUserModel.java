package com.example.shoplocator.ui.users.model;

import com.example.shoplocator.ui.model.UserModel;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class SelectableUserModel extends UserModel {

    private boolean selected;

    public SelectableUserModel(String id, String name) {
        super(id, name);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
