package org.bhushan.productserviceproject.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl ;
    private Category category ;
    private Rating rating;


}
