package com.nagisazz.fund.schedule;

import com.nagisazz.fund.dao.FundInfoExtendMapper;
import com.nagisazz.fund.entity.FundInfo;
import com.nagisazz.fund.entity.InvestLog;
import com.nagisazz.fund.service.FundCalService;
import com.nagisazz.fund.service.FundInfoService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class FundJob {

    @Resource
    private FundInfoExtendMapper fundInfoExtendMapper;

    @Resource
    private FundInfoService fundInfoService;

    @Resource
    private FundCalService fundCalService;

    // 定时任务1：周一到周五23点更新t_fund_info表中所有有效数据
    // 逻辑：判断当天是否是交易日
    // 逻辑：按照日期查询t_invest_log，如果有数据说明当天有定投，则更新t_invest_log数据，并且计算定投相关变化
    @XxlJob("worth")
    public void worth() {
        final String jobParam = XxlJobHelper.getJobParam();
        log.info("开始执行【worth】任务，jobParam：{}", jobParam);
        // 所有正在持续计算的基金
        final List<FundInfo> fundInfos = fundInfoExtendMapper.selectList(FundInfo.builder().valid(1).build());
        if (CollectionUtils.isEmpty(fundInfos)) {
            XxlJobHelper.handleSuccess("worth成功，没有找到需要计算的基金");
            return;
        }
        if (!fundInfoService.judgeTrading(fundInfos.get(0).getCode())) {
            XxlJobHelper.handleSuccess("worth成功，今天" + LocalDate.now() + "不是交易日");
            return;
        }
        for (FundInfo fundInfo : fundInfos) {
            // 判断当天是否有定投记录
            final InvestLog investLog = fundInfoExtendMapper.selectTodayInvestLog(fundInfo.getId());
            // 没有定投记录，正常计算
            if (investLog == null) {
                fundCalService.calNormal(fundInfo);
            } else {
                fundCalService.calInvest(fundInfo, investLog);
            }
        }
        log.info("结束执行【worth】任务，jobParam：{}", jobParam);
    }

    // 定时任务2：周一到周五14点50计算t_fund_info表中所有有效数据的定投
    // 逻辑：查询t_fund_info表中上一个定投日距今天数是否超过定投频率，如果超过则进行定投操作
    // 逻辑：判断当天是否是交易日
    // 逻辑：插入t_invest_log记录，包括定投金额及定投比率
    @XxlJob("invest")
    public void invest() {
        final String jobParam = XxlJobHelper.getJobParam();
        log.info("开始执行【invest】任务，jobParam：{}", jobParam);
        // 所有正在持续计算的基金
        final List<FundInfo> fundInfos = fundInfoExtendMapper.selectNeedInvest();
        if (CollectionUtils.isEmpty(fundInfos)) {
            XxlJobHelper.handleSuccess("invest成功，没有找到需要计算的基金");
            return;
        }
        if (!fundInfoService.judgeTrading(fundInfos.get(0).getCode())) {
            XxlJobHelper.handleSuccess("invest成功，今天" + LocalDate.now() + "不是交易日");
            return;
        }
        for (FundInfo fundInfo : fundInfos) {
            // 进行定投操作
            String worth = fundInfoService.getWorth(fundInfo.getCode());
            fundCalService.operateInvest(fundInfo, worth);
        }
        log.info("结束执行【invest】任务，jobParam：{}", jobParam);
    }

    @XxlJob("test")
    public void test() {
        final String jobParam = XxlJobHelper.getJobParam();
        log.info("开始执行【test】任务，jobParam：{}", jobParam);
        XxlJobHelper.handleSuccess("success");
        log.info("结束执行【test】任务，jobParam：{}", jobParam);
    }
}
