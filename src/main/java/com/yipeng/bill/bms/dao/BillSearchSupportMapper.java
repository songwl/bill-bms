package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillSearchSupport;

public interface BillSearchSupportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillSearchSupport record);

    int insertSelective(BillSearchSupport record);

    BillSearchSupport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillSearchSupport record);

    int updateByPrimaryKey(BillSearchSupport record);
}