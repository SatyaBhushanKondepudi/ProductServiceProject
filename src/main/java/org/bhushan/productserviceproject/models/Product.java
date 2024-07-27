package org.bhushan.productserviceproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String title;
    private String description;
    private Double price;
    private String imageUrl ;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private float rate ;
    private int count;
}

