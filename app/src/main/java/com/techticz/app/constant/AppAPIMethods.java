package com.techticz.app.constant;

import com.techticz.app.domain.model.pojo.CharacterResponseContainer;
import com.techticz.app.domain.model.pojo.ComicsResponseContainer;
import com.techticz.app.domain.model.pojo.CreatorResponseContainer;
import com.techticz.app.domain.model.pojo.EventResponseContainer;
import com.techticz.app.domain.model.pojo.SeriesResponseContainer;
import com.techticz.app.domain.model.pojo.StoryResponseContainer;
import com.techticz.app.network.ResponseContainer;

/**
 * Created by gssirohi on 18/8/16.
 */
public class AppAPIMethods {
    public static final String CORPORATE_CREATE_TRIP_METHOD = "corporateGenerateTripId.htm";
    public static final String CORPORATE_GET_TRIP_DETAILS_METHOD = "getCorporateTripDetails.htm";
    public static final String CORPORATE_GET_ALL_TRIPS_METHOD = "getCorporateTripListData.htm";
    public static final String FETCH_CHARACTERS_LIST = "/characters";
    public static final String FETCH_COMICS_LIST = "/comics";
    public static final String FETCH_SERIES_LIST = "/series";
    public static final String FETCH_STORIES_LIST = "/stories";
    public static final String FETCH_EVENTS_LIST = "/events";
    public static final String FETCH_CREATORS_LIST = "/creators";

    public static String getProductRequestMethod(Products type) {
        switch (type) {
            case COMIC:
                return FETCH_COMICS_LIST;
            case CHARACTER:
                return FETCH_CHARACTERS_LIST;
            case EVENT:
                return FETCH_EVENTS_LIST;
            case SERIES:
                return FETCH_SERIES_LIST;
            case STORY:
                return FETCH_STORIES_LIST;
            case CREATOR:
                return FETCH_CREATORS_LIST;
        }
        return null;
    }

    public static Class<? extends ResponseContainer> getProductClass(Products type) {
        switch (type) {
            case COMIC:
                return ComicsResponseContainer.class;
            case CHARACTER:
                return CharacterResponseContainer.class;
            case EVENT:
                return EventResponseContainer.class;
            case SERIES:
                return SeriesResponseContainer.class;
            case STORY:
                return StoryResponseContainer.class;
            case CREATOR:
                return CreatorResponseContainer.class;
        }
        return null;
    }
}
