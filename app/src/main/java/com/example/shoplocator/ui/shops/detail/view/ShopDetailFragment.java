package com.example.shoplocator.ui.shops.detail.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoplocator.App;
import com.example.shoplocator.R;
import com.example.shoplocator.dagger.shopDetail.ShopDetailModule;
import com.example.shoplocator.ui.model.ShopCoordinate;
import com.example.shoplocator.ui.shops.detail.presenter.IShopDetailPresenter;
import com.example.shoplocator.ui.shops.list.listAdapter.shopSpannable.ShopSpannableModelFactory;
import com.example.shoplocator.util.fragment.FragmentTag;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@FragmentTag(tag = "Detail_fragment")
public class ShopDetailFragment extends Fragment implements IShopDetailView {

    public static final String PARAM_SHOP_ID = "shop_id";
    public static final String PARAM_IMAGE_VIEW_TRANSITION_NAME = "image_view_transition_name";

    @Inject IShopDetailPresenter presenter;

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.textViewShopOwner) TextView textViewShopOwner;
    @BindView(R.id.textViewCoordinate) TextView textViewCoordinate;

    private String imageViewTransitionName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        App.instance().applicationComponent().plus(new ShopDetailModule()).inject(this);
        fetchArguments();
        postponeEnterTransitionIfExist();
    }

    private void postponeEnterTransitionIfExist() {
        if (imageViewTransitionName != null) {
            getActivity().supportPostponeEnterTransition();
        }
    }

    private void fetchArguments() {
        Bundle arguments = getArguments();
        if (!arguments.containsKey(PARAM_SHOP_ID)) {
            throw new RuntimeException("Param shop_id is missing.");
        }
        imageViewTransitionName = arguments.getString(PARAM_IMAGE_VIEW_TRANSITION_NAME);
        presenter.setShopId(arguments.getString(PARAM_SHOP_ID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.bindView(this);
        presenter.setupShopDetails();
        setartEntierTransitionIfExist();
    }

    private void setartEntierTransitionIfExist() {
        if (imageViewTransitionName != null) {
            ViewCompat.setTransitionName(imageView, imageViewTransitionName);
            imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    getActivity().supportStartPostponedEnterTransition();
                    imageViewTransitionName = null;
                    return true;
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void setTitle(@NonNull String name) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(name);
        }
    }

    @Override
    public void setImage(@NonNull String imageUrl) {
        Picasso.with(getContext())
                .load(imageUrl)
                .centerCrop()
                .fit()
                .into(imageView);
    }

    @Override
    public void setOwner(@NonNull String name) {
        Spannable spannableName = new ShopSpannableModelFactory(getContext()).createOwnerSpannable(name);
        textViewShopOwner.setText(spannableName);
    }

    @Override
    public void setCoordinate(@NonNull ShopCoordinate coordinate) {
        Spannable spannableCoordinate = new ShopSpannableModelFactory(getContext()).createCoordsSpannable(coordinate);
        textViewCoordinate.setText(spannableCoordinate);
    }
}
