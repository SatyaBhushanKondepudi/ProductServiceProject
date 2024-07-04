package org.bhushan.productserviceproject.services;

import org.bhushan.productserviceproject.dtos.FakeStoreDto;
import org.bhushan.productserviceproject.dtos.ProductResponseDto;
import org.bhushan.productserviceproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate ;
    }

    @Override
    public Product getSingleProduct(int productId) {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "http://fakestoreapi.com/products/" + productId, FakeStoreDto.class
        );
        return fakeStoreDto.toProduct();
    }


}
