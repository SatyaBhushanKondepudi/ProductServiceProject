package org.bhushan.productserviceproject.services;

import org.bhushan.productserviceproject.dtos.FakeStoreDto;
import org.bhushan.productserviceproject.dtos.ProductResponseDto;
import org.bhushan.productserviceproject.models.Product;

public interface ProductService {

    public Product getSingleProduct(int productId);
}
