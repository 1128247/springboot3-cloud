package com.main.configuration;

import com.main.filter.LoginAuthenticationFilter;
import com.main.handler.JSONAuthenticationFailureHandler;
import com.main.handler.JSONAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final UserDetailsService userDetailsService;
  private final JSONAuthenticationSuccessHandler successHandler;

  private final JSONAuthenticationFailureHandler failureHandler;

  @Bean
  public AuthenticationManager authenticationManagerBean(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(List.of(provider));
  }
  @Bean
  AuthenticationFilter authenticationFilter() {
    return new AuthenticationFilter();
  }
  @Bean
  public LoginAuthenticationFilter loginAuthenticationFilter(AuthenticationManager authenticationManager) {
    LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(authenticationManager);
    loginAuthenticationFilter.setFilterProcessesUrl("/login");
    loginAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
    loginAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
    return loginAuthenticationFilter;
  }
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.addAllowedOriginPattern("*");
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setMaxAge(3600L); // 1 hour
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                          LoginAuthenticationFilter loginAuthenticationFilter, AuthenticationFilter authenticationFilter) throws Exception {

    Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestsCustomizer =
        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
            .requestMatchers("/", "/login")
            .permitAll()
            .anyRequest()
            .authenticated();
    return httpSecurity
//        .addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .requestCache(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeHttpRequestsCustomizer)
        .sessionManagement(AbstractHttpConfigurer::disable)
        .anonymous(AbstractHttpConfigurer::disable)
        .build();
  }

}
