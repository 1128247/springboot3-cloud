package com.main.controller;

import com.main.rpc.StringInterface;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service01Controller {

  @DubboReference
  private StringInterface stringInterface;

  @GetMapping("/data")
  public String getData() {
    return stringInterface.getData();
  }
}
