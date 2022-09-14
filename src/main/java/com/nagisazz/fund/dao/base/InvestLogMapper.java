package com.nagisazz.fund.dao.base;

import com.nagisazz.fund.entity.InvestLog;
import java.util.List;

public interface InvestLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvestLog investlog);

    int insertSelective(InvestLog investlog);

    InvestLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvestLog investlog);

    int updateByPrimaryKey(InvestLog investlog);

    InvestLog selectOne(InvestLog investlog);

    List<InvestLog> selectList(InvestLog investlog);

    int deleteSelective(InvestLog investlog);

    int batchUpdate(List<InvestLog> investlogList);

    int batchUpdateSelective(List<InvestLog> investlogList);

    int batchInsert(List<InvestLog> investlogList);

    int batchDelete(List<Long> investlogList);

    List<InvestLog> batchSelect(List<Long> investlogList);
}