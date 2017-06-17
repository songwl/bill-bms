package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.KeywordsPrice;
import com.yipeng.bill.bms.model.KeywordToPrice;

import java.util.List;
import java.util.Map;

public interface KeywordsPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KeywordsPrice record);

    int insertSelective(KeywordsPrice record);

    KeywordsPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KeywordsPrice record);

    int updateByPrimaryKey(KeywordsPrice record);

    List<KeywordsPrice> selectByword(Map<String, Object> params2);

    List<KeywordToPrice> selectBywordToPrice(Map<String, Object> params2);

    KeywordsPrice selectByTaskId(int taskid);
}