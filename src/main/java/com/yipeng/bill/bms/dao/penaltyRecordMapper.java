package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.penaltyRecord;

public interface penaltyRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(penaltyRecord record);

    int insertSelective(penaltyRecord record);

    penaltyRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(penaltyRecord record);

    int updateByPrimaryKey(penaltyRecord record);
}