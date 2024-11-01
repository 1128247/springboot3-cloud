package com.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private String serialNumber;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商家id
     */
    private Integer merchantId;

    /**
     * 菜单id
     */
    private String menuItemIds;

    /**
     * 价格共计
     */
    private BigDecimal totalAmount;

    /**
     * 地址ID
     */
    private Integer addressId;

    /**
     * 订单信息
     */
    private String orderStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
