package com.main.controller;

import com.main.entity.Users;
import com.main.mapper.UsersMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Service02Controller {

  @Resource
  private UsersMapper usersMapper;
  @GetMapping("/data")
  public String getData() {
    return "Service02 data from Service01";
  }

  @GetMapping( "/test")
  public Users test(){
    Users users = usersMapper.selectById(1);
    log.info("users:{}", users);
    return users;
  }
}
