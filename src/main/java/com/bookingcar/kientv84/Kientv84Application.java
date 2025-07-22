package com.bookingcar.kientv84;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Kientv84Application {

  public static void main(String[] args) {
    SpringApplication.run(Kientv84Application.class, args);
  }
}
