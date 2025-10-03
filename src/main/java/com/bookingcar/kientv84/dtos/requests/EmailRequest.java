package com.bookingcar.kientv84.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequest {
  private String to;
  private String subject;
  private String body;
}
