
package com.techticz.app.domain.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class Event extends ProductModel {

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
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("creators")
    @Expose
    private CreatorSummaryListContainer creatorSummaryListContainer;
    @SerializedName("characters")
    @Expose
    private CharacterSummaryListContainer characterSummaryListContainer;
    @SerializedName("stories")
    @Expose
    private StorySummaryListContainer StorySummaryListContainer;
    @SerializedName("comics")
    @Expose
    private ComicSummaryListContainer comicSummaryListContainer;
    @SerializedName("series")
    @Expose
    private SeriesSummaryListContainer seriesSummaryListContainer;
    @SerializedName("next")
    @Expose
    private Next next;
    @SerializedName("previous")
    @Expose
    private Previous previous;

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
     * @return The start
     */
    public String getStart() {
        return start;
    }

    /**
     * @param start The start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return The end
     */
    public String getEnd() {
        return end;
    }

    /**
     * @param end The end
     */
    public void setEnd(String end) {
        this.end = end;
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
     * @return The creatorSummaryListContainer
     */
    public CreatorSummaryListContainer getCreatorSummaryListContainer() {
        return creatorSummaryListContainer;
    }

    /**
     * @param CreatorSummaryListContainer The creatorSummaryListContainer
     */
    public void setCreatorSummaryListContainer(CreatorSummaryListContainer CreatorSummaryListContainer) {
        this.creatorSummaryListContainer = CreatorSummaryListContainer;
    }

    /**
     * @return The characterSummaryListContainer
     */
    public CharacterSummaryListContainer getCharacterSummaryListContainer() {
        return characterSummaryListContainer;
    }

    /**
     * @param characterSummaryListContainer The characterSummaryListContainer
     */
    public void setCharacterSummaryListContainer(CharacterSummaryListContainer characterSummaryListContainer) {
        this.characterSummaryListContainer = characterSummaryListContainer;
    }

    /**
     * @return The StorySummaryListContainer
     */
    public StorySummaryListContainer getStorySummaryListContainer() {
        return StorySummaryListContainer;
    }

    /**
     * @param StorySummaryListContainer The StorySummaryListContainer
     */
    public void setStorySummaryListContainer(StorySummaryListContainer StorySummaryListContainer) {
        this.StorySummaryListContainer = StorySummaryListContainer;
    }

    /**
     * @return The comicSummaryListContainer
     */
    public ComicSummaryListContainer getComicSummaryListContainer() {
        return comicSummaryListContainer;
    }
/*

    @Override
    public EventSummaryListContainer getEventSummaryListContainer() {
        return null;
    }
*/

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
     * @return The next
     */
    public Next getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(Next next) {
        this.next = next;
    }

    /**
     * @return The previous
     */
    public Previous getPrevious() {
        return previous;
    }

    /**
     * @param previous The previous
     */
    public void setPrevious(Previous previous) {
        this.previous = previous;
    }

}
