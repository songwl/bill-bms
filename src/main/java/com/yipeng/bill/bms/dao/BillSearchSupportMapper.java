package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillSearchSupport;

public interface BillSearchSupportMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByBillId(Long id);
    int insert(BillSearchSupport record);

    int insertSelective(BillSearchSupport record);

    BillSearchSupport selectByPrimaryKey(Long id);
    BillSearchSupport selectByBillId(Long BillId);
    int updateByPrimaryKeySelective(BillSearchSupport record);

    int updateByPrimaryKey(BillSearchSupport record);
}