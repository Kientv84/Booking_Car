package com.bookingcar.kientv84.configs;

import com.bookingcar.kientv84.components.JwtAuthenticationFilter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * dependency spring security giúp tạo ra class config giúp chúng ta có thể bảo mật bằng cách, - xác
 * thực login, mã hóa mật khẩu - phần quyền truy cập cho các API - Cấu hình JWT filter xử lý các
 * request HTTP. - Đưa JWT filter để kiểm tra token trong các request gửi đi
 */
@AllArgsConstructor
@Configuration // Đánh dấu đây là một class được config . Khi chạy spring sẽ quét,
// các class có anotation là config và sẽ thực thi các bean bên trong

public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * Khai báo phương thức là một Bean để spring container biết và sẽ được inject và sử dụng,
   * anotation @Bean Spring sẽ thực thi phương thức này một lần duy nhất và lưu đối tượng trả về.
   * AuthenticationManager là một interface chủ đạo của spring security dùng để quản lý xác thực tài
   * khoản
   */
  @Bean
  public AuthenticationManager authorizationManager(AuthenticationConfiguration configuration)
      throws Exception {
    // configuration là một ojbect có sẵn trong spring security, nó cấu hình sẵn các (
    // UserDetailsService, PasswordEncoder, ... )

    return configuration.getAuthenticationManager();

    // gọi và lấy ra getAuthenticationManager để sử dụng,
    // vậy lúc này sau khi inject private AuthenticationManager authenticationManager thì sẽ gọi
    // được các method của authenticationManager
    // vd authenticationManager.authenticate(
    //        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    //    );
  }

  @Bean // Đánh dấu PasswordEncoder là một Bean dùng để Inject và sử dụng khi mã hóa mật khẩu
  // vd      String encodePassword = passwordEncoder.encode(accountRequest.getPassword());
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Không để gì mặt định encode mới mã là 10
  }

  /**
   * Security filter chain cấu hình giúp Sprin biết: - API nào được truy cập tự do - API nào cần
   * được xác thực - Cần dùng JWT không? - thêm các filter tùy chỉnh cho xác thực
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
      throws Exception { // Nhận vào request cần bảo mật
    return httpSecurity
        .cors(Customizer.withDefaults()) // Thêm cross
        .csrf(
            AbstractHttpConfigurer
                ::disable) // Cờ csrf (Cross- site request forgery) là cấu hình bảo mật khi dùng với
        // form hoặc session,
        // do chúng ta đang sử dụng JWT(stateless) nên disable đi ... tránh lỗi 403 khi gọi API từ
        // client (như Postman hay FE React).

        // Khi không sử dụng JWT thì bình thường spring sẽ sử dụng session based authentication, Khi
        // login thành công thì spring sẽ lưu lại
        // Session id đó, ở phía server thì lưu trong gam hoặc cache, phía client thì lưu vào
        // cookie,
        // Thì sau khi mỗi lần gửi request thì spring sẽ kiểm tra session Id đó đã tồn tại chưa và
        // nếu chưa thì xác định là chưa đăng nhập.
        // ==> sử dụng STATEFUL, mà STATEFUL thì tối RAM để lưu và không phù hợp khi sử dụng
        // MICROSERVICE hoặc CLOUD

        .sessionManagement(
            sm ->
                sm.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)) // Khi dùng JWT, bạn phải dùng STATELESS
        // cấu hình không sử dụng session, mà mỗi request sẽ cần JWT Token.
        // Tôi không dùng session mà mỗi request đều là độc lập và cần xác thực thông qua JWT token
        // đi kèm
        // Research thêm: Vì sao bạn phải dùng STATELESS khi dùng JWT?,  So sánh trực quan: giữa
        // STATEFULL và STATELESS

        .authorizeHttpRequests(
            ath ->
                ath // bạn cấu hình các route/API nào
                    .requestMatchers("/actuator/health")
                    .permitAll() // Cho phép truy cập công khai
                    .requestMatchers("/api/v1/auths/login")
                    .permitAll()
                    .requestMatchers("/api/v1/register")
                    .permitAll()
                    .requestMatchers("/api/v1/accounts")
                    .permitAll()
                    .requestMatchers("/api/v1/account/*")
                    .permitAll()
                    .requestMatchers("/api/v1/account/update/*")
                    .permitAll()
                    .requestMatchers("/api/v1/accounts/delete")
                    .permitAll()
                    .requestMatchers("/api/v1/refresh")
                    .permitAll()
                    .anyRequest()
                    .authenticated() // Cần phải có token

            // “Trước khi xử lý xác thực username/password như mặc định, hãy chạy qua filter kiểm
            // tra JWT token của tôi trước!”
            )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build(); // Sau khi cấu hình xong hết thì gọi .build() để trả về một SecurityFilterChain
    // hoàn chỉnh cho spring
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("*")); // hoặc dùng List.of("http://localhost:8080")
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
