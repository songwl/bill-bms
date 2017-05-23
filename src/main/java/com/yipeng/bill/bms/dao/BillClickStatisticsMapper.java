package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillClickStatistics;

import java.util.List;
import java.util.Map;

public interface BillClickStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillClickStatistics record);

    int insertSelective(BillClickStatistics record);

    BillClickStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillClickStatistics record);

    int updateByPrimaryKey(BillClickStatistics record);

    List<BillClickStatistics> selectByDateNow(Map<String,Object> params);
}