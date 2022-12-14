package com.nagisazz.fund.service;

import com.nagisazz.fund.common.result.OperationResult;
import com.nagisazz.fund.dao.FundInfoExtendMapper;
import com.nagisazz.fund.dao.base.InvestLogMapper;
import com.nagisazz.fund.entity.FundInfo;
import com.nagisazz.fund.entity.InvestLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class FundService {

    @Autowired
    private FundInfoExtendMapper fundInfoMapper;

    @Autowired
    private FundInfoService fundInfoService;

    @Autowired
    private InvestLogMapper investLogMapper;

    /**
     * 开始持续计算
     *
     * @param params（code，balance，profileAmount，yields，worth，frequence，investMoney，mean，wave，type）
     * @return
     */
    public OperationResult start(String[] params) {
        try {
            final String fundName = fundInfoService.getFundName(params[0]);
            FundInfo fundInfo = FundInfo.builder()
                    .name(fundName)
                    .code(params[0])
                    .balance(Double.valueOf(params[1]))
                    .profileAmount(Double.valueOf(params[2]))
                    .yields(Double.valueOf(params[3]))
                    .worth(Double.valueOf(params[4]))
                    .buyAmount(Double.parseDouble(params[1]) - Double.parseDouble(params[2]))
                    .saleAmount((double) 0)
                    .investAmount(Double.parseDouble(params[1]) - Double.parseDouble(params[2]))
                    .investNum(0)
                    .frequence(Integer.valueOf(params[5]))
                    .investMoney(Integer.valueOf(params[6]))
                    .mean(Integer.valueOf(params[7]))
                    .wave(Integer.valueOf(params[8]))
                    .type(Integer.valueOf(params[9]))
                    .valid(1)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            fundInfoMapper.insertSelective(fundInfo);
        } catch (Exception e) {
            log.error("开始持续计算失败", e);
            return OperationResult.buildFail("参数错误，开始持续计算失败");
        }
        return OperationResult.buildSuccess("开始持续计算成功");
    }

    public List<FundInfo> getList() {
        return fundInfoMapper.selectList(FundInfo.builder().valid(1).build());
    }

    public List<InvestLog> getInfo(Long fundId) {
        List<InvestLog> investLogs = investLogMapper.selectList(InvestLog.builder().fundId(fundId).build());
        investLogs.removeIf(investLog -> investLog.getBalance() == null);
        return investLogs;
    }

    public void stop(Long fundId) {
        fundInfoMapper.updateByPrimaryKeySelective(FundInfo.builder().id(fundId).valid(0).build());
    }

    public OperationResult update(String[] params) {
        fundInfoMapper.updateByPrimaryKeySelective(FundInfo.builder()
                .id(Long.valueOf(params[0]))
                .frequence(Integer.valueOf(params[1]))
                .investMoney(Integer.valueOf(params[2]))
                .mean(Integer.valueOf(params[3]))
                .wave(Integer.valueOf(params[4]))
                .type(Integer.valueOf(params[5]))
                .lastInvestTime(LocalDate.parse(params[6], DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay())
                .updateTime(LocalDateTime.now())
                .build());
        return OperationResult.buildSuccess("更新成功");
    }
}
