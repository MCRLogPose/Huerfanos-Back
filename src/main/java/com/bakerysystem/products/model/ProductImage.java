package com.bakerysystem.products.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 50)
    private String provider; // Ej: "cloudinary", "aws-s3", "local"

    @Column(name = "provider_id")
    private String providerId; // id devuelto por la nube

    @Column(nullable = false, length = 1024)
    private String url;

    @Column(name = "alt_text")
    private String altText;

    private Integer position = 0;
}
