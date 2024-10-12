package com.main.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {
  @Override
  public Health health() {
    // 检查某些条件，返回相应的 Health 状态
    return Health.up().withDetail("Custom check", "Service is healthy").build();
  }
}
