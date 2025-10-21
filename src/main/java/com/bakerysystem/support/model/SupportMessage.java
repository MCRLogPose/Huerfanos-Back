package com.bakerysystem.support.model;

import com.bakerysystem.auth.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupportMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_support_user"))
    private User user;

    @Column(nullable = false)
    private String fromEmail;

    @Column(nullable = false)
    private String toEmail;

    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
