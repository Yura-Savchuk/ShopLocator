package com.example.shoplocator.ui.shops;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.shoplocator.App;
import com.example.shoplocator.R;

import com.example.shoplocator.ui.shops.detail.ShopDetailActivity;
import com.example.shoplocator.ui.shops.detail.view.ShopDetailFragment;
import com.example.shoplocator.ui.shops.list.view.ShopsListFragment;
import com.example.shoplocator.util.fragment.FragmentRouteAbs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopsListActivity extends AppCompatActivity implements ShopListDelegate {

    @Inject FragmentRouteAbs fragmentRoute;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);
        App.instance().applicationComponent().inject(this);
        setupActionBar();
        twoPane = findViewById(R.id.shopDetailContainer) != null;
        if (savedInstanceState == null) {
            setupFragment();
        } else {
            deleteReduantDetailFragment();
        }
    }

    private void setupFragment() {
        fragmentRoute.setFragment(this, new ShopsListFragment());
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void deleteReduantDetailFragment() {
        fragmentRoute.deleteFragmentIfExist(this, ShopDetailFragment.class);
    }

    @Override
    public void showShopDetail(long shopId, View itemView) {
        Bundle arguments = new Bundle();
        arguments.putLong(ShopDetailFragment.PARAM_SHOP_ID, shopId);
        if (twoPane) {
            ShopDetailFragment fragment = new ShopDetailFragment();
            fragment.setArguments(arguments);
            fragmentRoute.replaceFragment(this, fragment, R.id.shopDetailContainer);
        } else {
            shopShopDetailActivity(itemView, arguments);
        }
    }

    private void shopShopDetailActivity(View itemView, Bundle arguments) {
        Intent intent = new Intent(this, ShopDetailActivity.class);
        intent.putExtras(arguments);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            View imageView = itemView.findViewById(R.id.imageView);
            String transitionName = imageView.getTransitionName();
            intent.putExtra(ShopDetailFragment.PARAM_IMAGE_VIEW_TRANSITION_NAME, transitionName);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, imageView, transitionName);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

}
