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
public class ResultData {

    private String all;

    private String rate;

    private String price;

    private String mean;

    private String wave;

    private String lastYield;
}
