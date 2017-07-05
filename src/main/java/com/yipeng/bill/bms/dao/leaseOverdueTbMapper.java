package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.leaseOverdueTb;

public interface leaseOverdueTbMapper {
    int deleteByPrimaryKey(Long id);

    int insert(leaseOverdueTb record);

    int insertSelective(leaseOverdueTb record);

    leaseOverdueTb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(leaseOverdueTb record);

    int updateByPrimaryKey(leaseOverdueTb record);
}