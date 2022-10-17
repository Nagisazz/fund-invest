package com.nagisazz.fund.service;

import com.alibaba.fastjson.JSONObject;
import com.nagisazz.fund.dao.FundInfoExtendMapper;
import com.nagisazz.fund.dao.base.InvestLogMapper;
import com.nagisazz.fund.entity.FundInfo;
import com.nagisazz.fund.entity.InvestLog;
import com.nagisazz.fund.vo.ParamData;
import com.nagisazz.fund.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
public class FundCalService {

    @Autowired
    private FundInfoExtendMapper fundInfoMapper;

    @Autowired
    private InvestLogMapper investLogMapper;

    @Autowired
    private MsgService msgService;

    /**
     * 普通交易日计算收益
     *
     * @param fundInfo
     */
    public void calNormal(FundInfo fundInfo) {
        crawler(fundInfo.getCode());
        String[] param = new String[]{"python",
                "/nagisa/invest/CalculateMain.py", fundInfo.getCode(), String.valueOf(LocalDate.now())};
        final String res = callPython(param);
        String[] data = res.split(" ");
        // 当天收益=余额*涨跌幅
        double profile = fundInfo.getBalance() * Double.parseDouble(data[1]);
        // 收益率=总收益/总买入
        double yields = (fundInfo.getProfileAmount() + profile) / fundInfo.getBuyAmount();

        // 更新当天信息
        fundInfo.setBalance(fundInfo.getBalance() + profile);
        fundInfo.setWorth(Double.parseDouble(data[0]));
        fundInfo.setYields(yields);
        fundInfo.setProfileAmount(fundInfo.getProfileAmount() + profile);
        fundInfo.setUpdateTime(LocalDateTime.now());
        fundInfoMapper.updateByPrimaryKeySelective(fundInfo);

        // 记录当天投资日志信息
        InvestLog investLog = new InvestLog();
        BeanUtils.copyProperties(fundInfo, investLog);
        investLog.setId(null);
        investLog.setFundId(fundInfo.getId());
        investLog.setInvestType(1);
        investLog.setCreateTime(LocalDateTime.now());
        investLog.setUpdateTime(LocalDateTime.now());
        investLogMapper.insertSelective(investLog);
    }

    /**
     * 定投日计算收益
     *
     * @param fundInfo
     * @param invest
     */
    public void calInvest(FundInfo fundInfo, InvestLog invest) {
        crawler(fundInfo.getCode());
        String[] param = new String[]{"python",
                "/nagisa/invest/CalculateMain.py", fundInfo.getCode(), String.valueOf(LocalDate.now())};
        final String res = callPython(param);
        String[] data = res.split(" ");
        // 当天收益=余额*涨跌幅
        double profile = fundInfo.getBalance() * Double.parseDouble(data[1]);
        // 当天买入金额
        double buyAmount = invest.getPrice() < 0 ? 0 : invest.getPrice();
        // 当天卖出金额
        double saleAmount = invest.getPrice() < 0 ? -invest.getPrice() : 0;
        // 收益率=总收益/总买入
        double yields = (fundInfo.getProfileAmount() + profile) / (fundInfo.getBuyAmount() + buyAmount);

        // 更新基金信息
        fundInfo.setBalance(fundInfo.getBalance() + profile + invest.getPrice());
        fundInfo.setWorth(Double.parseDouble(data[0]));
        fundInfo.setYields(yields);
        fundInfo.setProfileAmount(fundInfo.getProfileAmount() + profile);
        fundInfo.setBuyAmount(fundInfo.getBuyAmount() + buyAmount);
        fundInfo.setSaleAmount(fundInfo.getSaleAmount() + saleAmount);
        fundInfo.setInvestAmount(fundInfo.getInvestAmount() + invest.getPrice());
        fundInfo.setInvestNum(fundInfo.getInvestNum() + 1);
        fundInfo.setUpdateTime(LocalDateTime.now());
        fundInfo.setLastInvestTime(LocalDateTime.now());
        fundInfoMapper.updateByPrimaryKeySelective(fundInfo);

        // 记录当天投资日志信息
        InvestLog investLog = new InvestLog();
        BeanUtils.copyProperties(fundInfo, investLog);
        investLog.setId(null);
        investLog.setFundId(fundInfo.getId());
        investLog.setInvestType(invest.getInvestType());
        investLog.setPrice(invest.getPrice());
        investLog.setRate(invest.getRate());
        investLog.setMeanAmount(invest.getMeanAmount());
        investLog.setWaveAmount(invest.getWaveAmount());
        investLog.setCreateTime(LocalDateTime.now());
        investLog.setUpdateTime(LocalDateTime.now());
        investLogMapper.insertSelective(investLog);

        investLogMapper.deleteByPrimaryKey(invest.getId());
    }

