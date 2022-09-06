package com.nagisazz.fund.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagisazz.fund.service.FundService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FundController {

    @Autowired
    private FundService fundService;

    @GetMapping("/test")
    public String test(){
        return fundService.calculate();
    }
}
