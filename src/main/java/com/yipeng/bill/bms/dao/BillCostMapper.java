package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillCost;

public interface BillCostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillCost record);

    int insertSelective(BillCost record);

    BillCost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillCost record);

    int updateByPrimaryKey(BillCost record);
}