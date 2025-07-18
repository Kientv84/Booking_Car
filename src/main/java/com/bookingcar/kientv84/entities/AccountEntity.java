package com.bookingcar.kientv84.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "account")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "account.username.notBlank")
  @Column(nullable = true)
  private String username;

  @NotBlank(message = "account.password.notBlank")
  private String password;

  // @NotBlank
  private String email;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "account_roles",
      joinColumns = @JoinColumn(name = "account_id"),
      inverseJoinColumns = @JoinColumn(name = "role_name"))
  private Set<RoleEntity> roles = new HashSet<>();
}
