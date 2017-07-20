
package com.techticz.app.domain.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class Series extends ProductModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("urls")
    @Expose
    private List<Url> urls = new ArrayList<Url>();
    @SerializedName("startYear")
    @Expose
    private Integer startYear;
    @SerializedName("endYear")
    @Expose
    private Integer endYear;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("creators")
    @Expose
    private CreatorSummaryListContainer creators;
    @SerializedName("characters")
    @Expose
    private CharacterSummaryListContainer characters;
    @SerializedName("stories")
    @Expose
    private StorySummaryListContainer stories;
    @SerializedName("comics")
    @Expose
    private ComicSummaryListContainer comics;
    @SerializedName("events")
    @Expose
    private EventSummaryListContainer events;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The resourceURI
     */
    public String getResourceURI() {
        return resourceURI;
    }

    /**
     * @param resourceURI The resourceURI
     */
    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    /**
     * @return The urls
     */
    public List<Url> getUrls() {
        return urls;
    }

    /**
     * @param urls The urls
     */
    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    /**
     * @return The startYear
     */
    public Integer getStartYear() {
        return startYear;
    }

    /**
     * @param startYear The startYear
     */
    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    /**
     * @return The endYear
     */
    public Integer getEndYear() {
        return endYear;
    }

    /**
     * @param endYear The endYear
     */
    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    /**
     * @return The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The modified
     */
    public String getModified() {
        return modified;
    }

    /**
     * @param modified The modified
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     * @return The thumbnail
     */
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    @Override
    public String getName() {
        return getTitle();
    }

    /**
     * @param thumbnail The thumbnail
     */
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return The creators
     */
    public CreatorSummaryListContainer getCreatorSummaryListContainer() {
        return creators;
    }

    /**
     * @param creators The creators
     */
    public void setCreatorSummaryListContainer(CreatorSummaryListContainer creators) {
        this.creators = creators;
    }

    /**
     * @return The characters
     */
    public CharacterSummaryListContainer getCharacterSummaryListContainer() {
        return characters;
    }

    /**
     * @param characters The characters
     */
    public void setCharacterSummaryListContainer(CharacterSummaryListContainer characters) {
        this.characters = characters;
    }

    /**
     * @return The stories
     */
    public StorySummaryListContainer getStories() {
        return stories;
    }

    /**
     * @param stories The stories
     */
    public void setStorySummaryListContainer(StorySummaryListContainer stories) {
        this.stories = stories;
    }

    /**
     * @return The comics
     */
    public ComicSummaryListContainer getComicSummaryListContainer() {
        return comics;
    }

    /**
     * @param comics The comics
     */
    public void setComicSummaryListContainer(ComicSummaryListContainer comics) {
        this.comics = comics;
    }

    /**
     * @return The events
     */
    public EventSummaryListContainer getEventSummaryListContainer() {
        return events;
    }
/*

    @Override
    public SeriesSummaryListContainer getSeriesSummaryListContainer() {
        return null;
    }

    @Override
    public StorySummaryListContainer getStorySummaryListContainer() {
        return null;
    }
*/

    /**
     * @param events The events
     */
    public void setEventSummaryListContainer(EventSummaryListContainer events) {
        this.events = events;
    }

    /**
     * @return The next
     */
    public Object getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(Object next) {
        this.next = next;
    }

    /**
     * @return The previous
     */
    public Object getPrevious() {
        return previous;
    }

    /**
     * @param previous The previous
     */
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

}
