package com.nagisazz.fund.schedule;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FundTask {

    // 定时任务1：周一到周五23点更新t_fund_info表中所有有效数据
    // 逻辑：判断当天是否是交易日
    // 逻辑：按照日期查询t_invest_log，如果有数据说明当天有定投，则更新t_invest_log数据，并且计算定投相关变化

    // 定时任务2：周一到周五14点50计算t_fund_info表中所有有效数据的定投
    // 逻辑：判断当天是否是交易日
    // 逻辑：查询t_invest_log表中上一个定投日距今天数是否超过定投频率，如果超过则进行定投操作
    // 逻辑：插入t_invest_log记录，包括定投金额及定投比率
}
