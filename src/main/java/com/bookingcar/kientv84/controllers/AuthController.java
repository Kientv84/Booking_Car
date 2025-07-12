package com.bookingcar.kientv84.controllers;

import com.bookingcar.kientv84.components.JwtTokenProvider;
import com.bookingcar.kientv84.dtos.requests.AuthRequest;
import com.bookingcar.kientv84.dtos.respones.AuthResponse;
import com.bookingcar.kientv84.dtos.respones.RefreshTokenResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/auths")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        log.info("Xac thuc nguoi dung");
        Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );


        log.info("Lay thong tin nguoi dung vao role");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        log.info("Tao jwt tu username va role");
        String token = jwtTokenProvider.generateToken(userDetails.getUsername(), roles);
        return ResponseEntity.ok(new AuthResponse(token));
    }


    @PostMapping("/refresh")
    public  ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenResponse request) {
        String oldToken = request.getToken();

        if(!jwtTokenProvider.validateToken(oldToken)){
            return  ResponseEntity.badRequest().build();
        }

        String username = jwtTokenProvider.getUsername(oldToken);
        List<String> roles = jwtTokenProvider.getRoles(oldToken);
        String newToken = jwtTokenProvider.generateToken(username, roles);
        return ResponseEntity.ok(new RefreshTokenResponse(newToken));
    }
}
