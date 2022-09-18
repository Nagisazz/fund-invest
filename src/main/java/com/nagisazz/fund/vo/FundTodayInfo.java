package com.nagisazz.fund.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FundTodayInfo {

    private String fundcode;

    private String name;

    private LocalDate jzrq;

    private String dwjz;

    private String gsz;

    private String gszzl;

    private LocalDateTime gztime;
}
