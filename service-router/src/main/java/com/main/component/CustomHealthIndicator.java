package com.main.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {
  @Override
  public Health health() {
    return Health.status(Status.UP).up().withDetail("Custom check", "Service is healthy").build();
  }
}
