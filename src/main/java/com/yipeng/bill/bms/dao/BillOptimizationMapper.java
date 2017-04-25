package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillOptimization;

public interface BillOptimizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillOptimization record);

    int insertSelective(BillOptimization record);

    BillOptimization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillOptimization record);

    int updateByPrimaryKey(BillOptimization record);
}