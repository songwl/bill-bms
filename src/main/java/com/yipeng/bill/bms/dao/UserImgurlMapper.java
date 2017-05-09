package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserImgurl;

import java.util.List;

public interface UserImgurlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserImgurl record);

    int insertSelective(UserImgurl record);

    UserImgurl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserImgurl record);

    int updateByPrimaryKey(UserImgurl record);

    List<UserImgurl> selectByUserId(Long userId);
    List<UserImgurl> selectByWebsite(String website);
}