package org.bhushan.productserviceproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductResponseDto {
    private int id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private CategoryDto category;
    private RatingDto rating;
}
