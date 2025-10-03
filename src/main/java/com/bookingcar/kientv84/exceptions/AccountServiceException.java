package com.bookingcar.kientv84.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountServiceException extends RuntimeException {
  private final String errorCode;
  private final String messageCode;
}
