package com.bookingcar.kientv84.dtos.respones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponese<T> {
  private int success;
  private String code;
  private String message;
  private T data;
}
