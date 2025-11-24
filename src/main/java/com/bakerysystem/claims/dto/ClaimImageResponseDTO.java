package com.bakerysystem.claims.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClaimImageResponseDTO {
    private Long id;
    private String url;
    private String provider;
    private String altText;
    private Integer position;
}
