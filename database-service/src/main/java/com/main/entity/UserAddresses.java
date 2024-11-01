package com.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("user_addresses")
public class UserAddresses implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地址ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 收件人的姓名
     */
    private String recipientName;

    private String phone;

    private String address;

    /**
     * 是否是默认地址
     */
    private Boolean isDefault;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
