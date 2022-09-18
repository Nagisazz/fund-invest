package com.nagisazz.fund.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvestLog {
    /**
     * 主键
     */
    private Long id;

    /**
     * 基金主键
     */
    private Long fundId;

    /**
     * 账户资产
     */
    private Double balance;

    /**
     * 净值
     */
    private Double worth;

    /**
     * 收益率
     */
    private Double yields;

    /**
     * 总收益
     */
    private Double profileAmount;

    /**
     * 总买入金额
     */
    private Double buyAmount;

    /**
     * 总卖出金额
     */
    private Double saleAmount;

    /**
     * 总投资金额
     */
    private Double investAmount;

    /**
     * 类型，1：普通日，2：定投日基准操作，3：定投日余额操作
     */
    private Integer investType;

    /**
     * 定投金额
     */
    private Double price;

    /**
     * 定投比率
     */
    private Double rate;

    /**
     * 均线金额
     */
    private Double meanAmount;

    /**
     * 振幅比率
     */
    private Double waveAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}