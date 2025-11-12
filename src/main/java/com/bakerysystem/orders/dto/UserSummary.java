package com.bakerysystem.orders.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
}
