package com.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("menu_items")
public class MenuItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 封面
     */
    private String cover;

    /**
     * 商家id
     */
    private Integer merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 菜名
     */
    private String name;

    private String description;

    private BigDecimal price;

    private Boolean available;

    private LocalDateTime createdAt;
}
