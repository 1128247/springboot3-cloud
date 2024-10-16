package com.main.handler;

import cn.hutool.core.convert.Convert;
import com.main.entity.Users;
import com.main.utils.Result;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
@Slf4j
@Component
@RequiredArgsConstructor
public class JSONAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final RedisTemplate<String, Object> redisTemplate;
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    Users user = Convert.convert(Users.class, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    String username = user.getUsername();
    redisTemplate.opsForValue().set(username, user, Duration.ofHours(1));
    user.setPassword(null);
    String jwt = Jwts.builder().content(user.toString()).compact();
    redisTemplate.opsForValue().set(jwt, username, Duration.ofHours(1));
    log.info("登录成功");
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().println(Result.successJSONStr(jwt,"登录成功"));
  }
}
