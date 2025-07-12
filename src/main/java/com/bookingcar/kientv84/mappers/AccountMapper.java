package com.bookingcar.kientv84.mappers;

import com.bookingcar.kientv84.entities.AccountEntity;
import com.bookingcar.kientv84.entities.RoleEntity;
import com.example.model.Account;
import com.example.model.AccountRequest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity map(AccountRequest request);

    Account mapToAccountModel(AccountEntity accountEntity);

    Account mapToResponse(AccountEntity entity);

    Set<RoleEntity> map(List<String> roleNames);

    default RoleEntity map(String name) { return RoleEntity.builder().name(name).build();
    }
}
