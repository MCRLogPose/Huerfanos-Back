package com.bakerysystem.ratings.repository;

import com.bakerysystem.ratings.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.product.id = :productId AND r.isLiked = true")
    long countLikesByProductId(Long productId);
}
