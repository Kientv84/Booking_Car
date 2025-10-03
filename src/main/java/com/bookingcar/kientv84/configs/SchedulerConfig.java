package com.bookingcar.kientv84.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setPoolSize(5); // cho phép 5 threads chạy song song
    scheduler.setThreadNamePrefix("MyDemoScheduler-");
    scheduler.initialize();
    return scheduler;
  }
}
