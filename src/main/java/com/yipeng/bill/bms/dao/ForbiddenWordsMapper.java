package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.ForbiddenWords;

import java.util.List;

public interface ForbiddenWordsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ForbiddenWords record);

    int insertSelective(ForbiddenWords record);

    ForbiddenWords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ForbiddenWords record);

    int updateByPrimaryKey(ForbiddenWords record);

    List<ForbiddenWords> selectBySelective();
}