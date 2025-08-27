package com.example.fullstack.config;
import org.springframework.context.annotation.*; import org.springframework.web.cors.*; import org.springframework.web.filter.CorsFilter; import java.util.List;
@Configuration
public class CorsConfig {
  @Bean public CorsFilter corsFilter(){
    var cfg=new CorsConfiguration(); cfg.setAllowCredentials(true);
    cfg.setAllowedOrigins(List.of("http://localhost:5173")); cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS")); cfg.setAllowedHeaders(List.of("*"));
    var src=new UrlBasedCorsConfigurationSource(); src.registerCorsConfiguration("/**", cfg); return new CorsFilter(src);
  }
}
