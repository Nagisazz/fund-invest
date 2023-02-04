package com.nagisazz.fund.controller;

import com.nagisazz.base.pojo.OperationResult;
import com.nagisazz.fund.entity.FundInfo;
import com.nagisazz.fund.entity.InvestLog;
import com.nagisazz.fund.service.FundCalService;
import com.nagisazz.fund.service.FundService;
import com.nagisazz.fund.vo.ParamData;
import com.nagisazz.fund.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class FundController {

    @Autowired
    private FundCalService fundCalService;

    @Autowired
    private FundService fundService;

    /**
     * 单次定投
     * @param data
     * @return
     */
    @GetMapping("/invest")
    @ResponseBody
    public OperationResult invest(@RequestParam("data") String data) {
        log.info("invest参数：{}", data);
        String[] params = data.split("\\$");
        ParamData paramData = ParamData.builder().code(params[0]).date(params[1]).frequence(params[2]).invest(params[3]).balance(params[4])
                .worth(params[5]).yields(params[6]).mean(params[7]).wave(params[8]).type(params[9]).build();
        if (StringUtils.isBlank(fundCalService.crawler(paramData.getCode()))) {
            return null;
        }
        return OperationResult.buildSuccessResult(fundCalService.invest(paramData));
    }

    @GetMapping("/start")
    @ResponseBody
    public OperationResult start(@RequestParam("data") String data){
        log.info("start参数：{}", data);
        String[] params = data.split("\\$");
        return fundService.start(params);
    }

    @GetMapping("/update")
    @ResponseBody
    public OperationResult update(@RequestParam("data") String data){
        log.info("update参数：{}", data);
        String[] params = data.split("\\$");
        return fundService.update(params);
    }

    @GetMapping("/list")
    @ResponseBody
    public OperationResult list(){
        List<FundInfo> fundInfos = fundService.getList();
        return OperationResult.buildSuccessResult(fundInfos);
    }

    @GetMapping("info")
    @ResponseBody
    public OperationResult info(@RequestParam("fundId") Long fundId){
        List<InvestLog> investLogs = fundService.getInfo(fundId);
        return OperationResult.buildSuccessResult(investLogs);
    }

    @GetMapping("stop")
    @ResponseBody
    public OperationResult stop(@RequestParam("fundId") Long fundId){
        fundService.stop(fundId);
        return OperationResult.buildSuccessResult(null);
    }
}
