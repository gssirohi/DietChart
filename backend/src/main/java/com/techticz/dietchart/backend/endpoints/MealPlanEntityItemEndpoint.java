package com.techticz.dietchart.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.techticz.dietchart.backend.entities.MealEntity;
import com.techticz.dietchart.backend.entities.MealPlanEntityItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import ch.boye.httpclientandroidlib.util.TextUtils;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "mealPlanEntityApi",
        version = "v1",
        resource = "mealPlanEntityItem",
        namespace = @ApiNamespace(
                ownerDomain = "backend.dietchart.techticz.com",
                ownerName = "backend.dietchart.techticz.com",
                packagePath = ""
        )
)
public class MealPlanEntityItemEndpoint {

    private static final Logger logger = Logger.getLogger(MealPlanEntityItemEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(MealPlanEntityItem.class);
    }

    /**
     * Returns the {@link MealPlanEntityItem} with the corresponding ID.
     *
     * @param uid the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code MealPlanEntityItem} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "mealPlanEntityItem/{uid}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public MealPlanEntityItem get(@Named("uid") Long uid) throws NotFoundException {
        logger.info("Getting MealPlanEntityItem with ID: " + uid);
        MealPlanEntityItem mealPlanEntityItem = ofy().load().type(MealPlanEntityItem.class).id(uid).now();
        if (mealPlanEntityItem == null) {
            throw new NotFoundException("Could not find MealPlanEntityItem with ID: " + uid);
        }
        return mealPlanEntityItem;
    }

    /**
     * Inserts a new {@code MealPlanEntityItem}.
     */
    @ApiMethod(
            name = "insert",
            path = "mealPlanEntityItem",
            httpMethod = ApiMethod.HttpMethod.POST)
    public MealPlanEntityItem insert(MealPlanEntityItem mealPlanEntityItem,@Named("autoLoad") Boolean autoLoad,@Named("prefRoutines") List<Integer> prefRoutines) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that mealPlanEntityItem.uid has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        if(autoLoad) {
            loadWithMeals(mealPlanEntityItem,prefRoutines);
        }
        ofy().save().entity(mealPlanEntityItem).now();
        logger.info("Created MealPlanEntityItem with ID: " + mealPlanEntityItem.getUid());

