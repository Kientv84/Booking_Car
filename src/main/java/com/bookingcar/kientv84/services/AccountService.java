package com.bookingcar.kientv84.services;

import com.bookingcar.kientv84.dtos.respones.AccountResponse;
import com.bookingcar.kientv84.entities.AccountEntity;
import com.bookingcar.kientv84.dtos.requests.AccountRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {

    ResponseEntity<List<AccountResponse>> getALlAccount();

    ResponseEntity<AccountResponse> getAccountById(Long id);

    boolean createAccount(AccountRequest account);

    ResponseEntity updateAccount(Long id, AccountRequest accountRequest);

    boolean deleteAccount(List<Long> ids);
}
