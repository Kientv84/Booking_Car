package com.bookingcar.kientv84.messagsing;

import com.bookingcar.kientv84.services.AccountService;
import com.example.model.AccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountConsumer {
  private final AccountService accountService;

  @KafkaListener(
      topics = "${spring.kafka.account.topic.created-account}",
      groupId = "spring.kafka.account.group")
  public void onMessageHandler(@Payload AccountRequest message) {
    try {
      log.info("[onMessageHandler] Start consuming message ...");
      accountService.createdAccount(message);
      log.info("[onMessageHandler] Created account ...");
    } catch (Exception e) {
      log.error("[onMessageHandler] Error while creating account . Err {}", e.getMessage());
    }
  }
}
