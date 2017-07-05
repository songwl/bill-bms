package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.leaseOverdueTb;

import java.util.List;
import java.util.Map;

public interface leaseOverdueTbMapper {
    int deleteByPrimaryKey(Long id);

    int insert(leaseOverdueTb record);

    int insertSelective(leaseOverdueTb record);

    leaseOverdueTb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(leaseOverdueTb record);

    int updateByPrimaryKey(leaseOverdueTb record);

    List<Map<String, Object>> selectByReserveId(Map<String, Object> map);

    Long selectByReserveIdCount(Map<String, Object> map);
}