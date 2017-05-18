package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.Logs;

public interface LogsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Logs record);

    int insertSelective(Logs record);

    Logs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Logs record);

    int updateByPrimaryKey(Logs record);
}