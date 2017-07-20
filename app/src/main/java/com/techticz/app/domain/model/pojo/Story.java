
package com.techticz.app.domain.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.ProductModel;


public class Story extends ProductModel {

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
    private CreatorSummaryListContainer CreatorSummaryListContainer;
    @SerializedName("characters")
    @Expose
    private CharacterSummaryListContainer characterSummaryListContainer;
    @SerializedName("series")
    @Expose
    private SeriesSummaryListContainer seriesSummaryListContainer;
    @SerializedName("comics")
    @Expose
    private ComicSummaryListContainer comicsSummaryListContainer;
    @SerializedName("events")
    @Expose
    private EventSummaryListContainer eventSummaryListContainer;
    @SerializedName("originalIssue")
    @Expose
    private OriginalIssue originalIssue;

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
        return null;
    }

    /**
     * @param thumbnail The thumbnail
     */
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return The CreatorSummaryListContainer
     */
    public CreatorSummaryListContainer getCreatorSummaryListContainer() {
        return CreatorSummaryListContainer;
    }

    /**
     * @param CreatorSummaryListContainer The CreatorSummaryListContainer
     */
    public void setCreatorSummaryListContainer(CreatorSummaryListContainer CreatorSummaryListContainer) {
        this.CreatorSummaryListContainer = CreatorSummaryListContainer;
    }

    /**
     * @return The characterSummaryListContainer
     */
    public CharacterSummaryListContainer getCharacterSummaryListContainer() {
        return characterSummaryListContainer;
    }

    /**
     * @param CharacterSummaryListContainer The characterSummaryListContainer
     */
    public void setCharacterSummaryListContainer(CharacterSummaryListContainer CharacterSummaryListContainer) {
        this.characterSummaryListContainer = CharacterSummaryListContainer;
    }

    /**
     * @return The seriesSummaryListContainer
     */
    public SeriesSummaryListContainer getSeriesSummaryListContainer() {
        return seriesSummaryListContainer;
    }

/*
    @Override
    public StorySummaryListContainer getStorySummaryListContainer() {
        return null;
    }
*/

    /**
     * @param seriesSummaryListContainer The seriesSummaryListContainer
     */
    public void setSeriesSummaryListContainer(SeriesSummaryListContainer seriesSummaryListContainer) {
        this.seriesSummaryListContainer = seriesSummaryListContainer;
    }

    /**
     * @return The comicsSummaryListContainer
     */
    public ComicSummaryListContainer getComicSummaryListContainer() {
        return comicsSummaryListContainer;
    }

    /**
     * @param comicsSummaryListContainer The comicsSummaryListContainer
     */
    public void setComicSummaryListContainer(ComicSummaryListContainer comicsSummaryListContainer) {
        this.comicsSummaryListContainer = comicsSummaryListContainer;
    }

    /**
     * @return The eventSummaryListContainer
     */
    public EventSummaryListContainer getEventSummaryListContainer() {
        return eventSummaryListContainer;
    }

    /**
     * @param eventSummaryListContainer The eventSummaryListContainer
     */
    public void setEventSummaryListContainer(EventSummaryListContainer eventSummaryListContainer) {
        this.eventSummaryListContainer = eventSummaryListContainer;
    }

    /**
     * @return The originalIssue
     */
    public OriginalIssue getOriginalIssue() {
        return originalIssue;
    }

    /**
     * @param originalIssue The originalIssue
     */
    public void setOriginalIssue(OriginalIssue originalIssue) {
        this.originalIssue = originalIssue;
    }

}
