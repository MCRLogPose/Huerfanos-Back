package com.bakerysystem.claims.controller;

import com.bakerysystem.claims.dto.ClaimRequestDTO;
import com.bakerysystem.claims.dto.ClaimResponseDTO;
import com.bakerysystem.claims.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<ClaimResponseDTO> createClaim(
            @RequestParam Long userId,
            @RequestParam Long orderId,
            @RequestParam String description,
            @RequestParam(required = false) List<MultipartFile> images) {

        ClaimRequestDTO request = new ClaimRequestDTO();
        request.setUserId(userId);
        request.setOrderId(orderId);
        request.setDescription(description);
        request.setImages(images);

        return ResponseEntity.ok(claimService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ClaimResponseDTO>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAll());
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<ClaimResponseDTO> markAsReviewed(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.markAsReviewed(id));
    }
}
