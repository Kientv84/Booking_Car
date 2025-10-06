package com.bookingcar.kientv84.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CronJobSchedule {

  //  @Scheduled(cron = "${scheduler.interval}", zone = "${scheduler.interval.zone}") // Mỗi 5s 1
  // lần
  //  public void logEveryMinuteA() throws InterruptedException {
  //    log.info("Task A chạy lúc: " + OffsetDateTime.now());
  //    Thread.sleep(8000); // Giải định task chạy lâu
  //    log.info("Task A end lúc: " + OffsetDateTime.now());
  //  }
  //
  //  @Scheduled(cron = "${scheduler.interval}", zone = "${scheduler.interval.zone}")
  //  public void logEveryMinuteB() {
  //    log.info("Task B chạy lúc: " + OffsetDateTime.now());
  //  }

  // TODO: use @Async
  //  @Scheduled(cron = "${scheduler.interval}", zone = "${scheduler.interval.zone}")
  //  public void triggerTask() {
  //    log.info("Scheduled call at : {} | {}", OffsetDateTime.now(),
  // Thread.currentThread().getName());
  //    doAsyncWork();
  //  }
  //
  //  @Async
  //  public void doAsyncWork() {
  //    log.info(" Start async task - {}", Thread.currentThread().getName());
  //    try {
  //      Thread.sleep(7000);
  //    } catch (InterruptedException exception) {
  //      Thread.currentThread().interrupt();
  //    }
  //    log.info("End process async - {}", Thread.currentThread().getName());
  //  }
}
