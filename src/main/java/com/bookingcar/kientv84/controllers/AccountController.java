package com.bookingcar.kientv84.controllers;

import com.bookingcar.kientv84.messagsing.producer.AccountProducer;
import com.bookingcar.kientv84.services.AccountService;
import com.example.api.AccountApi;
import com.example.model.Account;
import com.example.model.AccountRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController implements AccountApi {

  private final AccountService accountService;
  private final AccountProducer accountProducer;

  @Override
  public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest) {
    return ResponseEntity.ok(accountService.createAccount(accountRequest));
  }

  @Override
  public ResponseEntity<List<Account>> getAllAccount() {
    return ResponseEntity.ok(accountService.getAllAccount());
  }

  @Override
  public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }

  @Override
  public ResponseEntity<Account> updateAccountById(
      @PathVariable Long id, @RequestBody AccountRequest accountRequest) {
    return ResponseEntity.ok(accountService.updateAccount(id, accountRequest));
  }

  @Override
  public ResponseEntity<Boolean> deleteAccount(@PathVariable List<Long> ids) {
    return ResponseEntity.ok(accountService.deleteAccount(ids));
  }

  // Moc api to produce message
  @PostMapping("v2/accounts")
  public ResponseEntity<Boolean> performAccount(@Valid @RequestBody AccountRequest accountRequest) {
    return ResponseEntity.ok(accountProducer.produceCreateAccountEvent(accountRequest));
  }
}
