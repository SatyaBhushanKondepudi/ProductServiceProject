package org.bhushan.productserviceproject.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bhushan.productserviceproject.models.Category;
import org.bhushan.productserviceproject.models.Product;
import org.bhushan.productserviceproject.models.Rating;

@Getter
@Setter
public class FakeStoreDto {
    private int id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
    private Rating rating;


    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setName(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);

        Category categoryDto = new Category();
        categoryDto.setTitle(category);
        product.setCategory(categoryDto);

        Rating ratingDto = new Rating();
        ratingDto.setRate(rating.getRate());
        ratingDto.setCount(rating.getCount());
        product.setRating(ratingDto);



        return product;
    }

}
