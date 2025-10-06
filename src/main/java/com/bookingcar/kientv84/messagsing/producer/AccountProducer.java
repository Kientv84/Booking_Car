package com.bookingcar.kientv84.messagsing.producer;

import com.bookingcar.kientv84.utils.message.KafkaObject;
import com.example.model.Account;
import com.example.model.AccountRequest;

public interface AccountProducer {

  void produceAccountEventSuccess(Account message);

  Boolean produceCreateAccountEvent(AccountRequest message);

  void produceMessageError(KafkaObject kafkaObject);
}
