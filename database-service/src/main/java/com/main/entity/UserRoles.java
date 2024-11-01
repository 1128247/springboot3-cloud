package com.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Andres
 * @since 2024-11-01
 */
@Getter
@Setter
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;
}
