
package com.techticz.app.domain.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class Creator extends ProductModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("comics")
    @Expose
    private ComicSummaryListContainer comicSummaryListContainer;
    @SerializedName("series")
    @Expose
    private SeriesSummaryListContainer seriesSummaryListContainer;
    @SerializedName("stories")
    @Expose
    private StorySummaryListContainer storySummaryListContainer;
    @SerializedName("events")
    @Expose
    private EventSummaryListContainer eventSummaryListContainer;
    @SerializedName("urls")
    @Expose
    private List<Url> urls = new ArrayList<Url>();

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

/*
    @Override
    public CharacterSummaryListContainer getCharacterSummaryListContainer() {
        return null;
    }
*/

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName The middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix The suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        return getFullName();
    }

    /**
     * @param thumbnail The thumbnail
     */
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
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
     * @return The comicSummaryListContainer
     */
    public ComicSummaryListContainer getComicSummaryListContainer() {
        return comicSummaryListContainer;
    }

    /**
     * @param ComicSummaryListContainer The comicSummaryListContainer
     */
    public void setComicSummaryListContainer(ComicSummaryListContainer ComicSummaryListContainer) {
        this.comicSummaryListContainer = ComicSummaryListContainer;
    }

    /**
     * @return The seriesSummaryListContainer
     */
    public SeriesSummaryListContainer getSeriesSummaryListContainer() {
        return seriesSummaryListContainer;
    }

    /**
     * @param SeriesSummaryListContainer The seriesSummaryListContainer
     */
    public void setSeriesSummaryListContainer(SeriesSummaryListContainer SeriesSummaryListContainer) {
        this.seriesSummaryListContainer = SeriesSummaryListContainer;
    }

    /**
     * @return The storySummaryListContainer
     */
    public StorySummaryListContainer getStorySummaryListContainer() {
        return storySummaryListContainer;
    }
/*

    @Override
    public CreatorSummaryListContainer getCreatorSummaryListContainer() {
        return null;
    }
*/

    @Override
    public String getDescription() {
        return null;
    }

    /**
     * @param StorySummaryListContainer The storySummaryListContainer
     */
    public void setStorySummaryListContainer(StorySummaryListContainer StorySummaryListContainer) {
        this.storySummaryListContainer = StorySummaryListContainer;
    }

    /**
     * @return The eventSummaryListContainer
     */
    public EventSummaryListContainer getEventSummaryListContainer() {
        return eventSummaryListContainer;
    }

    /**
     * @param EventSummaryListContainer The eventSummaryListContainer
     */
    public void setEventSummaryListContainer(EventSummaryListContainer EventSummaryListContainer) {
        this.eventSummaryListContainer = EventSummaryListContainer;
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

}
