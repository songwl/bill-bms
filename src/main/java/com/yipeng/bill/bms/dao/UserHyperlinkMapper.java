package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserHyperlink;

import java.util.List;

public interface UserHyperlinkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserHyperlink record);

    int insertSelective(UserHyperlink record);

    UserHyperlink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserHyperlink record);

    int updateByPrimaryKey(UserHyperlink record);

    List<UserHyperlink> selectByUserId(Long userId);
    List<UserHyperlink> selectByWebsite(String website);
}