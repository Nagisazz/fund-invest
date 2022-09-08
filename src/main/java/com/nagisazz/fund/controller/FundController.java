package com.nagisazz.fund.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagisazz.fund.service.FundService;
import com.nagisazz.fund.vo.ParamData;
import com.nagisazz.fund.vo.ResultData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FundController {

    @Autowired
    private FundService fundService;

    @GetMapping("/cal")
    public ResultData cal(@RequestParam("data") String data) {
        String[] params = data.split("#");
        ParamData paramData = ParamData.builder().code(params[0]).date(params[1]).frequence(params[2]).invest(params[3]).balance(params[4])
                .worth(params[5]).yields(params[6]).mean(params[7]).wave(params[8]).type(params[9]).build();
        if (StringUtils.isBlank(fundService.crawler(paramData.getCode()))) {
            return null;
        }
        return fundService.calculate(paramData);
    }
}
