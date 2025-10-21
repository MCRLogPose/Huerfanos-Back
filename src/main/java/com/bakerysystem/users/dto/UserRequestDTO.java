package com.bakerysystem.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
