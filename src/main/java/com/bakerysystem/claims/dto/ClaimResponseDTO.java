package com.bakerysystem.claims.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClaimResponseDTO {
    private Long id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;
    private List<ClaimImageResponseDTO> images;
}
