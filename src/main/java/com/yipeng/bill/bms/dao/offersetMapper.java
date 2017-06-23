package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.offerset;

import java.util.List;

public interface offersetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(offerset record);

    int insertSelective(offerset record);

    offerset selectByPrimaryKey(Long id);

    offerset selectByUserId(Long UserId);

    List<offerset> selectByUserIds(Long UserId);

    int updateByPrimaryKeySelective(offerset record);

    int updateByPrimaryKey(offerset record);
}