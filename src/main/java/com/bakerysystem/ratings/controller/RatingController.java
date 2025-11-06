package com.bakerysystem.ratings.controller;

import com.bakerysystem.ratings.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    // 👍 Reaccionar o quitar like
    @PostMapping("/react")
    public ResponseEntity<String> toggleReaction(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam boolean isLiked
    ) {
        ratingService.toggleLike(userId, productId, isLiked);
        return ResponseEntity.ok(isLiked ? "Like agregado" : "Like removido");
    }

    // Eliminar completamente la reacción
    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteReaction(
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        ratingService.deleteReaction(userId, productId);
        return ResponseEntity.ok("Reacción eliminada");
    }

    // 📊 Contar likes por producto
    @GetMapping("/count/{productId}")
    public ResponseEntity<Long> countLikes(@PathVariable Long productId) {
        return ResponseEntity.ok(ratingService.countLikes(productId));
    }

    // 🔍 Ver si el usuario ya dio like
    @GetMapping("/check")
    public ResponseEntity<Boolean> hasLiked(
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        return ResponseEntity.ok(ratingService.hasUserLiked(userId, productId));
    }
}
