package com.bookingcar.kientv84.services;

import com.example.model.Account;
import com.example.model.AccountRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {

//    ResponseEntity<List<AccountResponse>> getALlAccount();
//
//    ResponseEntity<AccountResponse> getAccountById(Long id);

    Account createAccount(AccountRequest account);

//    ResponseEntity updateAccount(Long id, AccountRequest accountRequest);
//
//    boolean deleteAccount(List<Long> ids);
}
