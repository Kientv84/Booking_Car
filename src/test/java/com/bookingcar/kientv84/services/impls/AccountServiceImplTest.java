// package com.bookingcar.kientv84.services.impls;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
//
// import com.bookingcar.kientv84.entities.AccountEntity;
// import com.bookingcar.kientv84.mappers.AccountMapper;
// import com.bookingcar.kientv84.repositories.AccountRepository;
// import com.bookingcar.kientv84.services.RedisService;
// import com.example.model.Account;
// import java.util.ArrayList;
// import java.util.List;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
// @ExtendWith(MockitoExtension.class)
// class AccountServiceImplTest {
//
//  @InjectMocks AccountServiceImpl accountService;
//
//  @Mock AccountRepository accountRepository;
//
//  @Mock AccountMapper accountMapper;
//
//  @Mock PasswordEncoder passwordEncoder;
//
//  @Mock RedisService redisService;
//
//  @Test
//  void getAccount_shouldReturnAccounts() {
//    // prepare data
//    var accountEntities = buildAccountEntities();
//    var accountResponse = buildAccounts();
//
//    // Giải lập hành vi mock
//    when(accountRepository.findAll()).thenReturn(accountEntities);
//    when(accountMapper.mapToAccountModel(any())).thenReturn(accountResponse);
//    //
//    //
// when(accountMapper.mapToAccountModel(accountEntities.get(1))).thenReturn(accountResponse.get(1));
//
//    var result = accountService.getAllAccount();
//
//    assertNotNull(result);
//    assertEquals(accountResponse.getUsername(), result.get(0).getUsername());
//  }
//
//  private List<AccountEntity> buildAccountEntities() {
//    var accounts = new ArrayList<AccountEntity>();
//    var accountResp1 = new AccountEntity();
//    accountResp1.setId(1L);
//    accountResp1.setUsername("example1");
//    accountResp1.setEmail("example1@gmail.com");
//    var accountResp2 = new AccountEntity();
//    accountResp2.setId(2L);
//    accountResp2.setUsername("example2");
//    accountResp1.setEmail("example2@gmail.com");
//    accounts.add(accountResp1);
//    accounts.add(accountResp2);
//    return accounts;
//  }
//
//  private Account buildAccounts() {
//    var accountResp1 = new Account();
//    accountResp1.setId(1L);
//    accountResp1.setUsername("example1");
//    accountResp1.setEmail("example1@gmail.com");
//
//    return accountResp1;
//  }
// }
