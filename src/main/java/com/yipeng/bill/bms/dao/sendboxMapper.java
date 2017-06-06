package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.sendbox;

public interface sendboxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(sendbox record);

    int insertSelective(sendbox record);

    sendbox selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(sendbox record);

    int updateByPrimaryKey(sendbox record);
}