package com.bookingcar.kientv84.messagsing.producer.impls;

import com.bookingcar.kientv84.messagsing.producer.AccountProducer;
import com.bookingcar.kientv84.properties.KafkaTopicProperties;
import com.bookingcar.kientv84.services.KafkaService;
import com.bookingcar.kientv84.utils.message.KafkaObject;
import com.example.model.Account;
import com.example.model.AccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountProducerImpl implements AccountProducer {
  private final KafkaTopicProperties kafkaTopicProperties;
  private final KafkaService kafkaService;

  // Sau khi consume message, xu ly tao account success -> produc message vo topic nay

  @Override
  public void produceAccountEventSuccess(Account message) {
    var topic = kafkaTopicProperties.getAccountCreated();
    log.info("[produceAccountEventSuccess] producing account to topic {}", topic);
    kafkaService.send(topic, message);
  }

  // mock: produce message vo topic created_account de yeu cau account service tao new account
  // Khong quan tam ket qua tra ve return default boolean true

  @Override
  public Boolean produceCreateAccountEvent(AccountRequest message) {
    var topic = kafkaTopicProperties.getCreatedAccount();
    log.info("[produceCreateAccountEvent] producing account to topic {}", topic);
    kafkaService.send(topic, message);
    return true;
  }

  @Override
  public void produceMessageError(KafkaObject kafkaObject) {

    var topic = kafkaTopicProperties.getErrorAccount();
    log.info("[produceMessageError] producing error to topic {}", topic);

    kafkaService.send(topic, kafkaObject);
  }
}
