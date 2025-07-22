package com.bookingcar.kientv84.components;

import com.bookingcar.kientv84.services.impls.CustomUserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Đây là một class cấu hình JWT filter tùy chỉnh. Nhận vào request gửi đến sever Với nhiệm vụ là:
 * Kiểm tra xem token có hợp lệ không. Nếu token hợp lệ, thì truy xuất thông tin người dùng từ token
 * đó. Xác thực người dùng đó và lưu vào hệ thống, security context của spring (security context)
 *
 * <p>Vậy tại sao cần phải kiểm tra token trước, vì ở securityFilterChain ta cấu hình STATELESS =>
 * Mỗi request độc lập cần có token kèm theo nên cần phải xác thực token đó
 */
@RequiredArgsConstructor // Tạo các constructor với tất cả các trường final để giúp khởi tạo đối
// tượng bởi dependency sẽ được inject tự động khi tạo bean
@Component // Đánh dấu class là một component giúp cho spring có thể quản lý như một bean và có thể
// inject và sử dụng.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  // Kế thừa class OncePerRequestFilter dùng để Chạy một lần duy nhất cho mỗi request.
  // Thường dùng để kiểm tra token hoặc log request.

  private final JwtTokenProvider jwtTokenProvider;

  private final CustomUserDetailServiceImpl userDetailService;

  /**
   * Đây là một filter tùy chỉnh kế thừa từ OncePerRequestFilter, chạy một lần cho mỗi request. Được
   * gọi cho mỗi request. Đây là nơi bạn sẽ thêm logic để kiểm tra JWT.
   * UsernamePasswordAuthenticationToken: (Tạo một token xác thực mới với thông tin người dùng đã
   * tải, quyền hạn (authorities) từ UserDetails.) Lắng nghe và kiểm tra mỗi request: Filter sẽ chạy
   * một lần cho mỗi request khách hàng, kiểm tra xem JWT có hợp lệ không.
   *
   * <p>Sử dụng JWT để xác thực và quyền hạn người dùng: Nếu hợp lệ, token nhớ quyền của người dùng
   * sẽ được lưu
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization"); // Khởi tạo header với key là Authorization

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7); // cắt chuỗi từ ký tự số 7 để lấy toàn bộ token
      if (jwtTokenProvider.validateToken(token)) {
        String username = jwtTokenProvider.getUsername(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails
                    .getAuthorities()); // credentials là null bởi vì xác thực đã được thực hiện
        // thông qua JWT,
        // không cần thông tin đăng nhập thêm trong ngữ cảnh này.

        authenticationToken.setDetails(
            new WebAuthenticationDetailsSource()
                .buildDetails(request)); // Gắn thêm thông tin bổ sung của request\
        // vào token xác thực (ví dụ địa chỉ IP, session, ...).

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // Dòng lệnh này đặt đối tượng Authentication vào SecurityContext, nơi mà Spring Security
        // lưu trữ thông tin xác thực của người dùng hiện tại cho thread đang chạy.
        // Spring Security sẽ sử dụng thông tin trong SecurityContext để xác định người dùng hiện
        // đang đăng nhập khi xử lý các yêu cầu tiếp theo trong cùng phiên.
      }
    }
    filterChain.doFilter(
        request,
        response); // Gọi tiếp chuỗi filter tiếp theo trong filter chain, đảm bảo các filter khác
    // cũng được chạy.
  }
}
