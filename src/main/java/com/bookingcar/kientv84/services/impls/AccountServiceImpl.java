package com.bookingcar.kientv84.services.impls;

import com.bookingcar.kientv84.dtos.respones.AccountResponse;
import com.bookingcar.kientv84.entities.AccountEntity;
import com.bookingcar.kientv84.mappers.AccountMapper;
import com.bookingcar.kientv84.repositories.AccountRepository;
import com.bookingcar.kientv84.dtos.requests.AccountRequest;
import com.bookingcar.kientv84.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean createAccount(AccountRequest accountRequest) {
        log.info("Start create account ...");

        String encodePassword = passwordEncoder.encode(accountRequest.getPassword());
        var account = accountMapper.map(accountRequest);
        account.setPassword(encodePassword);
        return !ObjectUtils.isEmpty(accountRepository.save(account));
    }

    @Override
    public ResponseEntity<AccountResponse> getAccountById(Long id) {
        log.info("Start get account by id ...");

        try {
            AccountEntity account = accountRepository.findById(id).orElse(null);

            if (account != null) {
                AccountResponse response = accountMapper.mapToResponse(account);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception e) {
            log.error("Error while getting account", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Override
    public  ResponseEntity<List<AccountResponse>> getALlAccount() {
        log.info("Start get all account ...");

        try {

            List<AccountEntity> accounts = accountRepository.findAll();

            List<AccountResponse> responseList = accounts.stream()
                    .map(accountMapper::mapToResponse)
                    .collect(Collectors.toList());


            return  ResponseEntity.ok(responseList);


        } catch (Exception e) {
            log.error("Error while getting all accounts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity updateAccount(Long id, AccountRequest accountRequest) {
        log.info("Start update account ...");
        try {

            AccountEntity account = accountRepository.findById(id).orElse(null);

            if (account == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                if (accountRequest.getUsername() != null) {
                    account.setUsername(accountRequest.getUsername());
                }
                if (accountRequest.getPassword() != null) {
                    account.setPassword(accountRequest.getPassword());
                }

                AccountEntity newAccount = accountRepository.save(account);

                return ResponseEntity.ok(newAccount);
            }

        } catch (Exception e) {
            log.info("Have err" + e.getMessage().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi trong quá trình update dữ liệu!");
        }
    }

    @Override
    public boolean deleteAccount(List<Long> ids) {
        log.info("Start delete accounts");

        try {
            if (ids == null || ids.isEmpty()) {
                log.warn("Empty list of ids!!");
                return false;
            } else {
                accountRepository.deleteAllById(ids);
            }

            log.info("Deleted accounts with ids: {}", ids);
            return true;

        } catch (Exception e) {
            log.error("Have err: {}", e.getMessage(), e);
            return false;
        }
    }

}
