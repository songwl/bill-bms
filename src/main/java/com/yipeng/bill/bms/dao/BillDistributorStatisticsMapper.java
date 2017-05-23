package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillDistributorStatistics;

import java.util.List;
import java.util.Map;

public interface BillDistributorStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillDistributorStatistics record);

    int insertSelective(BillDistributorStatistics record);

    BillDistributorStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillDistributorStatistics record);

    int updateByPrimaryKey(BillDistributorStatistics record);

    BillDistributorStatistics selectByDayExsits(Map<String,Object> params);

    List<BillDistributorStatistics> selectByDay(Map<String,Object> params);
}