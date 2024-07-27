package org.bhushan.productserviceproject;

import jakarta.transaction.Transactional;
import org.bhushan.productserviceproject.exceptions.ProductNotFoundException;
import org.bhushan.productserviceproject.models.Category;
import org.bhushan.productserviceproject.models.Product;
import org.bhushan.productserviceproject.models.Rating;
import org.bhushan.productserviceproject.repositories.CategoryRepository;
import org.bhushan.productserviceproject.repositories.ProductRepository;
import org.bhushan.productserviceproject.repositories.projections.ProductProjection;
import org.bhushan.productserviceproject.repositories.projections.ProductWithIdAndTitle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;

@SpringBootTest
class ProductServiceProjectApplicationTests {

    // 3 Ways to do the dependency injection
//    1. Constructor injection
//    2. Setter method
//    3. @Autowired notation
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }



    @Test
    void testJPADeclaredJoin(){
        List<Product> products = productRepository.findByCategory_Title("Plus Model Iphones");
        for(Product product : products){
            System.out.println(product.getTitle());
        }
    }

    @Test
    void testHQL(){
        List<Product>products = productRepository.getProductsWithCategoryName("PRO Model Iphones");
        for(Product product : products){
            System.out.println(product.getTitle());
        }
    }

    @Test
    void testProjections(){
        List<ProductWithIdAndTitle> ProjectionsMethod1 = productRepository.ProjectionsMethod1(
                "PRO Model Iphones"
        );
        for(ProductWithIdAndTitle product : ProjectionsMethod1){
            System.out.println(product.getTitle());
            System.out.println(product.getId());
        }

        List<ProductProjection> ProjectionsMethod2 = productRepository.ProjectionsMethod2(
                "PRO Model Iphones"
        );
        for(ProductProjection product : ProjectionsMethod2){
            System.out.println(product.getTitle());
            System.out.println(product.getId());
        }
    }


    @Test
    void testNativeSql() throws ProductNotFoundException {
        Product product = productRepository.NativeSqlQuery1(3L);
        if(product == null) throw new ProductNotFoundException("Product not found");
        System.out.println(product.getTitle());
    }

    @Test
    @Transactional
    void testFetchMode(){
        List<Category> categories =
                categoryRepository.findByTitleEndingWith("Iphones");
        for(Category category : categories){
            System.out.println(category.getTitle());
            List<Product> products = category.getProducts();
            for(Product product : products){
                System.out.println(product.getTitle());
            }
        }

        // FetchType.LAZY >> @Transactional >> N+1 Problem
        // FetchType.Eager >> All the N+1 DB calls are done upfront
        // FetchMode.SUBSELECT >> Only 2 DB Call by using "in" Operator
    }

    @Test
    void addManyProducts() {
        double basePrice = 2000;
        String productName = "toy";
        String productDescription = "toy number";
        String imageUrl = "www.imgur.com/";
        String categoryName = "toys";

        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setTitle(productName + "-" + i);
            product.setDescription( productDescription + " # " + i);
            product.setPrice(basePrice + i);
            Category category = new Category();
            category.setTitle(categoryName);
            product.setCategory(category);
            product.setImageUrl(imageUrl + i);
//            Rating rating = new Rating();
            product.setRate((float) i );
            product.setCount(i);
            productRepository.save(product);
        }
    }
}
