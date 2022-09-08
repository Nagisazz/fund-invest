package com.nagisazz.fund.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.nagisazz.fund.vo.ParamData;
import com.nagisazz.fund.vo.ResultData;

@Slf4j
@Service
public class FundService {

    public String crawler(String code) {
        Process proc;
        StringBuilder res = new StringBuilder();
        StringBuilder resErr = new StringBuilder();
        String tmpErr = "";
        String tmp = "";
        try {
            String[] param = new String[]{"python",
                    "/nagisa/invest/CrawlerMain.py", code};
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
        log.info("crawler返回结果：{}", res);
        log.info("crawler失败结果：{}", resErr);
        return String.valueOf(res);
    }

    public ResultData calculate(ParamData paramData) {
        Process proc;
        StringBuilder res = new StringBuilder();
        StringBuilder resErr = new StringBuilder();
        String tmpErr = "";
        String tmp = "";
        try {
            String[] param = new String[]{"python",
                    "/nagisa/invest/DecisionMain.py", "-c" + paramData.getCode(), "-d" + paramData.getDate(),
                    "-f" + paramData.getFrequence(), "-i" + paramData.getInvest(), "-b" + paramData.getBalance(), "-w" + paramData.getWorth(),
                    "-y" + paramData.getYields(), "-m" + paramData.getMean(), "-v" + paramData.getWave(), "-t" + paramData.getType()};
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
        log.info("calculate返回结果：{}", res);
        log.info("calculate失败结果：{}", resErr);

        String[] data = String.valueOf(res).split(" ");
        ResultData resultData = ResultData.builder().all(data[0]).rate(data[1]).price(data[2]).mean(data[3]).wave(data[4]).lastYield(data[5]).build();
        log.info("calculate返回封装结果：{}", resultData);
        return resultData;
    }
}
