package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.Searchenginecompletionrate;

import java.util.Map;

public interface SearchenginecompletionrateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Searchenginecompletionrate record);

    int insertSelective(Searchenginecompletionrate record);

    Searchenginecompletionrate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Searchenginecompletionrate record);

    int updateByPrimaryKey(Searchenginecompletionrate record);

    //查询今日是否存在记录
    Searchenginecompletionrate selectByUsedIdAndDay(Map<String,Object> params);
}