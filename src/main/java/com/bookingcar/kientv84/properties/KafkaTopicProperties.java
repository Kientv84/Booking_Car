package com.bookingcar.kientv84.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties("spring.kafka.account.topic")
public class KafkaTopicProperties {
  private String createdAccount;
  private String accountCreated;
}
