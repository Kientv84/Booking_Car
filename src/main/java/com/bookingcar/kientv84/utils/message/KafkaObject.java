package com.bookingcar.kientv84.utils.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class KafkaObject {
  private String topic;
  private String messageError;

  private KafkaObject() {}
}
