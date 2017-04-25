package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.NoticeUser;

public interface NoticeUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoticeUser record);

    int insertSelective(NoticeUser record);

    NoticeUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoticeUser record);

    int updateByPrimaryKey(NoticeUser record);
}