    /**
     * 定投操作
     *
     * @param fundInfo
     */
    public void operateInvest(FundInfo fundInfo, String worth) {
        crawler(fundInfo.getCode());
        final ParamData paramData = ParamData.builder().code(fundInfo.getCode()).date(String.valueOf(LocalDate.now()))
                .frequence(String.valueOf(fundInfo.getFrequence())).invest(String.valueOf(fundInfo.getInvestMoney()))
                .balance(String.valueOf(fundInfo.getBalance())).worth(worth)
                .yields(String.valueOf(fundInfo.getYields())).mean(String.valueOf(fundInfo.getMean()))
                .wave(String.valueOf(fundInfo.getWave())).type(String.valueOf(fundInfo.getType())).build();
        final ResultData resultData = invest(paramData);

        // 记录定投信息
        InvestLog investLog = new InvestLog();
        investLog.setFundId(fundInfo.getId());
        investLog.setInvestType(resultData.getAll().equals("True") ? 3 : 2);
        investLog.setPrice(Double.valueOf(resultData.getPrice()));
        investLog.setRate(Double.valueOf(resultData.getRate()));
        investLog.setMeanAmount(Double.valueOf(resultData.getMean()));
        investLog.setWaveAmount(Double.valueOf(resultData.getWave()));
        investLog.setCreateTime(LocalDateTime.now());
        investLog.setUpdateTime(LocalDateTime.now());
        investLogMapper.insertSelective(investLog);

        // 发送定投消息
        // xx基金目前持有收益xx元，收益率xx，高于（低于）均线xx。建议今日买入（卖出）xx元
        String wordMean = (Double.parseDouble(worth) / investLog.getMeanAmount() - 1) > 0 ? "高于" : "低于";
        String wordInvest = investLog.getInvestType() == 3 ? "卖出" + investLog.getPrice() + "元" :
                (investLog.getPrice() < 0 ? "卖出" + Math.abs(investLog.getPrice()) + "元" : "买入" + investLog.getPrice() + "元");
        String content = fundInfo.getName() + "基金目前持有收益" + transPercent(fundInfo.getProfileAmount()) + "元，收益率" +
                transPercent(fundInfo.getYields()) + "%，" + wordMean + "均线" +
                Math.abs(transPercent(Double.parseDouble(worth) / investLog.getMeanAmount() - 1)) +
                "%。建议今日" + wordInvest;
        msgService.sendMsg("基金定投提醒", content);
    }

    public String crawler(String code) {
        String[] param = new String[]{"python",
                "/nagisa/invest/CrawlerMain.py", code};
        return callPython(param);
    }

    public ResultData invest(ParamData paramData) {
        String[] param = new String[]{"python",
                "/nagisa/invest/DecisionMain.py", "-c" + paramData.getCode(), "-d" + paramData.getDate(),
                "-f" + paramData.getFrequence(), "-i" + paramData.getInvest(), "-b" + paramData.getBalance(), "-w" + paramData.getWorth(),
                "-y" + paramData.getYields(), "-m" + paramData.getMean(), "-v" + paramData.getWave(), "-t" + paramData.getType()};
        final String res = callPython(param);
        String[] data = res.split(" ");
        ResultData resultData = ResultData.builder().all(data[0]).rate(data[1]).price(data[2]).mean(data[3]).wave(data[4]).lastYield(data[5]).build();
        log.info("invest返回封装结果：{}", resultData);
        return resultData;
    }

    private String callPython(String[] param) {
        log.info("调用python参数：{}", JSONObject.toJSONString(param));
        Process proc;
        StringBuilder res = new StringBuilder();
        StringBuilder resErr = new StringBuilder();
        String tmpErr = "";
        String tmp = "";
        try {
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
            log.error("调用python失败", e);
        }
        log.info("调用python返回结果：{}", res);
        log.info("调用python失败结果：{}", resErr);
        return String.valueOf(res);
    }

    private Double transPercent(Double num) {
        return ((int) (num * 10000)) / 100.0;
    }
}
