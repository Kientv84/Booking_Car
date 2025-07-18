package com.bookingcar.kientv84.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:language/messages_account");
    // Nhiều file vd: "classpath:language/messages_account", "classpath:language/messages_user"
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
