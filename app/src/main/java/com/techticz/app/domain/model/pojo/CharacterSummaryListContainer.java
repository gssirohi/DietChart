
package com.techticz.app.domain.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.SummaryListContainer;

import java.util.ArrayList;
import java.util.List;


public class CharacterSummaryListContainer extends SummaryListContainer {

    @SerializedName("available")
    @Expose
    private Integer available;
    @SerializedName("collectionURI")
    @Expose
    private String collectionURI;
    @SerializedName("items")
    @Expose
    private List<CharacterSummary> items = new ArrayList<CharacterSummary>();
    @SerializedName("returned")
    @Expose
    private Integer returned;

    /**
     * @return The available
     */
    public Integer getAvailable() {
        return available;
    }

    /**
     * @param available The available
     */
    public void setAvailable(Integer available) {
        this.available = available;
    }

    /**
     * @return The collectionURI
     */
    public String getCollectionURI() {
        return collectionURI;
    }

    /**
     * @param collectionURI The collectionURI
     */
    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    /**
     * @return The items
     */
    public List<CharacterSummary> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<CharacterSummary> items) {
        this.items = items;
    }

    /**
     * @return The returned
     */
    public Integer getReturned() {
        return returned;
    }

    /**
     * @param returned The returned
     */
    public void setReturned(Integer returned) {
        this.returned = returned;
    }

}
