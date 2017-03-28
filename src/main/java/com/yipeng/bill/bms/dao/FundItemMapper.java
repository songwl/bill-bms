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
    Long getFundItemListCount(Map<String,Object> params);

    List<FundItem> getFundItemListByOther(Map<String,Object> params);
    Long getFundItemListByOtherCount(Map<String,Object> params);
}