package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillPrice;

import java.util.List;

public interface BillPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillPrice record);

    int insertSelective(BillPrice record);

    BillPrice selectByPrimaryKey(Long id);
    List<BillPrice> selectByBillId(Long BillId);

    int updateByPrimaryKeySelective(BillPrice record);

    int updateByPrimaryKey(BillPrice record);
}