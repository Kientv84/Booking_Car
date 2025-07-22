package com.bookingcar.kientv84.services.impls;

import com.bookingcar.kientv84.entities.AccountEntity;
import com.bookingcar.kientv84.exceptions.AccountServiceException;
import com.bookingcar.kientv84.mappers.AccountMapper;
import com.bookingcar.kientv84.repositories.AccountRepository;
import com.bookingcar.kientv84.services.AccountService;
import com.bookingcar.kientv84.services.RedisService;
import com.bookingcar.kientv84.utils.message.AccountMessage;
import com.example.model.Account;
import com.example.model.AccountRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor // sử dụng cho final hoặc các field not null
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final RedisService redisService;

  private final PasswordEncoder passwordEncoder;

  @Autowired private AccountMessage accountMessage;

  @Override
  public Account createAccount(AccountRequest accountRequest) {
    log.info("Start create account ...");

    String encodePassword = passwordEncoder.encode(accountRequest.getPassword());
    var account = accountMapper.map(accountRequest);
    account.setPassword(encodePassword);
    AccountEntity accountEntity = accountRepository.save(account);

    Account accountResponse = accountMapper.mapToAccountModel(accountEntity);

    log.info("Save account to cache ...");

    redisService.setValue("account", accountResponse);
    return accountResponse;
  }

  @Override
  public Account getAccountById(Long id) {
    log.info("Start get account by id ...");

    Account account = redisService.getValue("account", Account.class);

    if (Objects.nonNull(account)) {
      log.info("Return account in cache ...");
      return account;
    }

    log.info("Get account from DB");
    AccountEntity accountEntity =
        accountRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new AccountServiceException(accountMessage.code, accountMessage.failRegister));

    return accountMapper.mapToAccountModel(accountEntity);
    //    if (account != null) {
    //      Account response = accountMapper.mapToResponse(account);
    //      return response;
    //    } else {
    //      throw new AccountServiceException(accountMessage.code, accountMessage.failGetById);
    //    }
  }

  @Override
  public List<Account> getALlAccount() {
    log.info("Start get all account ...");

    List<AccountEntity> accounts = accountRepository.findAll();

    if (accounts == null || accounts.isEmpty()) {
      throw new AccountServiceException(accountMessage.code, accountMessage.failGetAll);
    }

    List<Account> responseList =
        accounts
            .stream() // Stream là cách biến đổi 1 list thành 1 dòng piline để thực hiện sử lý tuần
            // tự
            // Ngoài ra khi .stream còn có thể
            //    .filter(...)     // lọc
            //    .map(...)        // chuyển đổi
            //    .sorted(...)     // sắp xếp
            // Có nghĩa là khi . stream thì ta đang muốn xử lý từng phần tử trong danh sách 1 cách
            // tuần tự, kiểu loop qua trong javascript
            .map(
                accountMapper
                    ::mapToAccountModel) // Đến đây khi map qua thì sẽ trả về Stream<T> tạm thời,
            // nên nếu muốn nó trở về list thì cần
            // .collect(Collectors.toList())
            .collect(Collectors.toList());

    // Bonus: Stream: Trong java thì stream không phải là List mà được hiểu là một dòng chãy, có thể
    // hiểu là dòng chãy chãy từ A->B->C, Một cách tuần tự
    //	Trừu tượng hóa dòng dữ liệu → dễ xử lý lớn, tuần tự hoặc song song
    // chỉ truyền qua các bước để sử lý, và immutable tức là một thay đôi dữ liệu gốc

    //       Pipeline: Là một chuỗi các bước xử lý dữ liệu có thể tái sử dụng
    //       Thiết kế xử lý theo từng bước → dễ mở rộng, bảo trì, test từng bước

    return responseList;
  }

  @Override
  public Account updateAccount(Long id, AccountRequest accountRequest) {
    log.info("Start update account ...");

    AccountEntity account = accountRepository.findById(id).orElse(null);

    if (account == null) {
      throw new AccountServiceException(accountMessage.code, accountMessage.failGetById);
    } else {
      if (accountRequest.getUsername() != null) {
        account.setUsername(accountRequest.getUsername());
      }
      if (accountRequest.getPassword() != null) {
        account.setPassword(accountRequest.getPassword());
      }

      AccountEntity newAccount = accountRepository.save(account);

      Account respone = accountMapper.mapToResponse(newAccount);

      return respone;
    }
  }

  @Override
  public boolean deleteAccount(List<Long> ids) {
    log.info("Start delete accounts");

    if (ids == null || ids.isEmpty()) {
      log.warn("Empty list of ids!!");
      return false;
    } else {
      accountRepository.deleteAllById(ids);
    }

    log.info("Deleted accounts with ids: {}", ids);
    return true;
  }
}
