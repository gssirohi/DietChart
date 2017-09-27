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
import com.techticz.dietchart.backend.entities.AppUser;
import com.techticz.dietchart.backend.entities.FoodEntity;
import com.techticz.dietchart.backend.entities.MealEntity;
import com.techticz.dietchart.backend.entities.MealPlanEntityItem;
import com.techticz.dietchart.backend.model.AddedFood;
import com.techticz.dietchart.backend.model.Authorities;
import com.techticz.dietchart.backend.model.MarketResponse;
import com.techticz.dietchart.backend.model.SyncInfoResponse;
import com.techticz.dietchart.backend.model.UpdateInfo;
import com.techticz.dietchart.backend.model.UserLoginResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import ch.boye.httpclientandroidlib.util.TextUtils;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 *
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
  name = "appUserApi",
  version = "v1",
  resource = "appUser",
  namespace = @ApiNamespace(
    ownerDomain = "backend.dietchart.techticz.com",
    ownerName = "backend.dietchart.techticz.com",
    packagePath=""
  )
)
public class AppUserEndpoint {

  private static final Logger logger = Logger.getLogger(AppUserEndpoint.class.getName());

  private static final int DEFAULT_LIST_LIMIT = 20;

  static {
    // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
    ObjectifyService.register(AppUser.class);
  }

    /**
     * Inserts a new {@code AppUser}.
     */
    @ApiMethod(
            name = "login",
            path = "login",
            httpMethod = ApiMethod.HttpMethod.POST)
    public UserLoginResponse login(AppUser appUser) throws NotFoundException {
        UserLoginResponse loginResponse = new UserLoginResponse();
        if(/*checkIfFireBaseUIDExists(appUser.getUid())*/true){
           // appUser = update(appUser.getUid(),appUser);

            loginResponse.setWelcomeMsg("Welcome to DietChart "+appUser.getName());
            //SyncInfoResponse  syncInfoResponse = appSync(appUser);
          //  loginResponse.setSyncInfoResponse(syncInfoResponse);
            loginResponse.setMarket(fetchCloudMarket(appUser.getIdentifier()));
            loginResponse.setSyncInfoResponse(null);
        } else {
   //         ofy().save().entity(appUser).now();
        }

        logger.info("Login Response sent");

        return loginResponse;
    }

    private MarketResponse fetchCloudMarket(String user) {
        MarketResponse resp = new MarketResponse();
        MealPlanEntityItemEndpoint endpoint = new MealPlanEntityItemEndpoint();
        CollectionResponse<MealPlanEntityItem> recommended = endpoint.listRecommended(null, null);
        CollectionResponse<MealPlanEntityItem> myPlans = endpoint.listMyPlan(null,user, null);
        if(recommended == null) return resp;
        if(myPlans != null) {
            recommended.getItems().addAll(myPlans.getItems());
        }

        if(recommended.getItems() != null) {
            Iterator<MealPlanEntityItem> it = recommended.getItems().iterator();
            List<Long> exclusiveMealIds = new ArrayList<>();
            while (it.hasNext()){
                MealPlanEntityItem plan = it.next();
                //String mealids = plan.getMutualMealIds();
                List<Long> mealIdList = plan.getMutualMealIds();// stringToList(mealids);
                System.out.println("[plan: "+plan.getName()+"]exclusiveMealIds count"+exclusiveMealIds.size());
                System.out.println("[plan: "+plan.getName()+"]mealListId count"+mealIdList.size());
                exclusiveMealIds.removeAll(mealIdList);
                System.out.println("[plan: "+plan.getName()+"]exclusiveMealIds count after remove all"+exclusiveMealIds.size());
                exclusiveMealIds.addAll(mealIdList);
                System.out.println("[plan: "+plan.getName()+"]exclusiveMealIds count after addAll"+exclusiveMealIds.size());
            }
            exclusiveMealIds.remove(new Long(0));
            System.out.println("exclusiveMealIds count after adding plan"+exclusiveMealIds.size());

            if(!exclusiveMealIds.isEmpty()) {
                CollectionResponse<MealEntity> meals = fetchMeals(exclusiveMealIds);
                resp.setExclusiveMeals(meals);
                if (meals != null && meals.getItems() != null) {
                    Iterator<MealEntity> itM = meals.getItems().iterator();
                    List<Long> exclusiveFoodIds = new ArrayList<>();
                    while (itM.hasNext()) {
                        MealEntity meal = itM.next();
                        String afs = meal.getAddedFoods();
                        List<AddedFood> afList = stringToAddedFoodList(afs);
                        List<Long> foodIds = idsFromAddedFoodList(afList);
                        exclusiveFoodIds.removeAll(foodIds);
                        exclusiveFoodIds.addAll(foodIds);
                    }
                    CollectionResponse<FoodEntity> foods = fetchFoods(exclusiveFoodIds);
                    resp.setExclusiveFood(foods);
                }
            }
        }
        resp.setPlans(recommended);

        return resp;
    }

