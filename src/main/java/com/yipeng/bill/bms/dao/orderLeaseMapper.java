package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.orderLease;

import java.util.List;
import java.util.Map;

public interface orderLeaseMapper {

    int deleteByPrimaryKey(Long id);

    int insert(orderLease record);

    int insertSelective(orderLease record);

    orderLease selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(orderLease record);

    int updateByPrimaryKey(orderLease record);

    //List<LeaseHall> selectByAllotId(Map<String, Object> param);
    List<Map<String, Object>> selectByAllotId(Map<String, Object> param);

    Long selectByAllotIdCount(Map<String, Object> param);

    int selectByKeywordCount(Map<String, Object> param);

    int selectHomePageCount(Map<String, Object> param);

    List<Map<String, Object>> selectByWebsite(Map<String, Object> map);

    int updateByWebsite(Map<String, Object> map);

    Long selectByReceiveIdCount(Map<String, Object> param);

    List<Map<String, Object>> selectByReceiveId(Map<String, Object> param);

    Long selectAllCount(Map<String, Object> param);

    List<Map<String, Object>> selectAll(Map<String, Object> param);

    orderLease selectReserveByWebsite(String website);
}