package com.bookingcar.kientv84.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class AccountRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private List<String> roles;
}
