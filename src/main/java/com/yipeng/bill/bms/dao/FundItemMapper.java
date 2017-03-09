package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.FundItem;

public interface FundItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundItem record);

    int insertSelective(FundItem record);

    FundItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundItem record);

    int updateByPrimaryKey(FundItem record);
}