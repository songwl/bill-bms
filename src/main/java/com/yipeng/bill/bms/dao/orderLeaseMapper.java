package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.model.LeaseHall;

import java.util.List;
import java.util.Map;

public interface orderLeaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(orderLease record);

    int insertSelective(orderLease record);

    orderLease selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(orderLease record);

    int updateByPrimaryKey(orderLease record);

    List<LeaseHall> selectByAllotId(Map<String, Object> param);

    Long selectByAllotIdCount(Map<String, Object> param);

    int selectByKeywordCount(Map<String, Object> param);

    int selectHomePageCount(Map<String, Object> param);

    List<HallDetails> selectByWebsite(Map<String, Object> map);
}