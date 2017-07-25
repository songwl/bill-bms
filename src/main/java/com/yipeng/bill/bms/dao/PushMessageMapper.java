package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.PushMessage;

import java.util.List;
import java.util.Map;

public interface PushMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PushMessage record);

    int insertSelective(PushMessage record);

    PushMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushMessage record);

    int updateByPrimaryKey(PushMessage record);

    List<PushMessage> selectByUserId(Map<String,Object> params);
}