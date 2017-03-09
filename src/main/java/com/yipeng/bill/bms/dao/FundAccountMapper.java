package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.FundAccount;

public interface FundAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundAccount record);

    int insertSelective(FundAccount record);

    FundAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundAccount record);

    int updateByPrimaryKey(FundAccount record);
}