package com.bookingcar.kientv84.messagsing.producer;

import com.example.model.Account;
import com.example.model.AccountRequest;

public interface AccountProducer {

  void produceAccountEventSuccess(Account message);

  Boolean produceCreateAccountEvent(AccountRequest message);
}
