package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillCost;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BillCostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillCost record);

    int insertSelective(BillCost record);

    BillCost selectByPrimaryKey(Long id);
    List<BillCost> selectByBillId(Long BillId);
    BillCost selectByBillIdAndDate(Map<String, Object> modelMap);

    int updateByPrimaryKeySelective(BillCost record);

    int updateByPrimaryKey(BillCost record);
}