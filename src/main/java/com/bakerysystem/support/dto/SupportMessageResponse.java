package com.bakerysystem.support.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportMessageResponse {
    private Long id;
    private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
    private LocalDateTime createdAt;
    private String userFullName;
}