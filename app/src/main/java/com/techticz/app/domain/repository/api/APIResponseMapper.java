package com.techticz.app.domain.repository.api;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Products;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.exception.AppParseException;
import com.techticz.app.domain.model.ProductModel;
import com.techticz.app.domain.model.pojo.CharacterDataContainer;
import com.techticz.app.domain.model.pojo.CharacterResponseContainer;
import com.techticz.app.domain.model.pojo.Comic;
import com.techticz.app.domain.model.pojo.ComicsDataContainer;
import com.techticz.app.domain.model.pojo.ComicsResponseContainer;
import com.techticz.app.domain.model.pojo.Creator;
import com.techticz.app.domain.model.pojo.CreatorDataContainer;
import com.techticz.app.domain.model.pojo.CreatorResponseContainer;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Event;
import com.techticz.app.domain.model.pojo.EventDataContainer;
import com.techticz.app.domain.model.pojo.EventResponseContainer;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.Series;
import com.techticz.app.domain.model.pojo.SeriesDataContainer;
import com.techticz.app.domain.model.pojo.SeriesResponseContainer;
import com.techticz.app.domain.model.pojo.Story;
import com.techticz.app.domain.model.pojo.StoryDataContainer;
import com.techticz.app.domain.model.pojo.StoryResponseContainer;
import com.techticz.app.domain.repository.database.Converters;
import com.techticz.app.network.ResponseContainer;

