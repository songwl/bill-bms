package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillOptimization;

import java.util.Map;

public interface BillOptimizationMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByBillId(Long id);
    int insert(BillOptimization record);

    int insertSelective(BillOptimization record);

    BillOptimization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillOptimization record);

    int updateByPrimaryKey(BillOptimization record);

    int selectByBillIdOfWeek(Map<String, Object> params);


    int selectByBillIdOfMonth(Map<String, Object> params);

    int selectByBillIdOfAll(Map<String, Object> params);
}