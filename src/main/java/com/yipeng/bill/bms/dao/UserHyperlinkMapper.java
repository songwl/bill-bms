package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserHyperlink;

public interface UserHyperlinkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserHyperlink record);

    int insertSelective(UserHyperlink record);

    UserHyperlink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserHyperlink record);

    int updateByPrimaryKey(UserHyperlink record);
}