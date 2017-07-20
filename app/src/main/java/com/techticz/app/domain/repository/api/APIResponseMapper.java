package com.techticz.app.domain.repository.api;

import com.techticz.app.constant.Products;
import com.techticz.app.domain.model.ProductModel;
import com.techticz.app.domain.model.pojo.CharacterDataContainer;
import com.techticz.app.domain.model.pojo.CharacterResponseContainer;
import com.techticz.app.domain.model.pojo.Comic;
import com.techticz.app.domain.model.pojo.ComicsDataContainer;
import com.techticz.app.domain.model.pojo.ComicsResponseContainer;
import com.techticz.app.domain.model.pojo.Creator;
import com.techticz.app.domain.model.pojo.CreatorDataContainer;
import com.techticz.app.domain.model.pojo.CreatorResponseContainer;
import com.techticz.app.domain.model.pojo.Event;
import com.techticz.app.domain.model.pojo.EventDataContainer;
import com.techticz.app.domain.model.pojo.EventResponseContainer;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.Series;
import com.techticz.app.domain.model.pojo.SeriesDataContainer;
import com.techticz.app.domain.model.pojo.SeriesResponseContainer;
import com.techticz.app.domain.model.pojo.Story;
import com.techticz.app.domain.model.pojo.StoryDataContainer;
import com.techticz.app.domain.model.pojo.StoryResponseContainer;
import com.techticz.app.domain.repository.database.Converters;
import com.techticz.app.network.ResponseContainer;
import com.techticz.dietchart.backend.foodEntityApi.model.FoodEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 11/7/16.
 */
public class APIResponseMapper {


    public <T extends ProductModel> List<T> getProductListFromResponse(ResponseContainer container, Products type) {
        switch (type) {
            case COMIC:
                return (List<T>) getComicsListFromResponse(container);
            case CHARACTER:
                // return (List<T>) getCharactersListFromResponse(container);
            case EVENT:
                return (List<T>) getEventListFromResponse(container);
            case SERIES:
                return (List<T>) getSeriesListFromResponse(container);
            case STORY:
                return (List<T>) getStoryListFromResponse(container);
            case CREATOR:
                return (List<T>) getCreatorListFromResponse(container);
        }
        return null;
    }

    public <T extends ProductModel> T getProductFromResponse(ResponseContainer container, Products type) {
        switch (type) {
            case COMIC:
                return (T) getComicFromResponse(container);
            case CHARACTER:
                //  return (T) getCharacterFromResponse(container);
            case EVENT:
                return (T) getEventFromResponse(container);
            case SERIES:
                return (T) getSeriesFromResponse(container);
            case STORY:
                return (T) getStoryFromResponse(container);
            case CREATOR:
                return (T) getCreatorFromResponse(container);
        }
        return null;
    }

