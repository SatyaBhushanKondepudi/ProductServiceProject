package org.bhushan.productserviceproject.services;

import org.bhushan.productserviceproject.exceptions.CategoryNotFoundException;
import org.bhushan.productserviceproject.exceptions.InvalidLimitException;
import org.bhushan.productserviceproject.exceptions.ProductNotFoundException;
import org.bhushan.productserviceproject.models.Category;
import org.bhushan.productserviceproject.models.Product;
import org.bhushan.productserviceproject.models.Rating;
import org.bhushan.productserviceproject.repositories.CategoryRepository;
import org.bhushan.productserviceproject.repositories.ProductRepository;
import org.bhushan.productserviceproject.repositories.RatingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RatingRepository ratingRepository;

    public SelfProductService(final ProductRepository productRepository,
                              final CategoryRepository categoryRepository ,
                              final RatingRepository ratingRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);
        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getLimitedProducts(int limit) throws InvalidLimitException {
        return List.of();
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        List<String> categoriesNames = new ArrayList<>();
        for (Category category : categories) {
            categoriesNames.add(category.getTitle());
        }
        return categoriesNames;
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) throws CategoryNotFoundException {
        return productRepository.getProductsWithCategoryName(categoryName);
    }

    @Override
    public Product addANewProduct(String title, Double price, String description,
                                  String image, String category,
                                  float rate , int count) {
        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        newProduct.setImageUrl(image);

        Category categoryObj = categoryRepository.findByTitle(category);
        if(categoryObj == null) {
            categoryObj = new Category();
            categoryObj.setTitle(category);
            categoryObj = categoryRepository.save(categoryObj);
        }
        newProduct.setCategory(categoryObj);
        newProduct.setRate(rate);
        newProduct.setCount(count);

        return productRepository.save(newProduct);
    }

    @Override
    public Product updateAProduct(Long id, String title, Double price, String description, String image, String category) throws ProductNotFoundException {
        Product productInDB = productRepository.findByIdIs(id);
        if (productInDB == null) {
            throw new ProductNotFoundException(
                    "Product with id " + id + " not found"
            );
        }
        if(title != null){
            productInDB.setTitle(title);
        }
        if(price != null){
            productInDB.setPrice(price);
        }
        if(description != null){
            productInDB.setDescription(description);
        }
        if(image != null){
            productInDB.setImageUrl(image);
        }
        if(category != null){
            Category categoryfromDb = categoryRepository.findByTitle(category);
            if(categoryfromDb == null) {
                Category newCategory = new Category();
                newCategory.setTitle(category);
                newCategory = categoryRepository.save(newCategory);
                categoryfromDb = newCategory;
            }
            productInDB.setCategory(categoryfromDb);
        }
        return productRepository.save(productInDB);
    }

    @Override
    public Product replaceAProduct(Long productID, String title, Double price, String description, String image, String category) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product deleteProduct(Long productID) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productID);
        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productID + " not found"
            );
        }
        productRepository.delete(product);
        return product;
    }

    @Override
    public Page<Product> getAllProducts( int pageNumber, int pageSize,
                                         String sortParam) {
        return productRepository.findAll(PageRequest.of(
                pageNumber, pageSize, Sort.by(sortParam).descending()));

        // if direction == ascending
        // else  descending
//        return productRepository.findAll(PageRequest.of
//                (pageNumber, pageSize,
//                        Sort.by(sortParam).descending()));
    }
}
