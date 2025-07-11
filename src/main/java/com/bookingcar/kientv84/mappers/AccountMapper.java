package com.bookingcar.kientv84.mappers;

import com.bookingcar.kientv84.dtos.requests.AccountRequest;
import com.bookingcar.kientv84.dtos.respones.AccountResponse;
import com.bookingcar.kientv84.entities.AccountEntity;
import com.bookingcar.kientv84.entities.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity map(AccountRequest request);

    AccountResponse mapToResponse(AccountEntity entity);

    Set<RoleEntity> map(List<String> roleNames);

    default RoleEntity map(String name) { return RoleEntity.builder().name(name).build();
    }
}
