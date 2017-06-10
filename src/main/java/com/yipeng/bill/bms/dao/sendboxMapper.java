package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.sendBox;

import java.util.List;
import java.util.Map;

public interface sendBoxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(sendBox record);

    int insertSelective(sendBox record);

    sendBox selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(sendBox record);

    int updateByPrimaryKey(sendBox record);

    Long selectCount(Map<String ,Object> params);
    Long selectCountRead(Map<String ,Object> params);

    List<sendBox> selectSendBox(Map<String,Object> params);

    sendBox selectById(Long id);
}