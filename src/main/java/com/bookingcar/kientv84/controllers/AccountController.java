package com.bookingcar.kientv84.controllers;

import com.bookingcar.kientv84.dtos.requests.AccountRequest;
import com.bookingcar.kientv84.dtos.respones.AccountResponse;
import com.bookingcar.kientv84.entities.AccountEntity;
import com.bookingcar.kientv84.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/register")
    public boolean createAccount(@RequestBody AccountRequest account) {
        return accountService.createAccount(account);
    }


    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccount(){
        return  accountService.getALlAccount();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/account/update/{id}")
    public  ResponseEntity updateAccount(@PathVariable Long id, @RequestBody AccountRequest accountRequest) {
        return accountService.updateAccount(id, accountRequest);
    }

    @PostMapping("/accounts/delete")
    public boolean deleteAccount(@RequestBody List<Long> ids) {
        return accountService.deleteAccount(ids);
    }
}
