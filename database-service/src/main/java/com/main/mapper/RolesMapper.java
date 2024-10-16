package com.main.mapper;

import com.main.entity.Roles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Your Name
 * @since 2024-10-15
 */
public interface RolesMapper extends BaseMapper<Roles> {

  List<Roles> selectRolesByUserId(Integer id);
}
