package com.huerfanosSRL.Huerfanosback.auth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="user_2fa")
public class TwoFAModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserModel user;

    private String secretKey;
    private Boolean enabled = false;
    private LocalDateTime createdAt;
}