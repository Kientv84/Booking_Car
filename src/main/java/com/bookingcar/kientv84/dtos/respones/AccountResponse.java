package com.bookingcar.kientv84.dtos.respones;

import com.bookingcar.kientv84.entities.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private String username;

    public AccountResponse(AccountEntity entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
    }
}
