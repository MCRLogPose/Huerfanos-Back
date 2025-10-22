package com.bakerysystem.claims.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "claim_images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClaimImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String provider;
    private String altText;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "claim_id")
    private Claim claim;
}
