package com.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.main.entity.Roles;
import com.main.entity.Users;
import com.main.mapper.RolesMapper;
import com.main.mapper.UsersMapper;
import com.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UsersMapper usersMapper;
  private final RolesMapper rolesMapper;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(Users::getUsername, username);
    Users users = usersMapper.selectOne(lambdaQueryWrapper);
    if (users == null) {
      throw new UsernameNotFoundException("用户不存在");
    }
    List<Roles> roles = rolesMapper.selectRolesByUserId(users.getId());
    users.setRoles(roles);
    return users;
  }
}
