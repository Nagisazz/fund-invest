package com.nagisazz.fund.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FundInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 基金名称
     */
    private String name;

    /**
     * 基金代码
     */
    private String code;

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
     * 定投次数
     */
    private Integer investNum;

    /**
     * 定投频率
     */
    private Integer frequence;

    /**
     * 定投基准金额
     */
    private Integer investMoney;

    /**
     * 参考均线天数
     */
    private Integer mean;

    /**
     * 参考振幅天数
     */
    private Integer wave;

    /**
     * 策略类型
     */
    private Integer type;

    /**
     * 是否有效，0否1是
     */
    private Integer valid;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 上次定投日期
     */
    private LocalDateTime lastInvestTime;
}