    private List<Long> idsFromAddedFoodList(List<AddedFood> afList) {
        List<Long> list = new ArrayList<>();
        if(afList == null) return list;

        for(AddedFood af: afList){
            list.add(af.getFoodId());
        }
        return list;
    }

    public static List<AddedFood> stringToAddedFoodList(String str){
        List<AddedFood> addedFoods = new ArrayList<>();
        if(TextUtils.isEmpty(str)) return addedFoods;

        String[] aFoods = str.split(":");
        for (String aF : aFoods) {
            String[] s = aF.split("@");
            Long id = Long.parseLong(s[0]);
            int serving = Integer.parseInt(s[1]);
            AddedFood addedFood = new AddedFood(id,serving);
            addedFoods.add(addedFood);
        }
        return addedFoods;
    }
    private CollectionResponse<MealEntity> fetchMeals(List<Long> exclusiveMealIds) {
        CollectionResponse<MealEntity> list;
        if(!exclusiveMealIds.isEmpty()) {
            MealEntityEndpoint endpoint = new MealEntityEndpoint();
            list = endpoint.listForIds(getArrayFromList(exclusiveMealIds), null, null);
            return list;
        } return null;
    }

    public static Long[] getArrayFromList(List<Long> list){
        if(list == null) return new Long[0];

        List<Long> nonZero = new ArrayList<>();
        for(Long l:list){
            if(l != 0)
            nonZero.add(l);
        }
        Long[] array = new Long[nonZero.size()];
        int index = 0;
        for(Long l:nonZero){
            array[index++] = l;
        }
        return array;
    }

    private CollectionResponse<FoodEntity> fetchFoods(List<Long> exclusiveFoodIds) {
        if(!exclusiveFoodIds.isEmpty()) {
            FoodEntityEndpoint endpoint = new FoodEntityEndpoint();
            CollectionResponse<FoodEntity> list = endpoint.listForIds(getArrayFromList(exclusiveFoodIds), null, null);
            return list;
        } return null;
    }

    public static List<Long> stringToList(String str) {
        List<Long> list = new ArrayList<>();
        if(TextUtils.isEmpty(str)) return list;

        String[] ids = str.split(":");
        for (String num : ids) {
            try {
                list.add(Long.parseLong(num));
            } catch (Exception e) {

            }
        }
        return list;
    }

    /**
     * App Sync a new {@code AppUser}.
     */
    @ApiMethod(
            name = "appSync",
            path = "appSync",
            httpMethod = ApiMethod.HttpMethod.POST)
    public SyncInfoResponse appSync(AppUser appUser) throws NotFoundException {
        SyncInfoResponse resp = new SyncInfoResponse();
        if(checkIfFireBaseUIDExists(appUser.getUid())){

            resp.setForceLogout(false);
            resp.setForceLogoutMsg("");
            resp.setLoggedIn(true);
            resp.setUpdateInfo(new UpdateInfo());
            resp.setAuthorities(new Authorities());
        } else {
            throw new NotFoundException("Could not find AppUser while syncing, UID" + appUser.getUid());
        }

        logger.info("Synced App for user.");
        return resp;
    }

