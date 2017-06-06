package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.inbox;

public interface inboxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(inbox record);

    int insertSelective(inbox record);

    inbox selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(inbox record);

    int updateByPrimaryKey(inbox record);
}