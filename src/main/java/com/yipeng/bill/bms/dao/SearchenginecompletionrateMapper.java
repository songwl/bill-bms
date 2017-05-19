package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.Searchenginecompletionrate;

public interface SearchenginecompletionrateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Searchenginecompletionrate record);

    int insertSelective(Searchenginecompletionrate record);

    Searchenginecompletionrate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Searchenginecompletionrate record);

    int updateByPrimaryKey(Searchenginecompletionrate record);
}