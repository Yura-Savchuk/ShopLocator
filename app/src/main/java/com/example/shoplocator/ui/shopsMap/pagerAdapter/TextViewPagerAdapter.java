package com.example.shoplocator.ui.shopsMap.pagerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shoplocator.R;
import com.example.shoplocator.ui.model.ShopModel;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

public class TextViewPagerAdapter extends PagerAdapter {

    private final List<ShopModel> shopList;

    public TextViewPagerAdapter(@NonNull List<ShopModel> shopList) {
        this.shopList = shopList;
    }

    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = getViewAtPosition(position, collection);
        collection.addView(view, 0);
        onBindView(view, position);
        return view;
    }

    private View getViewAtPosition(int position, @NonNull ViewGroup container) {
        return onCreateView(position, container);
    }

    private View onCreateView(int position, @NonNull ViewGroup container) {
        return LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_shop_map_name, container, false);
    }

    private void onBindView(View view, int position) {
        TextView textView = (TextView) view;
        textView.setText(shopList.get(position).getName());
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
