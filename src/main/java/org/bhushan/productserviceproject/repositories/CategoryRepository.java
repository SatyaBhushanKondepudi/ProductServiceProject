package org.bhushan.productserviceproject.repositories;

import org.bhushan.productserviceproject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    Category save(Category category);
    Category findByTitle(String title);
    List<Category> findAll();
    List<Category> findByTitleEndingWith(String ending);

}
