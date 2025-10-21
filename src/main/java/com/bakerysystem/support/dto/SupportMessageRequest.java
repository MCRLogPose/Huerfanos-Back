package com.bakerysystem.support.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportMessageRequest {
    private Long userId; // opcional
    private String fromEmail;
    private String subject;
    private String body;
}