package com.main;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class Service02Application {
  public static void main(String[] args) {
    SpringApplication.run(Service02Application.class, args);
  }
}