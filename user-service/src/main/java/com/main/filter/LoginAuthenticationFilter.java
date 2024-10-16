package com.main.filter;

import com.main.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    // 使用父类方法来获取用户名
    String username = obtainUsername(request);
    // 使用父类方法来获取密码
    String password = obtainPassword(request);
    String ip = IpUtils.getIp(request);
    log.info("用户名: " + username + ", IP为: " + ip + "，的用户请求登录接口");
    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
    setDetails(request, authRequest);
    // 认证管理器进行认证
    return this.getAuthenticationManager().authenticate(authRequest);
  }
}
