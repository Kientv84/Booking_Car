package com.bookingcar.kientv84.utils.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "account")
public class AccountMessage {
    public String code;
    public String successRegister;
    public String successUpdate;
    public String successGetAll;
    public String successGetById;
    public String successDelete;
    public String failRegister;
    public String failUpdate;
    public String failGetAll;
    public String failGetById;
    public String failDelete;
    public String usernameNotnull;
    public String passwordNotnull;
}
