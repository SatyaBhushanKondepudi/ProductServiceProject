package org.bhushan.productserviceproject.services;

import org.bhushan.productserviceproject.dtos.FakeStoreDto;
import org.bhushan.productserviceproject.dtos.NewProductResponseDto;
import org.bhushan.productserviceproject.exceptions.CategoryNotFoundException;
import org.bhushan.productserviceproject.exceptions.InvalidLimitException;
import org.bhushan.productserviceproject.exceptions.ProductNotFoundException;
import org.bhushan.productserviceproject.models.Product;
import org.bhushan.productserviceproject.models.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "http://fakestoreapi.com/products/" + productId, FakeStoreDto.class
        );
        if (fakeStoreDto == null) {
            throw new ProductNotFoundException("Product with  " + productId + " not found");
        }

        return fakeStoreDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreDto[] fakeStoreDtos = restTemplate.getForObject(
                "http://fakestoreapi.com/products", FakeStoreDto[].class
        );

        List<Product> products = new ArrayList<Product>();
        for (FakeStoreDto fakeStoreDto : fakeStoreDtos) {
            products.add(fakeStoreDto.toProduct());
        }
        return products;
    }

    @Override
    public List<Product> getLimitedProducts(int limit)
            throws InvalidLimitException {
        FakeStoreDto[] fakeStoreDtos = restTemplate.getForObject(
                "http://fakestoreapi.com/products?limit=" + limit, FakeStoreDto[].class);
        List<Product> products = new ArrayList<>();
        if (fakeStoreDtos.length == 0) {
            throw new InvalidLimitException("Limit entered is incorrect");
        }
        for (FakeStoreDto fakeStoreDto : fakeStoreDtos) {
            products.add(fakeStoreDto.toProduct());
        }
        return products;
    }

    @Override
    public List<String> getAllCategories() {
        List<String> fakeStoreCategoriesDtos = restTemplate.getForObject(
                "http://fakestoreapi.com/products/categories", List.class
        );
        return fakeStoreCategoriesDtos;
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) throws CategoryNotFoundException {
        List<Product> result = new ArrayList<>();
        FakeStoreDto[] fakeStoreDtos = restTemplate.getForObject(
                "http://fakestoreapi.com/products/category/" + categoryName, FakeStoreDto[].class
        );
        if (fakeStoreDtos.length == 0) {
            throw new CategoryNotFoundException("Category with name : " + categoryName + " not found");
        }
        List<Product> products = new ArrayList<>();
        for (FakeStoreDto fakeStoreDto : fakeStoreDtos) {
            products.add(fakeStoreDto.toProduct());
        }
        return products;
    }

    @Override
    public Product addANewProduct(String title, Double price, String description, String image, String category,
                                                float rate , int count) {
        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setPrice(price);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setCategory(category);
        Rating ratingDto = new Rating();
        ratingDto.setRate(rate);
        ratingDto.setCount(count);
        requestDto.setRating(ratingDto);
        FakeStoreDto response = restTemplate.postForObject(
                "http://fakestoreapi.com/products", requestDto, FakeStoreDto.class
        );
        return requestDto.toProduct();
    }

    @Override
    public Product updateAProduct(Long id, String title,
                                  Double price, String description,
                                  String image, String category)
            throws ProductNotFoundException {
        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        // Known Issue: HTTP PATCH is not supported by RestTemplate
        // So below code will NOT work
        // Will throw an exception:
        // Invalid HTTP method: PATCH\n\tat org.springframework.web.client.
        // create request entity to send in patch request body to fakestore
//         HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(requestDto);
//
//        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.exchange(
//                "https://fakestoreapi.com/products/" + productId,
//                HttpMethod.PATCH,
//                requestEntity,
//                FakeStoreDto.class
//        );
//
//        FakeStoreDto response = responseEntity.getBody();
//        if (response == null) {
//            throw new ProductNotFoundException(
//                    "Product with id " + productId + " not found");
//        }

        FakeStoreDto response = requestDto;
        response.setId(id);
        return response.toProduct();
    }

    @Override
    public Product replaceAProduct(Long productID,
                                   String title,
                                   Double price,
                                   String description,
                                   String image,
                                   String category) throws ProductNotFoundException {
        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setCategory(category);
        requestDto.setPrice(price);
        // create request entity to send in put request body to fakestore
        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<FakeStoreDto>(requestDto);
        FakeStoreDto response = restTemplate.exchange(
                "http://fakestoreapi.com/products/" + productID,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreDto.class
        ).getBody();

        if (response == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productID + " not found");
        }
        return response.toProduct();
    }

    @Override
    public Product deleteProduct(Long productID) throws ProductNotFoundException {
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.exchange(
                "http://fakestoreapi.com/products/" + productID,
                HttpMethod.DELETE,
                null,
                FakeStoreDto.class
        );
//        FakeStoreDto fakeStoreDto = restTemplate.exchange(
//                "http://fakestoreapi.com/products/" + productId,
//                HttpMethod.DELETE,
//                null,
//                FakeStoreDto.class
//        ).getBody();

        FakeStoreDto fakeStoreDto = responseEntity.getBody();
        if (fakeStoreDto == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productID + " not found"
                            + " try to delete a product with id less than 21");
        }
        return fakeStoreDto.toProduct();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam) {
        return null;
    }
}
