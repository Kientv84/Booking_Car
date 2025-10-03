package com.bookingcar.kientv84.intergration;

import com.bookingcar.kientv84.dtos.requests.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "${openfeign.notificationClient.name}",
    url = "${spring.cloud.notificationClient.client.config.notificationClient.url}")
public interface NotificationClient {

  @PostMapping(
      value = "${openfeign.notificationClient.url.send-email}",
      consumes = "application/json")
  ResponseEntity<String> sendEmail(@RequestBody EmailRequest request);
}
