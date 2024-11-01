package com.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Merchants implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String phone;

    private String address;

    /**
     * 商家封面
     */
    private String cover;

    private LocalDateTime createdAt;
}
