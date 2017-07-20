package com.techticz.app.domain.model;

import com.techticz.app.domain.model.pojo.CharacterSummaryListContainer;
import com.techticz.app.domain.model.pojo.ComicSummaryListContainer;
import com.techticz.app.domain.model.pojo.CreatorSummaryListContainer;
import com.techticz.app.domain.model.pojo.EventSummaryListContainer;
import com.techticz.app.domain.model.pojo.SeriesSummaryListContainer;
import com.techticz.app.domain.model.pojo.StorySummaryListContainer;
import com.techticz.app.domain.model.pojo.Thumbnail;

/**
 * Created by gssirohi on 25/8/16.
 */
public abstract class ProductModel extends Model {

    public abstract Thumbnail getThumbnail();

    public abstract String getName();

    public abstract Integer getId();

    public abstract String getDescription();
}