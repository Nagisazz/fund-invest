package com.nagisazz.fund.controller;

import com.nagisazz.fund.common.result.OperationResult;
import com.nagisazz.fund.entity.FundInfo;
import com.nagisazz.fund.entity.InvestLog;
import com.nagisazz.fund.service.FundCalService;
import com.nagisazz.fund.service.FundService;
import com.nagisazz.fund.vo.ParamData;
import com.nagisazz.fund.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@CrossOrigin
public class FundController {

    @Autowired
    private FundCalService fundCalService;

    @Autowired
    private FundService fundService;

    @Value("${login.username}")
    private String loginName;

    @Value("${login.password}")
    private String loginPassword;

    @PostMapping("login")
    public String login(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpSession session) {

        if (!loginName.equals(username) || !loginPassword.equals(password)){
            return "redirect:fund-login.html";
        }

        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid", username);

        // 将以上返回值和状态码OK封装到响应结果中并返回
        return "redirect:fund-search.html";
    }

    /**
     * 单次定投
     * @param data
     * @return
     */
    @GetMapping("/invest")
    @ResponseBody
    public ResultData invest(@RequestParam("data") String data) {
        log.info("invest参数：{}", data);
        String[] params = data.split("\\$");
        ParamData paramData = ParamData.builder().code(params[0]).date(params[1]).frequence(params[2]).invest(params[3]).balance(params[4])
                .worth(params[5]).yields(params[6]).mean(params[7]).wave(params[8]).type(params[9]).build();
        if (StringUtils.isBlank(fundCalService.crawler(paramData.getCode()))) {
            return null;
        }
        return fundCalService.invest(paramData);
    }

    @GetMapping("/start")
    @ResponseBody
    public OperationResult start(@RequestParam("data") String data){
        log.info("start参数：{}", data);
        String[] params = data.split("\\$");
        return fundService.start(params);
    }

    @GetMapping("/list")
    @ResponseBody
    public OperationResult list(){
        List<FundInfo> fundInfos = fundService.getList();
        return OperationResult.buildSuccess(fundInfos);
    }

    @GetMapping("info")
    @ResponseBody
    public OperationResult info(@RequestParam("fundId") Long fundId){
        List<InvestLog> investLogs = fundService.getInfo(fundId);
        return OperationResult.buildSuccess(investLogs);
    }

    @GetMapping("stop")
    @ResponseBody
    public OperationResult stop(@RequestParam("fundId") Long fundId){
        fundService.stop(fundId);
        return OperationResult.buildSuccess(null);
    }
}
