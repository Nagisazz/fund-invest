package com.nagisazz.fund.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParamData {

    // 基金代码
    private String code;

    // 计算时间
    private String date;

    // 定投频率
    private String frequence;

    // 每次定投金额
    private String invest;

    // 账户资产
    private String balance;

    // 当前净值
    private String worth;

    // 收益率
    private String yields;

    // 参考均线天数
    private String mean;

    // 参考振幅天数
    private String wave;

    // 策略类型（1：按照定投金额卖，2：按照总资产卖，3：简单的定投）
    private String type;

}
