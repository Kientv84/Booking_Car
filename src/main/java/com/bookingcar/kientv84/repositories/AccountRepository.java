package com.bookingcar.kientv84.repositories;

import com.bookingcar.kientv84.entities.AccountEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  Optional<AccountEntity> findById(Long id);

  Optional<AccountEntity> findByUsername(String username);
}
