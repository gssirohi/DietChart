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
import com.techticz.dietchart.backend.entities.FoodEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "foodEntityApi",
        version = "v1",
        resource = "foodEntity",
        namespace = @ApiNamespace(
                ownerDomain = "backend.dietchart.techticz.com",
                ownerName = "backend.dietchart.techticz.com",
                packagePath = ""
        )
)
public class FoodEntityEndpoint {

    private static final Logger logger = Logger.getLogger(FoodEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(FoodEntity.class);
    }


    /**
     * Returns the {@link FoodEntity} with the corresponding ID.
     *
     * @param uid the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code FoodEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "foodEntity/{uid}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public FoodEntity get(@Named("uid") long uid) throws NotFoundException {
        logger.info("Getting FoodEntity with ID: " + uid);
        FoodEntity foodEntity = ofy().load().type(FoodEntity.class).id(uid).now();
        if (foodEntity == null) {
            throw new NotFoundException("Could not find FoodEntity with ID: " + uid);
        }
        return foodEntity;
    }

    /**
     * Inserts a new {@code FoodEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "foodEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FoodEntity insert(FoodEntity foodEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that foodEntity.uid has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(foodEntity).now();
        logger.info("Created FoodEntity with ID: " + foodEntity.getUid());

        return ofy().load().entity(foodEntity).now();
    }

    /**
     * Updates an existing {@code FoodEntity}.
     *
     * @param uid        the ID of the entity to be updated
     * @param foodEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code uid} does not correspond to an existing
     *                           {@code FoodEntity}
     */
    @ApiMethod(
            name = "update",
            path = "foodEntity/{uid}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public FoodEntity update(@Named("uid") long uid, FoodEntity foodEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(uid);
        ofy().save().entity(foodEntity).now();
        logger.info("Updated FoodEntity: " + foodEntity);
        return ofy().load().entity(foodEntity).now();
    }

    /**
     * Deletes the specified {@code FoodEntity}.
     *
     * @param uid the ID of the entity to delete
     * @throws NotFoundException if the {@code uid} does not correspond to an existing
     *                           {@code FoodEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "foodEntity/{uid}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("uid") long uid) throws NotFoundException {
        checkExists(uid);
        ofy().delete().type(FoodEntity.class).id(uid).now();
        logger.info("Deleted FoodEntity with ID: " + uid);
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
            path = "foodEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<FoodEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<FoodEntity> query = ofy().load().type(FoodEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<FoodEntity> queryIterator = query.iterator();
        List<FoodEntity> foodEntityList = new ArrayList<FoodEntity>(limit);
        while (queryIterator.hasNext()) {
            foodEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<FoodEntity>builder().setItems(foodEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "foodListWhereNameContains",
            path = "foodEntityList",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<FoodEntity> listWhere(@Named("searchKey") String searchKey,@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<FoodEntity> query = ofy().load().type(FoodEntity.class).limit(limit);
        query.filter("name =","%"+searchKey+"%");

        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<FoodEntity> queryIterator = query.iterator();
        List<FoodEntity> foodEntityList = new ArrayList<FoodEntity>(limit);
        while (queryIterator.hasNext()) {
            FoodEntity e = queryIterator.next();
            if(e.getName().contains(searchKey)) {
                foodEntityList.add(e);
            }
        }
        return CollectionResponse.<FoodEntity>builder().setItems(foodEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "foodListForIds",
            path = "foodEntityListForIds",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<FoodEntity> listForIds(@Named("foodIds") Long[] foodIds,@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        List<FoodEntity> foodEntityList = new ArrayList<FoodEntity>(limit);
        for(Long l:foodIds) {
            FoodEntity foodEntity = ofy().load().type(FoodEntity.class).id(l).now();
            foodEntityList.add(foodEntity);
        }
        return CollectionResponse.<FoodEntity>builder().setItems(foodEntityList).build();
    }

    private void checkExists(long uid) throws NotFoundException {
        try {
            ofy().load().type(FoodEntity.class).id(uid).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find FoodEntity with ID: " + uid);
        }
    }
}