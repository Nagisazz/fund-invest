package com.nagisazz.fund.service;

import com.alibaba.fastjson.JSON;
import com.nagisazz.fund.vo.FundTodayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Slf4j
@Service
public class MsgService {

    @Value("${pushplus.url}")
    private String url;

    @Value("${pushplus.token}")
    private String token;

    @Value("${pushplus.groupId}")
    private String groupId;

    @Autowired
    private RestTemplate restTemplate;

    public void sendMsg(String title, String content) {
        String sendMsgUrl = MessageFormat.format(url, token, title, content, groupId);
        restTemplate.getForObject(sendMsgUrl, String.class);
    }
}
