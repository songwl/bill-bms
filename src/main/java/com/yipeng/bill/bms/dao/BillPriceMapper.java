package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillPrice;

public interface BillPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillPrice record);

    int insertSelective(BillPrice record);

    BillPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillPrice record);

    int updateByPrimaryKey(BillPrice record);
}