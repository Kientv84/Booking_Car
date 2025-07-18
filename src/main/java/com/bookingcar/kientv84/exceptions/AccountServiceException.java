package com.bookingcar.kientv84.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountServiceException extends RuntimeException {
  private final String errorCode;
  private final String messageCode;
}