        return ofy().load().entity(mealPlanEntityItem).now();
    }

    private void loadWithMeals(MealPlanEntityItem mealPlan, List<Integer> prefRoutines) {
        float targetCalory = mealPlan.getDailyCalory();
        float[] dtc = getDistributedTargetCalories(targetCalory,prefRoutines);
        List<List<MealEntity>> inventry = loadInventryForRoutines(prefRoutines);


            String loadedDayMeals = "0:0:0:0:0:0:0";
                    loadedDayMeals = getLoadedDayMeals(prefRoutines,mealPlan.getMondayMeals(),dtc,inventry);
                    mealPlan.setMondayMeals(loadedDayMeals);
                    loadedDayMeals = getLoadedDayMeals(prefRoutines,mealPlan.getTuesdayMeals(),dtc,inventry);
                    mealPlan.setTuesdayMeals(loadedDayMeals);
                    loadedDayMeals = getLoadedDayMeals(prefRoutines,mealPlan.getWednesdayMeals(),dtc,inventry);
                    mealPlan.setWednesdayMeals(loadedDayMeals);
                    loadedDayMeals = getLoadedDayMeals(prefRoutines,mealPlan.getThursdayMeals(),dtc,inventry);
                    mealPlan.setThursdayMeals(loadedDayMeals);
                    loadedDayMeals = getLoadedDayMeals(prefRoutines,mealPlan.getFridayMeals(),dtc,inventry);
                    mealPlan.setFridayMeals(loadedDayMeals);
                    loadedDayMeals = getLoadedDayMeals(prefRoutines,mealPlan.getSaturdayMeals(),dtc,inventry);
                    mealPlan.setSaturdayMeals(loadedDayMeals);
                    loadedDayMeals = getLoadedDayMeals(prefRoutines,mealPlan.getSundayMeals(),dtc,inventry);
                    mealPlan.setSundayMeals(loadedDayMeals);



    }

    private String getLoadedDayMeals(List<Integer> prefRoutines, String meals, float[] dtc, List<List<MealEntity>> inventry) {
        String[] mealIds = meals.split(":");
        for(Integer rId : prefRoutines){
            String id = mealIds[rId-1];
            if(id.equalsIgnoreCase("0")){
                // find out suitable meal for this routine matching calory target
                float target = dtc[rId - 1];
                MealEntity e = findSuitableMealFromInventry(target,inventry.get(rId-1));
                if(e == null){
                    //do nothing
                } else {
                    mealIds[rId - 1] = "" + e.getUid();
                }
            }
        }
       return getStringFromArray(mealIds);

    }

    private MealEntity findSuitableMealFromInventry(float target, List<MealEntity> meals) {
        if(meals == null) return null;
        //collect all the meals in the range and pick one randomly

        List<MealEntity> inRange = new ArrayList<>();
        for(MealEntity e:meals){
            float c = e.extractNutritient().getCalory();
            if(c >= target ){
                if(c - 50 <= target){
                    //in range
                    inRange.add(e);
                }
            } else {
                if(c + 50 >= target){
                    //in range
                    inRange.add(e);
                }
            }
        }
        if(inRange.size() == 0) return null;
        int size = inRange.size();
        //pick random
        Random rand = new Random();
        int  random = rand.nextInt(size);
        return inRange.get(random);
    }

    private String getStringFromArray(String[] mealIds) {
        String s = "";
        for(int i = 0;i<mealIds.length;i++){
            s = s+mealIds[i];
            if(i < mealIds.length-1){
                s = s+":";
            }
        }
        return s;
    }

    private List<List<MealEntity>> loadInventryForRoutines(List<Integer> prefRoutines) {
        List<List<MealEntity>> inventry = new ArrayList<>();
        inventry.add(new ArrayList<MealEntity>());
        inventry.add(new ArrayList<MealEntity>());
        inventry.add(new ArrayList<MealEntity>());
        inventry.add(new ArrayList<MealEntity>());
        inventry.add(new ArrayList<MealEntity>());
        inventry.add(new ArrayList<MealEntity>());
        inventry.add(new ArrayList<MealEntity>());
        MealEntityEndpoint endpoint = new MealEntityEndpoint();
        for(Integer rID: prefRoutines){
            String routineKey = "R"+rID;
            Collection<MealEntity> result = endpoint.list(routineKey, null, null).getItems();
            inventry.get(rID-1).addAll(result);
        }
        return inventry;
    }

    private float[] getDistributedTargetCalories(float targetCalory,List<Integer> prefRoutines) {
        float[] dtc = new float[7];
        if(prefRoutines == null) return dtc;
        int[] percents = new int[]{10,20,20,10,10,20,10};
        float totalDistributed = 100;
        for(Integer rID: prefRoutines){
            dtc[rID-1] = (percents[rID-1]*targetCalory)/100;
            totalDistributed = totalDistributed + dtc[rID-1];
        }
        float caloryLeft = targetCalory - totalDistributed;

        //equally distribute left calories
        for(Integer rID: prefRoutines){
            dtc[rID-1] = dtc[rID-1] + caloryLeft/prefRoutines.size();
        }
        return dtc;
    }

    /**
     * Updates an existing {@code MealPlanEntityItem}.
     *
     * @param uid                the ID of the entity to be updated
     * @param mealPlanEntityItem the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code uid} does not correspond to an existing
     *                           {@code MealPlanEntityItem}
     */
    @ApiMethod(
            name = "update",
            path = "mealPlanEntityItem/{uid}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public MealPlanEntityItem update(@Named("autoLoad") Boolean autoLoad,@Named("prefRoutines") List<Integer> prefRoutines,@Named("uid") Long uid, MealPlanEntityItem mealPlanEntityItem) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(uid);
        if(autoLoad){
            loadWithMeals(mealPlanEntityItem,prefRoutines);
        }
        ofy().save().entity(mealPlanEntityItem).now();
        logger.info("Updated MealPlanEntityItem: " + mealPlanEntityItem);
        return ofy().load().entity(mealPlanEntityItem).now();
    }

    /**
     * Deletes the specified {@code MealPlanEntityItem}.
     *
     * @param uid the ID of the entity to delete
     * @throws NotFoundException if the {@code uid} does not correspond to an existing
     *                           {@code MealPlanEntityItem}
     */
    @ApiMethod(
            name = "remove",
            path = "mealPlanEntityItem/{uid}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("uid") Long uid) throws NotFoundException {
        checkExists(uid);
        ofy().delete().type(MealPlanEntityItem.class).id(uid).now();
        logger.info("Deleted MealPlanEntityItem with ID: " + uid);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "mealPlanEntityItem",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MealPlanEntityItem> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MealPlanEntityItem> query = ofy().load().type(MealPlanEntityItem.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MealPlanEntityItem> queryIterator = query.iterator();
        List<MealPlanEntityItem> mealPlanEntityItemList = new ArrayList<MealPlanEntityItem>(limit);
        while (queryIterator.hasNext()) {
            mealPlanEntityItemList.add(queryIterator.next());
        }
        return CollectionResponse.<MealPlanEntityItem>builder().setItems(mealPlanEntityItemList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    @ApiMethod(
            name = "listRecommended",
            path = "mealPlanEntityItem/recommended",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MealPlanEntityItem> listRecommended(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MealPlanEntityItem> query = ofy().load().type(MealPlanEntityItem.class).limit(limit)
                .filter("recommended",true);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MealPlanEntityItem> queryIterator = query.iterator();
        List<MealPlanEntityItem> mealPlanEntityItemList = new ArrayList<MealPlanEntityItem>(limit);
        while (queryIterator.hasNext()) {
            mealPlanEntityItemList.add(queryIterator.next());
        }
        return CollectionResponse.<MealPlanEntityItem>builder().setItems(mealPlanEntityItemList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    @ApiMethod(
            name = "listMyPlan",
            path = "mealPlanEntityItem/myplans",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MealPlanEntityItem> listMyPlan(@Nullable @Named("cursor") String cursor,@Nullable @Named("creater") String creater, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MealPlanEntityItem> query = null;
        if(!TextUtils.isEmpty(creater))
         query = ofy().load().type(MealPlanEntityItem.class).limit(limit)
                .filter("creater",creater);
        else
            query = ofy().load().type(MealPlanEntityItem.class).limit(limit);

        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MealPlanEntityItem> queryIterator = query.iterator();
        List<MealPlanEntityItem> mealPlanEntityItemList = new ArrayList<MealPlanEntityItem>(limit);
        while (queryIterator.hasNext()) {
            mealPlanEntityItemList.add(queryIterator.next());
        }
        return CollectionResponse.<MealPlanEntityItem>builder().setItems(mealPlanEntityItemList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long uid) throws NotFoundException {
        try {
            ofy().load().type(MealPlanEntityItem.class).id(uid).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find MealPlanEntityItem with ID: " + uid);
        }
    }
}