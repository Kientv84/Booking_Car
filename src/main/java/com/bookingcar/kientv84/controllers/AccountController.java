package com.bookingcar.kientv84.controllers;

import com.bookingcar.kientv84.services.AccountService;
import com.example.api.AccountApi;
import com.example.model.Account;
import com.example.model.AccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController implements AccountApi {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AccountApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Account> createAccount(AccountRequest accountRequest) {
       return ResponseEntity.ok(accountService.createAccount(accountRequest));
    }

//    @PostMapping("/register")
//    public boolean createAccount( @Valid  @RequestBody AccountRequest account) {
//        return accountService.createAccount(account);
//    }
//
//
//    @GetMapping("/accounts")
//    public ResponseEntity<List<AccountResponse>> getAllAccount(){
//        return  accountService.getALlAccount();
//    }
//
//    @GetMapping("/account/{id}")
//    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
//        return accountService.getAccountById(id);
//    }
//
//    @PostMapping("/account/update/{id}")
//    public  ResponseEntity updateAccount(@PathVariable Long id, @RequestBody AccountRequest accountRequest) {
//        return accountService.updateAccount(id, accountRequest);
//    }
//
//    @PostMapping("/accounts/delete")
//    public boolean deleteAccount(@RequestBody List<Long> ids) {
//        return accountService.deleteAccount(ids);
//    }
}
