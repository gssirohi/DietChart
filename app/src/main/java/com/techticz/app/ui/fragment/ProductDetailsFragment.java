package com.techticz.app.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.interactor.LoadImageUseCase;
import com.techticz.app.ui.activity.DailyRoutineActivity;
import com.techticz.app.ui.customview.HeaderView;
import com.techticz.app.ui.customview.ProductCardView;
import com.techticz.app.ui.presenter.IProductDetailsScreenPresenter;
import com.techticz.app.ui.presenter.ProductDetailsScreenPresenter;
import com.techticz.app.ui.viewcontract.ProductDeatailsScreenViewContract;
import com.techticz.app.ui.viewmodel.ProductCardViewModel;
import com.techticz.app.ui.viewmodel.ProductDetailsViewModel;

import java.util.List;

import butterknife.Bind;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link DayMealsListFragment.OnListFragmentInteractionListener}
 * interface.
 */
public class ProductDetailsFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener
        , ProductDeatailsScreenViewContract {


    @Bind(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @Bind(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @Bind(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    private boolean isHideToolbarView = false;

    private String productUri;
    private FetchImageInteractor interactor;
    private LinearLayout cardContainer;
    private ProductDetailsViewModel viewModel;
    private IProductDetailsScreenPresenter iPresenter;
    private ImageView headerImage;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailsFragment() {
    }

    // TODO: Customize parameter initialization

    public static ProductDetailsFragment newInstance(String resUri) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(Key.PRODUCT_URI, resUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            productUri = args.getString(Key.PRODUCT_URI);
        }
        iPresenter = new ProductDetailsScreenPresenter(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_details_layout, container, false);
        //ButterKnife.bind(this,view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);
        ((DailyRoutineActivity) getActivity()).setSupportActionBar(toolbar);
        ((DailyRoutineActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((DailyRoutineActivity) getActivity()).getSupportActionBar().setTitle("");
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        toolbarHeaderView = (HeaderView) view.findViewById(R.id.toolbar_header_view);
        floatHeaderView = (HeaderView) view.findViewById(R.id.float_header_view);
        cardContainer = (LinearLayout) view.findViewById(R.id.product_card_container);
        headerImage = (ImageView) view.findViewById(R.id.character_image);
        initUi();

        return view;
    }

    private void addProductCardViews() {

        if (!TextUtils.isEmpty(viewModel.getDescription())) {
            ViewGroup description =
                    (ViewGroup) LayoutInflater.from(getContext())
                            .inflate(R.layout.product_description_layout, null, false);
            TextView tv_lable = (TextView) description.findViewById(R.id.tv_product_lable);
            TextView tv_desc = (TextView) description.findViewById(R.id.tv_product_description);
            tv_lable.setText("Description");
            tv_desc.setText(viewModel.getDescription());
            cardContainer.addView(description);
        }

        List<ProductCardViewModel> cardModels = viewModel.getProductCardViewModelList();
        for (ProductCardViewModel cardModel : cardModels) {
            ProductCardView cardView = new ProductCardView(getContext(), cardModel);
            cardView.setContract((DailyRoutineActivity) getActivity());
            cardContainer.addView(cardView);
        }
//        ProductCardView cardView = new ProductCardView(parent,viewModel.getProductCardViewModel(Products.COMIC));
//        cardContainer.addView(cardView);
    }

    private void initUi() {
        appBarLayout.addOnOffsetChangedListener(this);
        iPresenter.setView(this);
        iPresenter.onFetchProductDetailsRequest(productUri, true);

    }

    public void setHeaderView() {
        floatHeaderView.bindTo(viewModel.getName(), "" + viewModel.getId());
        toolbarHeaderView.bindTo(viewModel.getName(), "" + viewModel.getId());
        setImageOnView(headerImage, viewModel.getImageUrl());
    }

    private void setImageOnView(final ImageView imageView, String url) {

        if (TextUtils.isEmpty(url)) return;

        if (interactor != null)
            interactor.setCancelled(true);//discard previous results since you have new  imageUrl
        interactor = (FetchImageInteractor) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.FETCH_PRODUCT_IMAGE);
        interactor.execute(new LoadImageUseCase.Callback() {
            @Override
            public void onError(AppErrors error) {

            }

            @Override
            public void onImage(Bitmap image) {
                imageView.setImageBitmap(image);
                imageView.setVisibility(View.VISIBLE);
            }
        }, url, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof DayMealsListFragment.OnListFragmentInteractionListener) {
//            mListener = (DayMealsListFragment.OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }


    @Override
    public void onProductDetailsFetched(ProductDetailsViewModel viewModel) {
        this.viewModel = viewModel;
        setHeaderView();
        addProductCardViews();
    }

    @Override
    public void onError(String s) {

    }
}
