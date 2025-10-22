package com.bakerysystem.claims.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ClaimRequestDTO {
    private Long userId;
    private Long orderId;
    private String description;
    private List<MultipartFile> images; // Evidencias opcionales
}
