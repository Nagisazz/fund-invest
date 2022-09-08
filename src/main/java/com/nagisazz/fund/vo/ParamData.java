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

    private String code;

    private String date;

    private String frequence;

    private String invest;

    private String balance;

    private String worth;

    private String yields;

    private String mean;

    private String wave;

    private String type;

}
