package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserFootMessage;

public interface UserFootMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFootMessage record);

    int insertSelective(UserFootMessage record);

    UserFootMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFootMessage record);

    int updateByPrimaryKey(UserFootMessage record);

    UserFootMessage selectByUserId(Long userId);

    UserFootMessage selectByWebsite(String website);
}