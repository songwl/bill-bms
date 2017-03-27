package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.FundItem;

import java.util.List;
import java.util.Map;

public interface FundItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundItem record);

    int insertSelective(FundItem record);

    FundItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundItem record);

    int updateByPrimaryKey(FundItem record);
    List<FundItem> getFundItemList(Map<String,Object> params);
}