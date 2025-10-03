// package com.bookingcar.kientv84.controllers;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import com.bookingcar.kientv84.services.AccountService;
// import com.example.model.Account;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import config.JwtMockConfig;
// import java.util.List;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Import;
// import org.springframework.test.web.servlet.MockMvc;
//
/// **
// * Cần test các endpoint trả về: vd status 200 OK, 404 Not Found, vv Format đúng json Các service
// * được gọi đúng cách (mock service) Validate đầu vào (request body, query param...)
// */
// @Import(JwtMockConfig.class)
// @WebMvcTest(
//    AccountController.class) // Anotation test để kiểm tra controller (Test REST API + Mock
// service)
// class AccountControllerTest {
//
//  /**
//   * MockMvc là một class do Spring cung cấp dùng để: Mô phỏng các HTTP request tới controller
// Kiểm
//   * tra response (status code, nội dung JSON, header,...) Dùng để test controller một cách nhanh,
//   * không cần chạy toàn bộ ứng dụng
//   */
//  @Autowired
//  private MockMvc
//      mockMvc; // Giả lập các HTTP request như GET, POST, PUT, DELETE để test các endpoint của
// REST
//
//  // API mà không cần phải khởi chạy server thật.
//
//  @InjectMocks AccountService accountService;
//
//  @Mock
// ObjectMapper objectMapper;
//
//  @Test
//  void createAccountWebMvcTest() throws Exception {
//    // TODO: viết sau
//  }
//
//  @Test
//  void getAllAccountWebMvcTest() throws Exception {
//    assertNotNull(accountService, "accountService should not be null");
//
//    Account account1 = new Account();
//    account1.setId(1L);
//    account1.setUsername("kientv");
//    account1.setEmail("kientv@example.com");
//
//    Account account2 = new Account();
//    account2.setId(2L);
//    account2.setUsername("kientv2");
//    account2.setEmail("kientv2@example.com");
//
//    List<Account> accounts = List.of(account1, account2);
//
//    Mockito.when(accountService.getAllAccount()).thenReturn(accounts);
//
//    mockMvc
//        .perform(get("/api/v1/accounts"))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$[0].username").value("kientv"))
//        .andExpect(jsonPath("$[0].email").value("kientv@example.com"))
//        .andDo(print()); // in ra toàn bộ response để debug
//  }
//
//  @Test
//  void getAccountById() {
//    // TODO: viết sau
//  }
//
//  @Test
//  void updateAccountById() {
//    // TODO: viết sau
//  }
//
//  @Test
//  void deleteAccount() {
//    // TODO: viết sau
//  }
//
//  /** Cung cấp mock bean thay thế cho @MockBean (đã deprecated). */
//  @TestConfiguration
//  static class TestConfig {
//    @Bean
//    public AccountService accountService() {
//      return Mockito.mock(AccountService.class);
//    }
//  }
// }
