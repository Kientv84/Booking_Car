package com.bookingcar.kientv84.mappers;

import com.bookingcar.kientv84.entities.AccountEntity;
import com.bookingcar.kientv84.entities.RoleEntity;
import com.example.model.Account;
import com.example.model.AccountRequest;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;

/**
 * Khai báo class AccountMapper từ mapstruct, mapstruct sẽ cung cấp các anotation
 * như @Mapper, @Mapping, ... Nếu một class có khai báo anotation là Mapper thì lúc này
 * mapstruct-professor sẽ tự động generate code vào trong folder
 * target/generated-sources/annotations/ Mapstruct map như thế nào? --> Mapstruct sử dụng name
 * convention để map tên field giống nhau
 */
@Mapper(
    componentModel =
        "spring") // Khai báo thêm componentModel = Spring đồng nghĩa việc khai báo class là một
// Component là một bean dùng để inject sd
public interface AccountMapper {

  AccountEntity map(AccountRequest request);

  Account mapToAccountModel(AccountEntity accountEntity);

  Account mapToResponse(AccountEntity entity);

  Set<RoleEntity> map(List<String> roleNames);

  default RoleEntity map(String name) {
    return RoleEntity.builder().name(name).build();
  }
}

// 1. Bạn viết interface có @Mapper
// 2. Compiler gọi mapstruct-processor khi build project
// 3. mapstruct-processor phân tích @Mapper và các method
// 4. Generate code .java file tương ứng (Impl)
// 5. Nếu dùng componentModel = "spring" → @Component sẽ tự đăng ký Bean cho Spring
