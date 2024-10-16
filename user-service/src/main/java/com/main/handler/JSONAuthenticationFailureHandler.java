package com.main.handler;

import com.main.utils.Result;
import com.main.utils.StatusCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JSONAuthenticationFailureHandler  implements AuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    // 设置响应的内容类型
    response.setContentType("application/json;charset=UTF-8");
    // 发送错误信息
    response.getWriter().write(Result.errorJSONStr(StatusCode.BAD_CREDENTIALS, "账号或者密码输入错误"));
  }
}
