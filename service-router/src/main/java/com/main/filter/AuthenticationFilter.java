package com.main.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.main.entity.Users;
import com.main.utils.Result;
import com.main.utils.StatusCode;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AuthenticationFilter implements WebFilter {
  @Resource
  private  RedisTemplate<String, Object> redisTemplate;
  @Resource
  private  UserDetailsService userDetailsService;
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    String requestPath = request.getPath().toString();
    log.info("requestPath: {}", requestPath);
    if(requestPath.equals("/user/login")){
     return chain.filter(exchange);
    }
    String authorization = request.getHeaders().getFirst("Authorization");
    DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();

    // 检查Authorization头是否存在
    if (StrUtil.isEmpty(authorization) || Boolean.FALSE.equals(redisTemplate.hasKey(authorization))) {
      return handleUnauthorizedResponse(response, dataBufferFactory, "登录状态已失效，请重新登录");
    }

    // 从Redis中获取用户名
    String username = Convert.convert(String.class, redisTemplate.opsForValue().get(authorization));

    // 如果用户名为空, 返回未授权的响应
    if (StrUtil.isEmpty(username)) {
      return handleUnauthorizedResponse(response, dataBufferFactory, "登录状态已失效，请重新登录");
    } else if (redisTemplate.getExpire(authorization, TimeUnit.MINUTES) < 10) {
      // 如果授权信息即将过期，则重设过期时间
      refreshExpiration(authorization, username);
    }

    // 获取用户信息
    Users user = getUserData(username);
    if (user == null) {
      return handleUnauthorizedResponse(response, dataBufferFactory, "用户信息不存在");
    }

    // 设置Spring Security的认证信息
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    // 设置SecurityContext
    return chain.filter(exchange)
        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
  }

  private Mono<Void> handleUnauthorizedResponse(ServerHttpResponse response, DataBufferFactory dataBufferFactory, String message) {
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    String responseBody = null;
    try {
      responseBody = Result.errorJSONStr(StatusCode.UNAUTHORIZED, message);
      DataBuffer dataBuffer = dataBufferFactory.wrap(responseBody.getBytes(StandardCharsets.UTF_8));
      return response.writeWith(Mono.just(dataBuffer));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      return response.setComplete();
    }
  }
  /**
   * 重设过期时间
   * @param authorization 授权信息
   * @param username 用户名
   */
  private void refreshExpiration(String authorization, String username){
    redisTemplate.expire(authorization, 1, TimeUnit.HOURS);
    redisTemplate.expire(username, 1, TimeUnit.HOURS);
  }
  private Users getUserData(String username){
    Users user = Convert.convert(Users.class, redisTemplate.opsForValue().get(username), null);
    if(user == null){
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      return (Users) userDetails;
    }
    return user;
  }
}
