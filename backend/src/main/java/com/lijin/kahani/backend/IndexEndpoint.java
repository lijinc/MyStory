package com.lijin.kahani.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "indexApi",
        version = "v1",
        resource = "index",
        namespace = @ApiNamespace(
                ownerDomain = "backend.kahani.lijin.com",
                ownerName = "backend.kahani.lijin.com",
                packagePath = ""
        )
)
public class IndexEndpoint {

    private static final Logger logger = Logger.getLogger(IndexEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Index.class);
    }

    /**
     * Returns the {@link Index} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Index} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "index/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Index get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Index with ID: " + id);
        Index index = ofy().load().type(Index.class).id(id).now();
        if (index == null) {
            throw new NotFoundException("Could not find Index with ID: " + id);
        }
        return index;
    }

    /**
     * Inserts a new {@code Index}.
     */
    @ApiMethod(
            name = "insert",
            path = "index",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Index insert(Index index) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that index.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(index).now();
        logger.info("Created Index with ID: " + index.getId());

        return ofy().load().entity(index).now();
    }

    /**
     * Updates an existing {@code Index}.
     *
     * @param id    the ID of the entity to be updated
     * @param index the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Index}
     */
    @ApiMethod(
            name = "update",
            path = "index/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Index update(@Named("id") Long id, Index index) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(index).now();
        logger.info("Updated Index: " + index);
        return ofy().load().entity(index).now();
    }

    /**
     * Deletes the specified {@code Index}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Index}
     */
    @ApiMethod(
            name = "remove",
            path = "index/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Index.class).id(id).now();
        logger.info("Deleted Index with ID: " + id);
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
            path = "index",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Index> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Index> query = ofy().load().type(Index.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Index> queryIterator = query.iterator();
        List<Index> indexList = new ArrayList<Index>(limit);
        while (queryIterator.hasNext()) {
            indexList.add(queryIterator.next());
        }
        return CollectionResponse.<Index>builder().setItems(indexList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Index.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Index with ID: " + id);
        }
    }

    @ApiMethod(
            name = "listChapters",
            path = "index/chapters/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Index> listChapters(@Named("id") Long id,@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Index> query = ofy().load().type(Index.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Index> queryIterator = query.iterator();
        List<Index> indexList = new ArrayList<Index>(limit);
        while (queryIterator.hasNext()) {
            Index index =queryIterator.next();
            if(index.getBookId().equals(id))
                indexList.add(index);
        }
        Collections.sort(indexList, new Comparator<Index>() {
            @Override
            public int compare(Index o1, Index o2) {
                return o1.getChapterNo() - o2.getChapterNo();
            }
        });
        return CollectionResponse.<Index>builder().setItems(indexList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

}