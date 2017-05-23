package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillCommissionerStatistics;

public interface BillCommissionerStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillCommissionerStatistics record);

    int insertSelective(BillCommissionerStatistics record);

    BillCommissionerStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillCommissionerStatistics record);

    int updateByPrimaryKey(BillCommissionerStatistics record);
}