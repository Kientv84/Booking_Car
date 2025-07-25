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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final ObjectMapper objectMapper;

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

    redisService.setValue("account:" + accountResponse.getId(), accountResponse, 45);

    //    redisService.setValue("account", accountResponse, 30);
    return accountResponse;
  }

  //  @Cacheable(value = "accounts", key = "#id")
  @Override
  public Account getAccountById(Long id) {
    log.info("Start get account by id ...");

    Account account = redisService.getValue("account:" + id, Account.class);

    if (Objects.isNull(account)) {
      // lấy từ DB
      AccountEntity accountEntity =
          accountRepository
              .findById(id)
              .orElseThrow(
                  () ->
                      new AccountServiceException(
                          accountMessage.code, accountMessage.failRegister));

      // Lưu vào cache, và xóa accounts::all để tránh lỗi dữ liệu củ
      redisService.setValue("account:" + id, accountEntity, 3600);
      redisService.deleteByKey("accounts::all");

      return accountMapper.mapToAccountModel(accountEntity);
    } else {
      log.info("Get account by cache ...");
      return account;
    }

    //    Account account = redisService.getValue("account:" + id, Account.class);
    //    //    Account account = redisService.getValue("account", Account.class);
    //
    //    if (Objects.nonNull(account)) {
    //      log.info("Return account in cache ...");
    //      return account;
    //    }
    //
    //    log.info("Get account from DB");
    //    AccountEntity accountEntity =
    //        accountRepository
    //            .findById(id)
    //            .orElseThrow(
    //                () ->
    //                    new AccountServiceException(accountMessage.code,
    // accountMessage.failRegister));
    //
    //    return accountMapper.mapToAccountModel(accountEntity);
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
    // Gọi cache ra

    String cacheData = redisService.getValue("accounts::all", String.class); // Gọi từ trong cache

    if (cacheData == null || cacheData.isEmpty()) { // Kiểm tra nếu null hoặc rỗng thì gọi từ db
      log.info("Get all account from DB...");
      List<AccountEntity> accounts = accountRepository.findAll();

      //        String jsonData = objectMapper.writeValueAsString(accounts);  //serialize thành
      // JSON,Phương thức sẽ tự động chuyển đổi bất kỳ đối tượng nào (từ primitive, object, List,
      // Map, ...) thành một chuỗi JSON tương ứng.

      redisService.setValue("accounts::all", accounts, 3600); // setValue cho redis, do redis chỉ

      List<Account> response =
          accounts.stream().map(accountMapper::mapToAccountModel).collect(Collectors.toList());

      return response;
    } else {

      log.info("Return list account from Cache");
      try {
        return objectMapper.readValue(
            cacheData,
            new TypeReference<List<Account>>() { // TypeReference là hỗ trợ lấy ra List danh sách
            });
      } catch (JsonProcessingException e) {
        System.out.println("Error while converting JSON");
        throw new RuntimeException(e);
      }
    }

    // chuyển đổi accounts danh sách thành JSON

    //    List<AccountEntity> accounts = accountRepository.findAll();
    //
    //    if (accounts == null || accounts.isEmpty()) {
    //      throw new AccountServiceException(accountMessage.code, accountMessage.failGetAll);
    //    }
    //
    //    List<Account> responseList =
    //        accounts
    //            .stream() // Stream là cách biến đổi 1 list thành 1 dòng piline để thực hiện sử lý
    // tuần
    //            // tự
    //            // Ngoài ra khi .stream còn có thể
    //            //    .filter(...)     // lọc
    //            //    .map(...)        // chuyển đổi
    //            //    .sorted(...)     // sắp xếp
    //            // Có nghĩa là khi . stream thì ta đang muốn xử lý từng phần tử trong danh sách 1
    // cách
    //            // tuần tự, kiểu loop qua trong javascript
    //            .map(
    //                accountMapper
    //                    ::mapToAccountModel) // Đến đây khi map qua thì sẽ trả về Stream<T> tạm
    // thời,
    //            // nên nếu muốn nó trở về list thì cần
    //            // .collect(Collectors.toList())
    //            .collect(Collectors.toList());
    //
    //    // Bonus: Stream: Trong java thì stream không phải là List mà được hiểu là một dòng chãy,
    // có thể
    //    // hiểu là dòng chãy chãy từ A->B->C, Một cách tuần tự
    //    //	Trừu tượng hóa dòng dữ liệu → dễ xử lý lớn, tuần tự hoặc song song
    //    // chỉ truyền qua các bước để sử lý, và immutable tức là một thay đôi dữ liệu gốc
    //
    //    //       Pipeline: Là một chuỗi các bước xử lý dữ liệu có thể tái sử dụng
    //    //       Thiết kế xử lý theo từng bước → dễ mở rộng, bảo trì, test từng bước
    //
    //    return responseList;
  }

  @Override
  public Account updateAccount(Long id, AccountRequest accountRequest) {
    log.info("Start update account ...");

    AccountEntity account = redisService.getValue("account:" + id, AccountEntity.class);

    if (account == null) {
      // Nếu cache chưa có, lấy từ DB
      log.info("Get account from db to update ...");
      account =
          accountRepository
              .findById(id)
              .orElseThrow(
                  () ->
                      new AccountServiceException(
                          accountMessage.code, accountMessage.failRegister));
    }

    log.info("Get account from cache to update ...");
    account.setUsername(accountRequest.getUsername());
    account.setPassword(accountRequest.getPassword());

    AccountEntity savedAccount = accountRepository.save(account);

    redisService.setValue("account:" + id, savedAccount, 3600);
    redisService.deleteByKey("accounts::all");
    // Tôi muốn logic ở đây sẽ là xóa accounts::all từ getAll để sync dữ liệu

    return accountMapper.mapToAccountModel(savedAccount);

    //    AccountEntity account = accountRepository.findById(id).orElse(null);
    //
    //    if (account == null) {
    //      throw new AccountServiceException(accountMessage.code, accountMessage.failGetById);
    //    } else {
    //      if (accountRequest.getUsername() != null) {
    //        account.setUsername(accountRequest.getUsername());
    //      }
    //      if (accountRequest.getPassword() != null) {
    //        account.setPassword(accountRequest.getPassword());
    //      }
    //
    //      AccountEntity newAccount = accountRepository.save(account);
    //
    //      Account respone = accountMapper.mapToResponse(newAccount);
    //
    //      return respone;
    //    }
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

    redisService.deleteByKey("accounts::all");

    log.info("Deleted accounts with ids: {}", ids);
    return true;
  }
}

// Annotation	Mục đích	Ví dụ
// @Cacheable	Lấy giá trị từ cache hoặc gọi method nếu cache chưa có	@Cacheable(value="accounts",
// key="#id")
// @CachePut	Cập nhật cache sau khi thực thi method	@CachePut(value="accounts", key="#result.id")
// @CacheEvict	Xóa cache khi dữ liệu thay đổi	@CacheEvict(value="accounts", allEntries=true)