import com.techticz.dietchart.backend.foodEntityApi.model.FoodEntity;
import com.techticz.dietchart.backend.mealEntityApi.model.MealEntity;
import com.techticz.dietchart.backend.mealPlanEntityApi.model.MealPlanEntityItem;


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

        try{
            List<Food> foods = new ArrayList<>();
        if(list == null) return foods;
        for(FoodEntity e : list){
            Food f = new Food();
            f.setRecommended(e.getRecommended());
            f.setVerified(e.getVerified());
            f.setVerifiedBy(e.getVerifiedBy());
            f.setPublished(e.getPublished());

            f.setCreator(e.getCreator());

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
            f.setProtine(e.getProtine());
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
    } catch (Exception e){
        throw new AppParseException(e);
    }
    }

    public FoodEntity getEntityFromFood(Food f) {
     try {
        FoodEntity e = new FoodEntity();

        if(f.getUid() == null || f.getUid() == 0)
            e.setUid(null);
        else
        e.setUid(f.getUid());
         e.setRecommended(f.isRecommended());
         e.setVerified(f.isVerified());
         e.setVerifiedBy(f.getVerifiedBy());
         e.setPublished(f.isPublished());
         e.setCreator(f.getCreator());
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
        e.setProtine(f.getProtine());
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
    } catch (Exception e){
        throw new AppParseException(e);
    }
    }

    public MealEntity getEntityFromMeal(Meal m) {
     try{
        MealEntity e = new MealEntity();
        if(m.getUid() == null || m.getUid() == 0)
            m.setUid(null);
        e.setUid(m.getUid());
         e.setRecommended(m.isRecommended());
         e.setVerified(m.isVerified());
         e.setVerifiedBy(m.getVerifiedBy());
         e.setPublished(m.isPublished());
         e.setCreator(m.getCreator());
        e.setBlobKey(m.getBlobKey());
        e.setBlobServingUrl(m.getBlobServingUrl());
        e.setAddedFoods(Converters.addedFoodListToString(m.getAddedFoods()));
        e.setDesc(m.getDesc());
        e.setName(m.getName());
        e.setR1(m.isR1());
         e.setR2(m.isR2());
         e.setR3(m.isR3());
         e.setR4(m.isR4());
         e.setR5(m.isR5());
         e.setR6(m.isR6());
         e.setR7(m.isR7());
         e.setCholestrol(m.getCholestrol());
         e.setCalcium(m.getCalcium());
         e.setCalory(m.getCalory());
         e.setCarbs(m.getCarbs());
         e.setFat(m.getFat());
         e.setFiber(m.getFiber());
         e.setIron(m.getIron());
         e.setMagnissium(m.getMagnissium());
         e.setPotassium(m.getPotassium());
         e.setProtine(m.getProtine());
         e.setSugar(m.getSugar());
         e.setSodium(m.getSodium());
         e.setVitaminA(m.getVitaminA());
         e.setVitaminB(m.getVitaminB());
         e.setVitaminC(m.getVitaminC());
         e.setVitaminD(m.getVitaminD());
         e.setVitaminE(m.getVitaminE());
         e.setVitaminK(m.getVitaminK());
         e.setZinc(e.getZinc());
        return e;
    } catch (Exception e){
        throw new AppParseException(e);
    }
    }

    public List<Meal> getMealsFromEntity(List<MealEntity> es){
       try {
        List<Meal> meals = new ArrayList<>();
        if(es == null || es.isEmpty()) return meals;

        for(MealEntity e:es) {
            Meal m = new Meal();
            m.setUid(e.getUid());
            m.setRecommended(e.getRecommended());
            m.setVerified(e.getVerified());
            m.setVerifiedBy(e.getVerifiedBy());
            m.setPublished(e.getPublished());
            m.setCreator(e.getCreator());
            m.setBlobKey(e.getBlobKey());
            m.setBlobServingUrl(e.getBlobServingUrl());
            m.setAddedFoods(Converters.stringToAddedFoodList(e.getAddedFoods()));
            m.setDesc(e.getDesc());
            m.setName(e.getName());
            m.setR1(e.getR1());
            m.setR2(e.getR2());
            m.setR3(e.getR3());
            m.setR4(e.getR4());
            m.setR5(e.getR5());
            m.setR6(e.getR6());
            m.setR7(e.getR7());
            m.setCholestrol(e.getCholestrol());
            m.setCalcium(e.getCalcium());
            m.setCalory(e.getCalory());
            m.setCarbs(e.getCarbs());
            m.setFat(e.getFat());
            m.setFiber(e.getFiber());
            m.setIron(e.getIron());
            m.setMagnissium(e.getMagnissium());
            m.setPotassium(e.getPotassium());
            m.setProtine(e.getProtine());
            m.setSugar(e.getSugar());
            m.setSodium(e.getSodium());
            m.setVitaminA(e.getVitaminA());
            m.setVitaminB(e.getVitaminB());
            m.setVitaminC(e.getVitaminC());
            m.setVitaminD(e.getVitaminD());
            m.setVitaminE(e.getVitaminE());
            m.setVitaminK(e.getVitaminK());
            m.setZinc(e.getZinc());
            meals.add(m);
        }
        return meals;
    } catch (Exception e){
        throw new AppParseException(e);
    }
    }

    public MealPlanEntityItem getEntitiyFromMealPlan(MealPlan p) throws AppParseException {
        try {
            MealPlanEntityItem e = new MealPlanEntityItem();
            if (p.getUid() == null || p.getUid() == 0) p.setUid(null);
            e.setUid(p.getUid());
            e.setName(p.getName());
            e.setDesc(p.getDesc());
            e.setRecommended(p.isRecommended());
            e.setVerified(p.isVerified());
            e.setVerifiedBy(p.getVerifiedBy());
            e.setPublished(p.isPublished());
            e.setCreator(p.getCreator());
            e.setBlobServingUrl(p.getBlobServingUrl());
            e.setDailyCalory(p.getDailyCalory());
            e.setHealthGoal(p.getHealthGoal());
            e.setMondayMeals(getEntityDayMeals(p.getMondayMeals()));
            e.setTuesdayMeals(getEntityDayMeals(p.getTuesdayMeals()));
            e.setWednesdayMeals(getEntityDayMeals(p.getWednesdayMeals()));
            e.setThursdayMeals(getEntityDayMeals(p.getThursdayMeals()));
            e.setFridayMeals(getEntityDayMeals(p.getFridayMeals()));
            e.setSaturdayMeals(getEntityDayMeals(p.getSaturdayMeals()));
            e.setSundayMeals(getEntityDayMeals(p.getSundayMeals()));

            e.setCholestrol(p.getCholestrol());
            e.setCalcium(p.getCalcium());
            e.setCalory(p.getCalory());
            e.setCarbs(p.getCarbs());
            e.setFat(p.getFat());
            e.setFiber(p.getFiber());
            e.setIron(p.getIron());
            e.setMagnissium(p.getMagnissium());
            e.setPotassium(p.getPotassium());
            e.setProtine(p.getProtine());
            e.setSugar(p.getSugar());
            e.setSodium(p.getSodium());
            e.setVitaminA(p.getVitaminA());
            e.setVitaminB(p.getVitaminB());
            e.setVitaminC(p.getVitaminC());
            e.setVitaminD(p.getVitaminD());
            e.setVitaminE(p.getVitaminE());
            e.setVitaminK(p.getVitaminK());
            e.setZinc(p.getZinc());
            return e;
        } catch (Exception e){
            throw new AppParseException(e);
        }

    }

    public MealPlan getMealPlanFromEntity(MealPlanEntityItem e) {
        try{
        MealPlan plan = new MealPlan();
        plan.setUid(e.getUid());
        plan.setName(e.getName());
        plan.setDesc(e.getDesc());
            plan.setRecommended(e.getRecommended());
            plan.setVerified(e.getVerified());
            plan.setVerifiedBy(e.getVerifiedBy());
            plan.setPublished(e.getPublished());
        plan.setCreator(e.getCreator());
        plan.setBlobServingUrl(e.getBlobServingUrl());
        plan.setDailyCalory(e.getDailyCalory());
        plan.setHealthGoal(e.getHealthGoal());
        plan.setMondayMeals(getDBDayMeals(e.getMondayMeals()));
        plan.setTuesdayMeals(getDBDayMeals(e.getTuesdayMeals()));
        plan.setWednesdayMeals(getDBDayMeals(e.getWednesdayMeals()));
        plan.setThursdayMeals(getDBDayMeals(e.getThursdayMeals()));
        plan.setFridayMeals(getDBDayMeals(e.getFridayMeals()));
        plan.setSaturdayMeals(getDBDayMeals(e.getSaturdayMeals()));
        plan.setSundayMeals(getDBDayMeals(e.getSundayMeals()));
            plan.setCholestrol(e.getCholestrol());
            plan.setCalcium(e.getCalcium());
            plan.setCalory(e.getCalory());
            plan.setCarbs(e.getCarbs());
            plan.setFat(e.getFat());
            plan.setFiber(e.getFiber());
            plan.setIron(e.getIron());
            plan.setMagnissium(e.getMagnissium());
            plan.setPotassium(e.getPotassium());
            plan.setProtine(e.getProtine());
            plan.setSugar(e.getSugar());
            plan.setSodium(e.getSodium());
            plan.setVitaminA(e.getVitaminA());
            plan.setVitaminB(e.getVitaminB());
            plan.setVitaminC(e.getVitaminC());
            plan.setVitaminD(e.getVitaminD());
            plan.setVitaminE(e.getVitaminE());
            plan.setVitaminK(e.getVitaminK());
            plan.setZinc(e.getZinc());
        return plan;
    } catch (Exception er){
        throw new AppParseException(er);
    }
    }

    private String getEntityDayMeals(DayMeals dayMeals) {
        return Converters.dayMealToString(dayMeals);
    }


    private DayMeals getDBDayMeals(String dayMeals) {
        return Converters.stringToDayMeals(dayMeals);
    }

    public List<Meal> getMealsFromUserMealEntity(List<com.techticz.dietchart.backend.appUserApi.model.MealEntity> es) {
        try {
            List<Meal> meals = new ArrayList<>();
            if(es == null || es.isEmpty()) return meals;

            for(com.techticz.dietchart.backend.appUserApi.model.MealEntity e:es) {
                Meal m = new Meal();
                m.setUid(e.getUid());
                m.setRecommended(e.getRecommended());
                m.setVerified(e.getVerified());
                m.setVerifiedBy(e.getVerifiedBy());
                m.setPublished(e.getPublished());
                m.setCreator(e.getCreator());
                m.setBlobKey(e.getBlobKey());
                m.setBlobServingUrl(e.getBlobServingUrl());
                m.setAddedFoods(Converters.stringToAddedFoodList(e.getAddedFoods()));
                m.setDesc(e.getDesc());
                m.setName(e.getName());
                m.setR1(e.getR1());
                m.setR2(e.getR2());
                m.setR3(e.getR3());
                m.setR4(e.getR4());
                m.setR5(e.getR5());
                m.setR6(e.getR6());
                m.setR7(e.getR7());
                m.setCholestrol(e.getCholestrol());
                m.setCalcium(e.getCalcium());
                m.setCalory(e.getCalory());
                m.setCarbs(e.getCarbs());
                m.setFat(e.getFat());
                m.setFiber(e.getFiber());
                m.setIron(e.getIron());
                m.setMagnissium(e.getMagnissium());
                m.setPotassium(e.getPotassium());
                m.setProtine(e.getProtine());
                m.setSugar(e.getSugar());
                m.setSodium(e.getSodium());
                m.setVitaminA(e.getVitaminA());
                m.setVitaminB(e.getVitaminB());
                m.setVitaminC(e.getVitaminC());
                m.setVitaminD(e.getVitaminD());
                m.setVitaminE(e.getVitaminE());
                m.setVitaminK(e.getVitaminK());
                m.setZinc(e.getZinc());
                meals.add(m);
            }
            return meals;
        } catch (Exception e){
            throw new AppParseException(e);
        }
    }

    public List<Food> getFoodsFromUserFoodEntity(List<com.techticz.dietchart.backend.appUserApi.model.FoodEntity> list) {
        try{
            List<Food> foods = new ArrayList<>();
            if(list == null) return foods;
            for(com.techticz.dietchart.backend.appUserApi.model.FoodEntity e : list){
                Food f = new Food();

                f.setUid(e.getUid());
                f.setRecommended(e.getRecommended());
                f.setVerified(e.getVerified());
                f.setVerifiedBy(e.getVerifiedBy());
                f.setPublished(e.getPublished());
                f.setCreator(e.getCreator());
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
                f.setProtine(e.getProtine());
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
        } catch (Exception e){
            throw new AppParseException(e);
        }
    }

    public List<MealPlan> getMealPlansFromUserEntities(List<com.techticz.dietchart.backend.appUserApi.model.MealPlanEntityItem> planEntities) {
        try{
            List<MealPlan> plans = new ArrayList<>();
            if(planEntities == null) return plans;
            for(com.techticz.dietchart.backend.appUserApi.model.MealPlanEntityItem e : planEntities){
                MealPlan p = getMealPlanFromUserEntity(e);
                if(p!= null){
                    plans.add(p);
                }
            }
            return plans;
        } catch (Exception er){
            throw new AppParseException(er);
        }
    }

    public MealPlan getMealPlanFromUserEntity(com.techticz.dietchart.backend.appUserApi.model.MealPlanEntityItem e) {
        try{
            MealPlan plan = new MealPlan();
            plan.setUid(e.getUid());
            plan.setName(e.getName());
            plan.setDesc(e.getDesc());
            plan.setRecommended(e.getRecommended());
            plan.setVerified(e.getVerified());
            plan.setVerifiedBy(e.getVerifiedBy());
            plan.setPublished(e.getPublished());
            plan.setCreator(e.getCreator());
            plan.setBlobServingUrl(e.getBlobServingUrl());
            plan.setDailyCalory(e.getDailyCalory());
            plan.setHealthGoal(e.getHealthGoal());
            plan.setMondayMeals(getDBDayMeals(e.getMondayMeals()));
            plan.setTuesdayMeals(getDBDayMeals(e.getTuesdayMeals()));
            plan.setWednesdayMeals(getDBDayMeals(e.getWednesdayMeals()));
            plan.setThursdayMeals(getDBDayMeals(e.getThursdayMeals()));
            plan.setFridayMeals(getDBDayMeals(e.getFridayMeals()));
            plan.setSaturdayMeals(getDBDayMeals(e.getSaturdayMeals()));
            plan.setSundayMeals(getDBDayMeals(e.getSundayMeals()));

            plan.setCholestrol(e.getCholestrol());
            plan.setCalcium(e.getCalcium());
            plan.setCalory(e.getCalory());
            plan.setCarbs(e.getCarbs());
            plan.setFat(e.getFat());
            plan.setFiber(e.getFiber());
            plan.setIron(e.getIron());
            plan.setMagnissium(e.getMagnissium());
            plan.setPotassium(e.getPotassium());
            plan.setProtine(e.getProtine());
            plan.setSugar(e.getSugar());
            plan.setSodium(e.getSodium());
            plan.setVitaminA(e.getVitaminA());
            plan.setVitaminB(e.getVitaminB());
            plan.setVitaminC(e.getVitaminC());
            plan.setVitaminD(e.getVitaminD());
            plan.setVitaminE(e.getVitaminE());
            plan.setVitaminK(e.getVitaminK());
            plan.setZinc(e.getZinc());
            return plan;
        } catch (Exception er){
            throw new AppParseException(er);
        }
    }
}
