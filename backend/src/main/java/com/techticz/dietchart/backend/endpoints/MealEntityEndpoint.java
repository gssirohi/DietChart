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
        name = "mealEntityApi",
        version = "v1",
        resource = "mealEntity",
        namespace = @ApiNamespace(
                ownerDomain = "backend.dietchart.techticz.com",
                ownerName = "backend.dietchart.techticz.com",
                packagePath = ""
        )
)
public class MealEntityEndpoint {

    private static final Logger logger = Logger.getLogger(MealEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(MealEntity.class);
    }

    /**
     * Returns the {@link MealEntity} with the corresponding ID.
     *
     * @param uid the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code MealEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "mealEntity/{uid}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public MealEntity get(@Named("uid") Long uid) throws NotFoundException {
        logger.info("Getting MealEntity with ID: " + uid);
        MealEntity mealEntity = ofy().load().type(MealEntity.class).id(uid).now();
        if (mealEntity == null) {
            throw new NotFoundException("Could not find MealEntity with ID: " + uid);
        }
        return mealEntity;
    }

    /**
     * Inserts a new {@code MealEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "mealEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public MealEntity insert(MealEntity mealEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that mealEntity.uid has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(mealEntity).now();
        logger.info("Created MealEntity.");

        return ofy().load().entity(mealEntity).now();
    }

    /**
     * Updates an existing {@code MealEntity}.
     *
     * @param uid       the ID of the entity to be updated
     * @param mealEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code uid} does not correspond to an existing
     *                           {@code MealEntity}
     */
    @ApiMethod(
            name = "update",
            path = "mealEntity/{uid}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public MealEntity update(@Named("uid") Long uid, MealEntity mealEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(uid);
        ofy().save().entity(mealEntity).now();
        logger.info("Updated MealEntity: " + mealEntity);
        return ofy().load().entity(mealEntity).now();
    }

    /**
     * Deletes the specified {@code MealEntity}.
     *
     * @param uid the ID of the entity to delete
     * @throws NotFoundException if the {@code uid} does not correspond to an existing
     *                           {@code MealEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "mealEntity/{uid}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("uid") Long uid) throws NotFoundException {
        checkExists(uid);
        ofy().delete().type(MealEntity.class).id(uid).now();
        logger.info("Deleted MealEntity with ID: " + uid);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "mealList",
            path = "mealEntities",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MealEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MealEntity> query = ofy().load().type(MealEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MealEntity> queryIterator = query.iterator();
        List<MealEntity> mealEntityList = new ArrayList<MealEntity>(limit);
        while (queryIterator.hasNext()) {
            mealEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<MealEntity>builder().setItems(mealEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    /* List all entities.
     *
             * @param cursor used for pagination to determine which page to return
            * @param limit  the maximum number of entries to return
            * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "mealListWhereNameContains",
            path = "mealEntityList",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MealEntity> listWhere(@Named("searchKey") String searchKey,@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MealEntity> query = ofy().load().type(MealEntity.class).limit(limit);
        query.filter("name =","%"+searchKey+"%");

        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MealEntity> queryIterator = query.iterator();
        List<MealEntity> mealEntityList = new ArrayList<MealEntity>(limit);
        while (queryIterator.hasNext()) {
            MealEntity e = queryIterator.next();
            if(e.getName().contains(searchKey)) {
                mealEntityList.add(e);
            }
        }
        return CollectionResponse.<MealEntity>builder().setItems(mealEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "mealListForIds",
            path = "mealEntityListForIds",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MealEntity> listForIds(@Named("mealIds") Long[] mealIds,@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        List<MealEntity> mealEntityList = new ArrayList<MealEntity>(limit);
        for(Long l:mealIds) {
            MealEntity mealEntity = ofy().load().type(MealEntity.class).id(l).now();
            mealEntityList.add(mealEntity);
        }
        return CollectionResponse.<MealEntity>builder().setItems(mealEntityList).build();
    }
    private void checkExists(Long uid) throws NotFoundException {
        try {
            ofy().load().type(MealEntity.class).id(uid).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find MealEntity with ID: " + uid);
        }
    }
}