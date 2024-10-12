package com.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service02Controller {

  @GetMapping("/data")
  public String getData() {
    return "Service02 data from Service01";
  }
}
