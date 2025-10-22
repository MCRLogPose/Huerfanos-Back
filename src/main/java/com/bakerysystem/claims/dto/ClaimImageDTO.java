package com.bakerysystem.claims.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimImageDTO {
    private Long id;
    private String url;
    private String publicId;
}
