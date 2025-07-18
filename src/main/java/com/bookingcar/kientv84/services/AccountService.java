package com.bookingcar.kientv84.services;

import com.example.model.Account;
import com.example.model.AccountRequest;
import java.util.List;

public interface AccountService {

  List<Account> getALlAccount();

  Account getAccountById(Long id);

  Account createAccount(AccountRequest account);

  Account updateAccount(Long id, AccountRequest accountRequest);

  boolean deleteAccount(List<Long> ids);
}
