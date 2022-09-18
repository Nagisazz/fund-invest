package com.nagisazz.fund.dao;

import com.nagisazz.fund.dao.base.FundInfoMapper;
import com.nagisazz.fund.entity.FundInfo;
import com.nagisazz.fund.entity.InvestLog;

import java.util.List;

public interface FundInfoExtendMapper extends FundInfoMapper {

    InvestLog selectTodayInvestLog(Long fundId);

    List<FundInfo> selectNeedInvest();
}
