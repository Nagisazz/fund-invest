package com.nagisazz.fund.service;

import com.alibaba.fastjson.JSON;
import com.nagisazz.fund.vo.FundTodayInfo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

@Slf4j
@Service
public class FundInfoService {

    @Resource
    private RestTemplate restTemplate;

    @Value("${url.fund}")
    private String fundUrl;

    public String getFundName(String fundCode) {
        return getInfo(fundCode).getName();
    }

    public boolean judgeTrading(String fundCode) {
        final FundTodayInfo info = getInfo(fundCode);
        return LocalDate.now().isEqual(ChronoLocalDate.from(info.getGztime()));
    }

    public String getWorth(String fundCode) {
        return getInfo(fundCode).getGsz();
    }

    private FundTodayInfo getInfo(String fundCode) {
        String url = fundUrl.replace("{code}", fundCode) + System.currentTimeMillis();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
        byte[] bytes = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody().getBytes(StandardCharsets.ISO_8859_1);
        String res = new String(bytes, StandardCharsets.UTF_8);
        log.info("调用接口：{},返回值：{}", url, res);
        return JSON.parseObject(res.substring(8, res.length() - 2), FundTodayInfo.class);
    }
}
