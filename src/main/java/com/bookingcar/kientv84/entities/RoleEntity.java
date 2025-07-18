package com.bookingcar.kientv84.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "role")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
  @Id private String name;
}
