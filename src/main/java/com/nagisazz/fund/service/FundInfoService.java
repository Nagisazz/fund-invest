package com.nagisazz.fund.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FundInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.fund}")
    private String fundUrl;

    public String getFundName(String fundCode) {
        fundUrl = fundUrl.replace("{code}", fundCode) + System.currentTimeMillis();
        String res = restTemplate.getForObject(fundUrl, String.class);
        return res;
    }

    public static void main(String[] args) {
        String str = "jsonpgz({\"fundcode\":\"161725\",\"name\":\"撒大声地(LOF)A\",\"jzrq\":\"2022-09-13\",\"dwjz\":\"1.1232\",\"gsz\":\"1.1117\",\"gszzl\":\"-1.03\",\"gztime\":\"2022-09-14 15:00\"});";
        String regex1 = "name\":\"(.*)\",\"jzrq\"";
        //编译正则表达式成为一个匹配的对象
        Pattern pattern1 = Pattern.compile(regex1);
        //通过匹配规则对象得到一个匹配数据内容的匹配器对象
        Matcher matcher1 = pattern1.matcher(str);
        //通过匹配器去内容中爬取信息
        while(matcher1.find()) {
            System.out.println(matcher1.group());
        }
    }


}
