package com.bookingcar.kientv84.services.impls;

import com.bookingcar.kientv84.repositories.AccountRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user =
        accountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    List<GrantedAuthority>
        authorityList = // GrantedAuthority là một phần của spring security để biểu diễn một quyền
            // (authority)
            // mà người dùng đang có

            // GrantedAuthority giúp spring security biết người dùng đang có những quyền gì để thực
            // hiện các thao tác truy cập
            user.getRoles().stream()
                .map(
                    role ->
                        new SimpleGrantedAuthority(
                            (role.getName()))) // SimpleGrantedAuthority là một implement đươn giản
                // cho gratedAuthority
                // trong đó mỗi quyền được biểu diễn dưới dạng một chuỗi văn bảng.
                .collect(
                    Collectors
                        .toList()); // .collect() // thu thập lại tất cả các SimpleGratedAuthority
    // Collectors.toList() là môột thành phâ cu api stream java, được dùng để biến đổi một stream
    // thành list

    return new User(user.getUsername(), user.getPassword(), authorityList);
  }
}
