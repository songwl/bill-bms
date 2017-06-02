package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.KeywordsPrice;

import java.util.List;

public interface KeywordsPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KeywordsPrice record);

    int insertSelective(KeywordsPrice record);

    KeywordsPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KeywordsPrice record);

    int updateByPrimaryKey(KeywordsPrice record);

    List<KeywordsPrice> selectByword(List<String> arrs);

    KeywordsPrice selectByTaskId(int taskid);
}