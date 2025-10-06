// package com.bookingcar.kientv84.services.impls;
//
// import static org.junit.jupiter.api.Assertions.*;
//
// import com.bookingcar.kientv84.mappers.AccountMapper;
// import com.bookingcar.kientv84.repositories.AccountRepository;
// import com.example.model.AccountRequest;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.context.ActiveProfiles;
//
// @SpringBootTest
// @ActiveProfiles("Test")
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// class AccountServiceIntegrationTest {
//
//  @Autowired AccountServiceImpl accountService;
//  @Autowired AccountRepository accountRepository;
//  @Autowired AccountMapper accountMapper;
//  @Autowired PasswordEncoder passwordEncoder;
//  @Autowired RedisServiceImpl redisService;
//
//  @BeforeEach
//  void setUp() {
//    accountRepository.deleteAll(); // Clean DB before each test
//  }
//
//  @Test
//  void getAccount_WhenCreateAccounts_shouldReturnAccounts() {
//    // Given
//    accountService.createAccount(new AccountRequest("example1", "123"));
//    accountService.createAccount(new AccountRequest("example2", "234"));
//
//    // When
//    var result = accountService.getAllAccount();
//
//    assertFalse(result.isEmpty());
//    assertEquals("example1", result.get(0).getUsername());
//    assertEquals("example2", result.get(1).getUsername());
//  }
// }
