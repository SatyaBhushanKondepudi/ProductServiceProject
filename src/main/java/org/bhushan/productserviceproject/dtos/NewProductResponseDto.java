package org.bhushan.productserviceproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddANewProductResponseDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image ;
    private String category;
}
