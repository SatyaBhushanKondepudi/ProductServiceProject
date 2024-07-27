package org.bhushan.productserviceproject.services;
import org.bhushan.productserviceproject.dtos.NewProductResponseDto;
import org.bhushan.productserviceproject.dtos.ProductRequestDto;
import org.bhushan.productserviceproject.exceptions.CategoryNotFoundException;
import org.bhushan.productserviceproject.exceptions.InvalidLimitException;
import org.bhushan.productserviceproject.exceptions.ProductNotFoundException;
import org.bhushan.productserviceproject.models.Category;
import org.bhushan.productserviceproject.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(Long productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();
    public List<Product> getLimitedProducts(int limit) throws InvalidLimitException;
    public List<String> getAllCategories();
    public List<Product> getProductsByCategory(String categoryName)
            throws CategoryNotFoundException;
    public Product addANewProduct(String title, Double price,
                                                String description, String image, String category,
                                                float rate , int count);

    public Product updateAProduct(Long id , String title, Double price,
                           String description, String image, String category)
            throws ProductNotFoundException;

    public Product replaceAProduct(Long productID,
                            String title, Double price,
                            String description, String image, String category)
            throws ProductNotFoundException;

    Product deleteProduct(Long productID) throws ProductNotFoundException;

    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam);
}
