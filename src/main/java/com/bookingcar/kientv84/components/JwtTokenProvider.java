package com.bookingcar.kientv84.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

/**
 * JwtTokenProvider giúp tạo JWT khi user login thành công Lấy ra thông tin cuủa token Kiểm tra tính
 * hợp lệ của token
 *
 * <p>JWT gồm <HEADER><PAYLOAD><SIGNATURE>
 */
@Component // Anotation đánh dấu một class là một Bean giúp spring container nhận biết và inject
// @Autowired để sử dụng
public class JwtTokenProvider {

  private static final String SECRET_KEY = "my-super-secret-jwt-key-123456789012345"; // Secret
  // key bí mật giúp hạn chế việc hacker lấy được token
  // Sử dụng chuỗi ít nhất 32 ký tự dùng để HMAC-SHA

  private static final long EXPIRATION_TIME = 3000; // Expiration time thời gian token hết hạn

  private static final String ROLES = "roles"; // tên key để lưu list roles

  private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Tạo key

  /**
   * Hàm lại generateToken dùng để tạo ra token, arguments dùng để tạo token ở đây là username và
   * list role PAYLOAD gồm ( username, roles, iat, exp, ..) SIGNATURE( SECRET_KEY )
   */
  public String generateToken(String username, List<String> roles) {
    return Jwts
        .builder() // thì để khỏi tạo ta gọi Jwts từ thư viện jsonwebtoken .builder() để trả về một
        // object JwtBuilder hoàn chỉnh, sau đó cho phép...
        .subject(username) // .subject() gán thông tin, gán username
        .claim("roles", roles) // .claim(..,..) Đặt các claims (dữ liệu đính kèm)
        .issuedAt(new Date()) // .isuseAt() Thời gian phát hành token
        .expiration(
            new Date(
                System.currentTimeMillis() + EXPIRATION_TIME)) // .expiration gán thời gian hết hạn
        .signWith(key) // Ký bằng key bí mật (HMAC-SHA256)
        .compact(); // cuối cùng trả về JWT hoàn chỉnh
  }

  /** Hàm getClaims giải mã JWT token và lấy phần payload ( username, roles, iat, exp, ..) */
  private Claims getClaims(String token) { // Nhận vào token, Claim là một tập hợp các dữ liệu
    //(metadata, thông tin người dùng, quyền hạn, thời gian hết hạn,...) được đính kèm trong JWT.
    return Jwts
        .parser() // .parser, parser có nghĩa là phân tích cú pháp, vậy Jwts.parser có nghĩa là bắt
        // đầu tạo parser để đọc JWT.
        .verifyWith((SecretKey) key) // để xác minh chữ ký SECRET KEY
        .build()
        .parseSignedClaims(token) // 	Giải mã token
        .getPayload(); // lấy ra payload
  }

  /** Hàm getUsername từ token gọi getClaims để lấy ra subject cụ thể là username. */
  public String getUsername(String token) {
    return getClaims(token).getSubject();
  }

  /**
   * Hàm getRoles nhận vào token trả về object gồm các roles, gọi hàm getClaims truyền vào token và
   * lấy ra roles theo key là ROLES Chuyển đổi roles từ dạng Object thành List<String>. Nếu roles là
   * một List → thì duyệt từng phần tử, ép về String. Nếu không phải List → trả về danh sách rỗng.
   */
  public List<String> getRoles(String token) {
    Object roles = getClaims(token).get(ROLES);
    return roles instanceof List<?>
        ? (((List<?>) roles).stream()).map(Object::toString).toList() //<?> gọi là wildcard trong generic thể hiện bất kỳ kiểu dữ liệu nào vd (String, interger, ...)
        : List.of();
    // Biểu thức điều kiện 3 ngôi (ternary operator) condition ? valueIfTrue : valueIfFalse;
  }

  /** Hàm validateToken Kiểm tra token có hợp lệ hay không. */
  public boolean validateToken(String token) {
    try {
      getClaims((token));
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
