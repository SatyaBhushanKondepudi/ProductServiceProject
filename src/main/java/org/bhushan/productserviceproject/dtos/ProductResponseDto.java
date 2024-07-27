package org.bhushan.productserviceproject.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bhushan.productserviceproject.models.Category;
import org.bhushan.productserviceproject.models.Rating;

@Getter
@Setter

public class ProductResponseDto {
    private int id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private Category category;
    private Rating rating;
}
