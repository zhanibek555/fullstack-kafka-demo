package com.example.fullstack.web;
import com.example.fullstack.security.JwtUtil; import lombok.Data; import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
@RestController @RequiredArgsConstructor @RequestMapping("/api/auth")
public class AuthController {
  private final AuthenticationManager am; private final JwtUtil jwt;
  @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoginRequest r){
    Authentication a=am.authenticate(new UsernamePasswordAuthenticationToken(r.getUsername(), r.getPassword()));
    return ResponseEntity.ok(new TokenResponse(jwt.generateToken(a.getName())));
  }
  @GetMapping("/me") public ResponseEntity<?> me(Authentication a){ return ResponseEntity.ok(a.getPrincipal()); }
  @Data public static class LoginRequest{ private String username; private String password; }
  @Data public static class TokenResponse{ private final String token; }
}
