package com.bookingcar.kientv84.dtos.respones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RefreshTokenResponse {
    private String token;
}
