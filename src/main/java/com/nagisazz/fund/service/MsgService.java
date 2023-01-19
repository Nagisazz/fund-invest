package com.nagisazz.fund.service;

import com.nagisazz.base.util.RestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private RestHelper restHelper;

    public void sendMsg(String title, String content) {
        String sendMsgUrl = MessageFormat.format(url, token, title, content, groupId);
        restHelper.get(sendMsgUrl);
    }
}
