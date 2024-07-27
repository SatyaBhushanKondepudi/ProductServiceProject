package org.bhushan.productserviceproject.repositories;

import org.bhushan.productserviceproject.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating save(Rating rating);
}
