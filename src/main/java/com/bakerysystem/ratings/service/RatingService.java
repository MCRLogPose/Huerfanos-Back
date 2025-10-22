package com.bakerysystem.ratings.service;

import com.bakerysystem.auth.model.User;
import com.bakerysystem.products.model.Product;
import com.bakerysystem.ratings.model.Rating;
import com.bakerysystem.ratings.repository.RatingRepository;
import com.bakerysystem.products.repository.ProductRepository;
import com.bakerysystem.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    // 👍 Dar o actualizar like
    public void toggleLike(Long userId, Long productId, boolean isLiked) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<Rating> existing = ratingRepo.findByUserIdAndProductId(userId, productId);

        if (existing.isPresent()) {
            Rating rating = existing.get();
            rating.setLiked(isLiked);
            ratingRepo.save(rating);
        } else {
            Rating newRating = Rating.builder()
                    .user(user)
                    .product(product)
                    .isLiked(isLiked)
                    .build();
            ratingRepo.save(newRating);
        }
    }

    // 🗑️ Eliminar reacción
    public void deleteReaction(Long userId, Long productId) {
        ratingRepo.findByUserIdAndProductId(userId, productId)
                .ifPresent(ratingRepo::delete);
    }

    // 📊 Contar likes de un producto
    public long countLikes(Long productId) {
        return ratingRepo.countLikesByProductId(productId);
    }

    // 🔍 Ver si un usuario reaccionó
    public boolean hasUserLiked(Long userId, Long productId) {
        return ratingRepo.findByUserIdAndProductId(userId, productId)
                .map(Rating::isLiked)
                .orElse(false);
    }
}