    /**
   * Returns the {@link AppUser} with the corresponding ID.
   *
   * @param uid the ID of the entity to be retrieved
   * @return the entity with the corresponding ID
   * @throws NotFoundException if there is no {@code AppUser} with the provided ID.
   */
  @ApiMethod(
    name = "get",
    path = "appUser/{uid}",
    httpMethod = ApiMethod.HttpMethod.GET)
  public AppUser get(@Named("uid") long uid) throws NotFoundException {
    logger.info("Getting AppUser with ID: " + uid);
    AppUser appUser = ofy().load().type(AppUser.class).id(uid).now();
    if (appUser == null) {
      throw new NotFoundException("Could not find AppUser with ID: " + uid);
    }
    return appUser;
  }

  /**
   * Inserts a new {@code AppUser}.
   */
  @ApiMethod(
    name = "insert",
    path = "appUser",
    httpMethod = ApiMethod.HttpMethod.POST)
  public AppUser insert(AppUser appUser) throws NotFoundException {
    // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
    // You should validate that appUser.uid has not been set. If the ID type is not supported by the
    // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
    //
    // If your client provides the ID then you should probably use PUT instead.
      if(checkIfFireBaseUIDExists(appUser.getUid())){
          update(appUser.getUid(),appUser);
      } else {
          ofy().save().entity(appUser).now();
      }

    logger.info("Created AppUser.");

    return ofy().load().entity(appUser).now();
  }

  /**
   * Updates an existing {@code AppUser}.
   *
   * @param uid the ID of the entity to be updated
   * @param appUser the desired state of the entity
   * @return the updated version of the entity
   * @throws NotFoundException if the {@code uid} does not correspond to an existing
   *  {@code AppUser}
   */
  @ApiMethod(
    name = "update",
    path = "appUser/{uid}",
    httpMethod = ApiMethod.HttpMethod.PUT)
  public AppUser update(@Named("uid") String uid, AppUser appUser) throws NotFoundException {
    // TODO: You should validate your ID parameter against your resource's ID here.
    checkExists(uid);
    ofy().save().entity(appUser).now();
    logger.info("Updated AppUser: " + appUser);
    return ofy().load().entity(appUser).now();
  }

  /**
   * Deletes the specified {@code AppUser}.
   *
   * @param uid the ID of the entity to delete
   * @throws NotFoundException if the {@code uid} does not correspond to an existing
   *  {@code AppUser}
   */
  @ApiMethod(
    name = "remove",
    path = "appUser/{uid}",
    httpMethod = ApiMethod.HttpMethod.DELETE)
  public void remove(@Named("uid") String uid) throws NotFoundException {
    checkExists(uid);
    ofy().delete().type(AppUser.class).id(uid).now();
    logger.info("Deleted AppUser with ID: " + uid);
  }

  /**
   * List all entities.
   *
   * @param cursor used for pagination to determine which page to return
   * @param limit the maximum number of entries to return
   * @return a response that encapsulates the result list and the next page token/cursor
   */
  @ApiMethod(
    name = "list",
    path = "appUser",
    httpMethod = ApiMethod.HttpMethod.GET)
  public CollectionResponse<AppUser> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
    limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
    Query<AppUser> query = ofy().load().type(AppUser.class).limit(limit);
    if (cursor != null) {
      query = query.startAt(Cursor.fromWebSafeString(cursor));
    }
    QueryResultIterator<AppUser> queryIterator = query.iterator();
    List<AppUser> appUserList = new ArrayList<AppUser>(limit);
    while (queryIterator.hasNext()) {
      appUserList.add(queryIterator.next());
    }
    return CollectionResponse.<AppUser>builder().setItems(appUserList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
  }

  private void checkExists(String uid) throws NotFoundException {
    try {
      ofy().load().type(AppUser.class).id(uid).safe();
    } catch (com.googlecode.objectify.NotFoundException e) {
      throw new NotFoundException("Could not find AppUser with ID: " + uid);
    }
  }

    private boolean checkIfFireBaseUIDExists(String uid){
        try {
            ofy().load().type(AppUser.class).id(uid);
        } catch (com.googlecode.objectify.NotFoundException e) {
            return false;
        }
        return true;
    }
    }