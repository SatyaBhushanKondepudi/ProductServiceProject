package org.bhushan.productserviceproject.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bhushan.productserviceproject.models.Category;
import org.bhushan.productserviceproject.models.Product;
import org.bhushan.productserviceproject.models.Rating;

@Getter
@Setter
public class FakeStoreDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;


    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);
        Category categoryObj = new Category();
        categoryObj.setTitle(category);
        product.setCategory(categoryObj);
        product.setRate(rating.getRate());
        product.setCount(rating.getCount());
        return product;
    }

    public NewProductResponseDto toNewProductResponseDto() {
        NewProductResponseDto newProductResponseDto = new NewProductResponseDto();
        newProductResponseDto.setId(id);
        newProductResponseDto.setTitle(title);
        newProductResponseDto.setDescription(description);
        newProductResponseDto.setPrice(price);
        newProductResponseDto.setImage(image);
        newProductResponseDto.setCategory(category);
        return newProductResponseDto;
    }

}
