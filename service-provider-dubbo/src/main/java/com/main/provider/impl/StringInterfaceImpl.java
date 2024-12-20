package com.main.provider.impl;

import com.main.rpc.StringInterface;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(registry = "service-provider-dubbo-8002")
public class StringInterfaceImpl implements StringInterface {
  @Override
  public String getData() {
    return "getData from StringInterfaceImpl";
  }
}
