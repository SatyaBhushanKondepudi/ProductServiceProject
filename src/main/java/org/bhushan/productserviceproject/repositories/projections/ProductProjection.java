package org.bhushan.productserviceproject.repositories.projections;

import org.bhushan.productserviceproject.models.Category;

import java.math.BigDecimal;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
    Category getCategory();
    String getImageUrl();
}
