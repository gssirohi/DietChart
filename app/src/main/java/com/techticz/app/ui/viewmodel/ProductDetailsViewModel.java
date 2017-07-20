package com.techticz.app.ui.viewmodel;


import com.techticz.app.constant.Products;
import com.techticz.app.domain.model.ProductModel;
import com.techticz.app.domain.model.SummaryListContainer;
import com.techticz.app.domain.model.SummaryModel;
import com.techticz.app.domain.model.pojo.Thumbnail;
import com.techticz.app.ui.viewmodel.contract.IProductCardViewModel;
import com.techticz.app.ui.viewmodel.contract.IProductDetailsViewModel;
import com.techticz.app.ui.viewmodel.contract.IProductViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 7/7/16.
 */

public class ProductDetailsViewModel implements IProductDetailsViewModel {


    private final ProductModel productModel;

    public ProductDetailsViewModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    @Override
    public String getId() {
        return "" + productModel.getId();
    }

    @Override
    public String getName() {
        return productModel.getName();
    }


    @Override
    public String getImageUrl() {
        Thumbnail thumbnail = productModel.getThumbnail();
        if (thumbnail != null)
            return thumbnail.getPath() + "/standard_fantastic." + productModel.getThumbnail().getExtension();
        else
            return null;
    }


    private <T extends SummaryModel> List<IProductViewModel> createList(List<T> models) {
        ArrayList<IProductViewModel> viewModels = new ArrayList<>();
        for (T t : models) {
            viewModels.add(new ProductViewModel(t));
        }
        return viewModels;
    }

    @Override
    public ProductModel getModel() {
        return productModel;
    }

    public IProductCardViewModel getProductCardViewModel(Products type) {
        return new ProductCardViewModel(getProductSummaryListContainer(type), type);
    }

    @Override
    public List<ProductCardViewModel> getProductCardViewModelList() {
        ArrayList<ProductCardViewModel> models = new ArrayList<>();
        for (Products type : Products.getList()) {
            SummaryListContainer summaryListContainer = getProductSummaryListContainer(type);
            if (summaryListContainer != null) {
                models.add(new ProductCardViewModel(summaryListContainer, type));
            }
        }
        return models;

    }

    @Override
    public String getDescription() {
        return productModel.getDescription();
    }

    private <T extends SummaryListContainer> T getProductSummaryListContainer(Products type) {
        /*switch (type) {
            case COMIC:
                return (T) productModel.getComicSummaryListContainer();
            case EVENT:
                return (T) productModel.getEventSummaryListContainer();
            case SERIES:
                return (T) productModel.getSeriesSummaryListContainer();
            case STORY:
                return (T) productModel.getStorySummaryListContainer();
            case CHARACTER:
                return (T) productModel.getCharacterSummaryListContainer();
            case CREATOR:
                return (T) productModel.getCreatorSummaryListContainer();
        }*/
        return null;
    }
}
