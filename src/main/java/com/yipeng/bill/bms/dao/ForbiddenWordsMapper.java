package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.ForbiddenWords;

import java.util.List;
import java.util.Map;

public interface ForbiddenWordsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ForbiddenWords record);

    int insertSelective(ForbiddenWords record);

    ForbiddenWords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ForbiddenWords record);

    int updateByPrimaryKey(ForbiddenWords record);

    List<ForbiddenWords> selectBySelective();

    List<ForbiddenWords> selectByWords(Map<String,Object> params);
    List<ForbiddenWords> selectByKeywords(Map<String,Object> params);
}