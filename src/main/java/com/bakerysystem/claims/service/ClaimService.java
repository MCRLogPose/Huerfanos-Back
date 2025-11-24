package com.bakerysystem.claims.service;

import com.bakerysystem.auth.model.User;
import com.bakerysystem.auth.repository.UserRepository;
import com.bakerysystem.claims.dto.*;
import com.bakerysystem.claims.mapper.ClaimMapper;
import com.bakerysystem.claims.model.*;
import com.bakerysystem.claims.repository.ClaimRepository;
import com.bakerysystem.common.storage.CloudStorageService;
import com.bakerysystem.orders.model.Order;
import com.bakerysystem.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimImageService claimImageService;
    private final CloudStorageService storageService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ClaimMapper claimMapper;

    public ClaimResponseDTO create(ClaimRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Claim claim = Claim.builder()
                .user(user)
                .order(order)
                .description(request.getDescription())
                .status(ClaimStatus.PENDING)
                .build();

        Claim saved = claimRepository.save(claim);

        // Subir imágenes (si existen)
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<String> urls = request.getImages().stream()
                    .map(file -> storageService.upload(file, "claims"))
                    .collect(Collectors.toList());
            claimImageService.saveMultiple(saved, urls, "cloudinary");
        }

        return toResponse(saved);
    }

    public List<ClaimResponseDTO> getAll() {
        return claimRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ClaimResponseDTO markAsReviewed(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Reclamo no encontrado"));
        claim.setStatus(ClaimStatus.REVIEWED);
        claim.setReviewedAt(java.time.LocalDateTime.now());
        return toResponse(claimRepository.save(claim));
    }

    private ClaimResponseDTO toResponse(Claim claim) {
        return ClaimResponseDTO.builder()
                .id(claim.getId())
                .description(claim.getDescription())
                .status(claim.getStatus().name())
                .createdAt(claim.getCreatedAt())
                .reviewedAt(claim.getReviewedAt())
                .images(claim.getImages() != null ?
                        claim.getImages().stream()
                                .map(img -> ClaimImageResponseDTO.builder()
                                        .url(img.getUrl())
                                        .provider(img.getProvider())
                                        .altText(img.getAltText())
                                        .position(img.getPosition())
                                        .build())
                                .toList()
                        : List.of())
                .userId(claim.getUser() != null ? claim.getUser().getId() : null)
                .orderId(claim.getOrder() != null ? claim.getOrder().getId() : null)
                .build();
    }
}
