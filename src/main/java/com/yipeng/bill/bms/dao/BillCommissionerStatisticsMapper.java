package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillCommissionerStatistics;

import java.util.List;
import java.util.Map;

public interface BillCommissionerStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillCommissionerStatistics record);

    int insertSelective(BillCommissionerStatistics record);

    BillCommissionerStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillCommissionerStatistics record);

    int updateByPrimaryKey(BillCommissionerStatistics record);

    BillCommissionerStatistics selectByDay(Map<String, Object> params);

    List<BillCommissionerStatistics> selectByGetAll(Map<String, Object> params);
}