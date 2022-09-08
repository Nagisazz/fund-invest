package com.nagisazz.fund.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class FundService {

    public String crawler() {
        Process proc;
        StringBuilder res = new StringBuilder();
        StringBuilder resErr = new StringBuilder();
        String tmpErr = "";
        String tmp = "";
        try {
//            String absolutePath = ResourceUtils.getFile("classpath:investment-plan/DecisionMain.py").getAbsolutePath();
//            System.out.println(absolutePath);
//            String[] param = new String[]{"python",
//                    "/nagisa/invest/DecisionMain.py", "-c 161725", "-d 20190821", "-f 7", "-i 3000", "-b 7813", "-w 0.9738", "-y 0.053", "-m 250", "-v 5", "-t 2"};
            String[] param = new String[]{"python",
                    "/nagisa/invest/CrawlerMain.py", "161725"};
            proc = Runtime.getRuntime().exec(param);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            while ((tmp = in.readLine()) != null) {
                res.append(tmp);
            }
            in.close();
            BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));
            while ((tmpErr = err.readLine()) != null) {
                resErr.append(tmpErr);
            }
            err.close();
            proc.waitFor();
        } catch (Exception e) {
            log.error("计算失败", e);
            e.printStackTrace();
        }
        log.info("返回结果：{}", res);
        log.info("失败结果：{}", resErr);
        return String.valueOf(res);
    }

    public String calculate() {
        Process proc;
        StringBuilder res = new StringBuilder();
        StringBuilder resErr = new StringBuilder();
        String tmpErr = "";
        String tmp = "";
        try {
//            String absolutePath = ResourceUtils.getFile("classpath:investment-plan/DecisionMain.py").getAbsolutePath();
//            System.out.println(absolutePath);
            String[] param = new String[]{"python",
                    "/nagisa/invest/DecisionMain.py", "-c161725", "-d20190821", "-f7", "-i3000", "-b7813", "-w0.9738", "-y0.053", "-m250", "-v5", "-t2"};
            proc = Runtime.getRuntime().exec(param);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            while ((tmp = in.readLine()) != null) {
                res.append(tmp);
            }
            in.close();
            BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream(), StandardCharsets.UTF_8));
            while ((tmpErr = err.readLine()) != null) {
                resErr.append(tmpErr);
            }
            err.close();
            proc.waitFor();
        } catch (Exception e) {
            log.error("计算失败", e);
            e.printStackTrace();
        }
        log.info("返回结果：{}", res);
        log.info("失败结果：{}", resErr);
        return String.valueOf(res);
    }
}
