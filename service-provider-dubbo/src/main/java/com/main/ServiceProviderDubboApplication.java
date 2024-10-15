package com.main;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@MapperScan("com.main.mapper")
@SpringBootApplication
public class ServiceProviderDubboApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServiceProviderDubboApplication.class, args);
  }
}