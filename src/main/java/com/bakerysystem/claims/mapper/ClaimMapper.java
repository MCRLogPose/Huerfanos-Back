package com.bakerysystem.claims.mapper;

import com.bakerysystem.claims.dto.ClaimImageResponseDTO;
import com.bakerysystem.claims.dto.ClaimResponseDTO;
import com.bakerysystem.claims.model.Claim;
import com.bakerysystem.claims.model.ClaimImage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClaimMapper {

    public ClaimResponseDTO toDTO(Claim claim) {
        return ClaimResponseDTO.builder()
                .id(claim.getId())
                .description(claim.getDescription())
                .status(claim.getStatus().toString())
                .createdAt(claim.getCreatedAt())
                .reviewedAt(claim.getReviewedAt())
                .images(
                        claim.getImages() != null
                                ? claim.getImages().stream()
                                .map(this::mapImage)
                                .collect(Collectors.toList())
                                : null
                )
                .userId(claim.getUser() != null ? claim.getUser().getId() : null)
                .orderId(claim.getOrder() != null ? claim.getOrder().getId() : null)
                .build();
    }

    private ClaimImageResponseDTO mapImage(ClaimImage img) {
        return ClaimImageResponseDTO.builder()
                .id(img.getId())
                .url(img.getUrl())
                .build();
    }
}
