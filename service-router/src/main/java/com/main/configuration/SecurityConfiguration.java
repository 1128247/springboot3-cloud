package com.main.configuration;

import com.main.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  @Bean
  public AuthenticationFilter authenticationFilter() {
    return new AuthenticationFilter();
  }

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity, AuthenticationFilter authenticationFilter, CorsConfigurationSource corsConfigurationSource) {
    return httpSecurity.addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION).authorizeExchange(
            authorizeExchange -> authorizeExchange
                .pathMatchers("/actuator/**", "/user/data", "/user/login")
                .permitAll()
                .anyExchange()
                .authenticated()).csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource))
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern("*"); // 允许所有源
    configuration.addAllowedMethod("*"); // 允许所有方法
    configuration.addAllowedHeader("*"); // 允许所有头部
    configuration.setAllowCredentials(true); // 允许凭证
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
