package com.example.fullstack.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets; import java.util.Date;
@Component
public class JwtUtil {
  @Value("${security.jwt.secret}") private String secret;
  @Value("${security.jwt.expiration-seconds}") private long expSec;
  public String generateToken(String username){
    var now=new Date(); var exp=new Date(now.getTime()+expSec*1000);
    var key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(exp)
      .signWith(key, SignatureAlgorithm.HS256).compact();
  }
}
