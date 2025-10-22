package com.bakerysystem.ratings.model;

import com.bakerysystem.auth.model.User;
import com.bakerysystem.products.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "product_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "is_liked", nullable = false)
    private boolean isLiked;
}
