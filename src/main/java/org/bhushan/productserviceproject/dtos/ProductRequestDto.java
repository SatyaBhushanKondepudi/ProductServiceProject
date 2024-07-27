package org.bhushan.productserviceproject.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bhushan.productserviceproject.models.Rating;

@Getter
@Setter
public class ProductRequestDto {
    private String title ;
    private Double price ;
    private String description ;
    private String image ;
    private String category ;
    private Rating rating ;
}

/*
 {
                    title: 'test product',
                    price: 13.5,
                    description: 'lorem ipsum set',
                    image: 'https://i.pravatar.cc',
                    category: 'electronic'
                }
 */