package com.example.fullstack.config;
import com.example.fullstack.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableMethodSecurity @RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
  @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable()); http.cors(Customizer.withDefaults());
    http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authorizeHttpRequests(auth->auth
      .requestMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
      .requestMatchers(HttpMethod.GET,"/actuator/**").permitAll()
      .anyRequest().authenticated());
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  @Bean public UserDetailsService userDetailsService(PasswordEncoder enc){
    var u=User.withUsername("admin").password(enc.encode("admin")).roles("ADMIN").build();
    return new InMemoryUserDetailsManager(u);
  }
  @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
  @Bean public AuthenticationManager authenticationManager(UserDetailsService uds, PasswordEncoder enc){
    var p=new DaoAuthenticationProvider(); p.setUserDetailsService(uds); p.setPasswordEncoder(enc);
    return new ProviderManager(p);
  }
}
