package com.main.configuration;

import com.main.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  @Bean
  public AuthenticationFilter authenticationFilter() {
    return new AuthenticationFilter();
  }

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity, AuthenticationFilter authenticationFilter) {
    return httpSecurity.addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) .authorizeExchange(
            authorizeExchange -> {
              authorizeExchange
                  .pathMatchers("/actuator/**", "/user/data", "/user/login")
                  .permitAll()
                  .anyExchange()
                  .authenticated();
            }).csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)
        .build();
  }
}
