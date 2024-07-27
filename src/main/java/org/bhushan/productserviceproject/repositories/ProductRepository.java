package org.bhushan.productserviceproject.repositories;

import org.bhushan.productserviceproject.models.Category;
import org.bhushan.productserviceproject.models.Product;
import org.bhushan.productserviceproject.repositories.projections.ProductProjection;
import org.bhushan.productserviceproject.repositories.projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);
    Product findByIdIs(Long id);
    List<Product> findAll();
    List<Product> findByCategory_Title(String title);

    @Query("select p from Product p where p.category.title = :categoryName")
    List<Product> getProductsWithCategoryName(String categoryName);

    @Query("select p.title as title from Product p where p.category.title = :categoryName")
    List<String> someTitleMethod(@Param("categoryName") String categoryName);


    @Query("select p.id as id, p.title as title from Product p where p.category.title = :categoryName")
    List<ProductWithIdAndTitle> ProjectionsMethod1(String categoryName);


    @Query("select p.id as id, p.title as title from Product p where p.category.title = :categoryName")
    List<ProductProjection> ProjectionsMethod2(String categoryName);

    // Native SQL query

    @Query(value = "select * from product p where p.id = :id", nativeQuery = true)
    Product NativeSqlQuery1(Long id);

    @Query(value = "select id, title from product where id = :id", nativeQuery = true)
    ProductProjection NativeSqlQurry2(Long id);

//    List<Product> getProducts();
}
