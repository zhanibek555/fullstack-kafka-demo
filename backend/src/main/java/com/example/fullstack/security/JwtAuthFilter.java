package com.example.fullstack.security;
import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*; import jakarta.servlet.http.*; import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.OncePerRequestFilter; import org.springframework.stereotype.Component;
import java.io.IOException; import java.nio.charset.StandardCharsets; import java.util.Date;
@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  @Value("${security.jwt.secret}") private String secret;
  private final UserDetailsService userDetailsService;
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc)
      throws ServletException, IOException {
    String a=req.getHeader("Authorization");
    if(a!=null && a.startsWith("Bearer ")) {
      var token=a.substring(7);
      try{
        var key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Claims c=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String user=c.getSubject(); Date exp=c.getExpiration();
        if(user!=null && exp!=null && exp.after(new Date())){
          UserDetails ud=userDetailsService.loadUserByUsername(user);
          var auth=new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      }catch(Exception ignore){}
    }
    fc.doFilter(req,res);
  }
}
