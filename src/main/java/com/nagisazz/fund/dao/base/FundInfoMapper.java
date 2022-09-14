package com.nagisazz.fund.dao.base;

import com.nagisazz.fund.entity.FundInfo;
import java.util.List;

public interface FundInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundInfo fundinfo);

    int insertSelective(FundInfo fundinfo);

    FundInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundInfo fundinfo);

    int updateByPrimaryKey(FundInfo fundinfo);

    FundInfo selectOne(FundInfo fundinfo);

    List<FundInfo> selectList(FundInfo fundinfo);

    int deleteSelective(FundInfo fundinfo);

    int batchUpdate(List<FundInfo> fundinfoList);

    int batchUpdateSelective(List<FundInfo> fundinfoList);

    int batchInsert(List<FundInfo> fundinfoList);

    int batchDelete(List<Long> fundinfoList);

    List<FundInfo> batchSelect(List<Long> fundinfoList);
}