package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.FundItem;
import com.yipeng.bill.bms.model.FundItemSum;

import java.util.List;
import java.util.Map;

public interface FundItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundItem record);

    int insertSelective(FundItem record);

    FundItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundItem record);

    int updateByPrimaryKey(FundItem record);

    Long getFundItemListCount(Map<String,Object> params);

    Long getFundItemListByOtherCount(Map<String,Object> params);
    //管理员
    List<FundItemSum> selectByAdmin(Map<String,Object> params);

}