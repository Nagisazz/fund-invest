package com.nagisazz.fund;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @auther zhushengzhe
 * @date 2022/09/04 16:02
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.nagisazz.fund.dao")
public class FundInvestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundInvestApplication.class, args);
    }
}
