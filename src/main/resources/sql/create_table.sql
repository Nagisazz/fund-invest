CREATE TABLE `t_fund_info` (
                               `n_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `c_name` varchar(100) DEFAULT NULL COMMENT '基金名称',
                               `c_code` varchar(100) DEFAULT NULL COMMENT '基金代码',
                               `n_balance` double DEFAULT NULL COMMENT '账户资产',
                               `n_worth` double DEFAULT NULL COMMENT '净值',
                               `n_yields` double DEFAULT NULL COMMENT '收益率',
                               `n_profile_amount` double DEFAULT NULL COMMENT '总收益',
                               `n_buy_amount` double DEFAULT NULL COMMENT '总买入金额',
                               `n_sale_amount` double DEFAULT NULL COMMENT '总卖出金额',
                               `n_invest_amount` double DEFAULT NULL COMMENT '总投资金额',
                               `n_invest_num` int DEFAULT NULL COMMENT '定投次数',
                               `n_frequence` int DEFAULT NULL COMMENT '定投频率',
                               `n_invest_money` int DEFAULT NULL COMMENT '定投基准金额',
                               `n_mean` int DEFAULT NULL COMMENT '参考均线天数',
                               `n_wave` int DEFAULT NULL COMMENT '参考振幅天数',
                               `n_type` int DEFAULT NULL COMMENT '策略类型',
                               `n_valid` int DEFAULT NULL COMMENT '是否有效，0否1是',
                               `dt_create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `dt_update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               PRIMARY KEY (`n_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `t_invest_log` (
                                `n_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `n_fund_id` bigint DEFAULT NULL COMMENT '基金主键',
                                `n_balance` double DEFAULT NULL COMMENT '账户资产',
                                `n_worth` double DEFAULT NULL COMMENT '净值',
                                `n_yields` double DEFAULT NULL COMMENT '收益率',
                                `n_profile_amount` double DEFAULT NULL COMMENT '总收益',
                                `n_buy_amount` double DEFAULT NULL COMMENT '总买入金额',
                                `n_sale_amount` double DEFAULT NULL COMMENT '总卖出金额',
                                `n_invest_amount` double DEFAULT NULL COMMENT '总投资金额',
                                `n_invest_type` int DEFAULT NULL COMMENT '类型，1：普通日，2：定投日基准操作，3：定投日余额操作',
                                `n_price` double DEFAULT NULL COMMENT '定投金额',
                                `n_rate` double DEFAULT NULL COMMENT '定投比率',
                                `n_mean` double DEFAULT NULL COMMENT '均线金额',
                                `n_wave` double DEFAULT NULL COMMENT '振幅比率',
                                `dt_create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `dt_update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`n_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;