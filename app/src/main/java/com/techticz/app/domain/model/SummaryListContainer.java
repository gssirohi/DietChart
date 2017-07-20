package com.techticz.app.domain.model;

import java.util.List;

/**
 * Created by gssirohi on 11/9/16.
 */
public abstract class SummaryListContainer extends Model {
    public abstract Integer getAvailable();

    public abstract Integer getReturned();

    public abstract <T extends SummaryModel> List<T> getItems();
}