    public List<Meal> getCharactersListFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        CharacterResponseContainer listContainer = (CharacterResponseContainer) container;
        CharacterDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults();
    }

    public List<Event> getEventListFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        EventResponseContainer listContainer = (EventResponseContainer) container;
        EventDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults();
    }

    public List<Series> getSeriesListFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        SeriesResponseContainer listContainer = (SeriesResponseContainer) container;
        SeriesDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults();
    }


    public List<Story> getStoryListFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        StoryResponseContainer listContainer = (StoryResponseContainer) container;
        StoryDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults();
    }


    public List<Creator> getCreatorListFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        CreatorResponseContainer listContainer = (CreatorResponseContainer) container;
        CreatorDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults();
    }

    public List<Comic> getComicsListFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        ComicsResponseContainer listContainer = (ComicsResponseContainer) container;
        ComicsDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults();
    }

    public Meal getCharacterFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        CharacterResponseContainer listContainer = (CharacterResponseContainer) container;
        CharacterDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults().get(0);
    }

    public Series getSeriesFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        SeriesResponseContainer listContainer = (SeriesResponseContainer) container;
        SeriesDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults().get(0);
    }

    public Story getStoryFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        StoryResponseContainer listContainer = (StoryResponseContainer) container;
        StoryDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults().get(0);
    }

    public Event getEventFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        EventResponseContainer listContainer = (EventResponseContainer) container;
        EventDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults().get(0);
    }


    public Creator getCreatorFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        CreatorResponseContainer listContainer = (CreatorResponseContainer) container;
        CreatorDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults().get(0);
    }

    private Comic getComicFromResponse(ResponseContainer container) {
        if (container == null)
            return null;

        ComicsResponseContainer listContainer = (ComicsResponseContainer) container;
        ComicsDataContainer data = listContainer.getProductsDataContainer();
        if (data == null) {
            return null;
        }
        return data.getResults().get(0);
    }

    public List<Food> getFoodsFromEntity(List<FoodEntity> list) {
        List<Food> foods = new ArrayList<>();
        if(list == null) return foods;
        for(FoodEntity e : list){
            Food f = new Food();

            f.setUid(e.getUid());
            f.setContentPerServing(e.getContentPerServing());
            f.setCholestrol(e.getCholestrol());
            f.setCalcium(e.getCalcium());
            f.setCalory(e.getCalory());
            f.setCarbs(e.getCarbs());
            f.setDesc(e.getDesc());
            f.setEatable(e.getEatable());
            f.setFat(e.getFat());
            f.setFiber(e.getFiber());
            f.setIron(e.getIron());
            f.setMagnissium(e.getMagnissium());
            f.setName(e.getName());
            f.setPotassium(e.getPotassium());
            f.setPrefRoutine(Converters.stringtoList(e.getPrefRoutine()));
            f.setProtine(e.getProtine());
            f.setRichIn(Converters.stringtoList(e.getRichIn()));
            f.setServingId(e.getServingId());
            f.setSugar(e.getSugar());
            f.setSodium(e.getSodium());
            f.setType(e.getType());
            f.setUnitId(e.getUnitId());
            f.setVitaminA(e.getVitaminA());
            f.setVitaminB(e.getVitaminB());
            f.setVitaminC(e.getVitaminC());
            f.setVitaminD(e.getVitaminD());
            f.setVitaminE(e.getVitaminE());
            f.setVitaminK(e.getVitaminK());
            f.setZinc(e.getZinc());

            f.setBlobServingUrl(e.getBlobServingUrl());
            f.setBlobKey(e.getBlobKey());

            foods.add(f);
        }
        return foods;
    }

    public FoodEntity getEntityFromFood(Food f) {
        FoodEntity e = new FoodEntity();

        if(f.getUid() == null || f.getUid() == 0)
            e.setUid(null);
        else
        e.setUid(f.getUid());

        e.setContentPerServing(f.getContentPerServing());
        e.setCholestrol(f.getCholestrol());
        e.setCalcium(f.getCalcium());
        e.setCalory(f.getCalory());
        e.setCarbs(f.getCarbs());
        e.setDesc(f.getDesc());
        e.setEatable(f.isEatable());
        e.setFat(f.getFat());
        e.setFiber(f.getFiber());
        e.setIron(f.getIron());
        e.setMagnissium(f.getMagnissium());
        e.setName(f.getName());
        e.setPotassium(f.getPotassium());
        e.setPrefRoutine(Converters.listToString(f.getPrefRoutine()));
        e.setProtine(f.getProtine());
        e.setRichIn(Converters.listToString(f.getRichIn()));
        e.setServingId(f.getServingId());
        e.setSugar(f.getSugar());
        e.setSodium(f.getSodium());
        e.setType(f.getType());
        e.setUnitId(f.getUnitId());
        e.setVitaminA(f.getVitaminA());
        e.setVitaminB(f.getVitaminB());
        e.setVitaminC(f.getVitaminC());
        e.setVitaminD(f.getVitaminD());
        e.setVitaminE(f.getVitaminE());
        e.setVitaminK(f.getVitaminK());
        e.setZinc(f.getZinc());
        e.setBlobServingUrl(f.getBlobServingUrl());
        e.setBlobKey(f.getBlobKey());
        return e;
    }
}
