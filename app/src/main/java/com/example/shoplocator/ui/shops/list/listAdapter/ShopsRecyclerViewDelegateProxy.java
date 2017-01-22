package com.example.shoplocator.ui.shops.list.listAdapter;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 23.01.17.
 */

public class ShopsRecyclerViewDelegateProxy implements ShopsRecyclerViewDelegate {

    private ShopsRecyclerViewDelegate recyclerViewDelegate;

    public void setRecyclerViewDelegate(ShopsRecyclerViewDelegate recyclerViewDelegate) {
        this.recyclerViewDelegate = recyclerViewDelegate;
    }

    @Override
    public void onItemClick(int position) {
        if (recyclerViewDelegate != null) recyclerViewDelegate.onItemClick(position);
    }
}
