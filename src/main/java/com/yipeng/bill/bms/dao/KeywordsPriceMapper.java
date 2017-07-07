package com.yipeng.bill.bms.dao;

import com.sun.org.apache.xerces.internal.xs.StringList;
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

    List<KeywordToPrice> selectBywordToPriceHave(Map<String, Object> params2);

    KeywordsPrice selectByTaskId(int taskid);

    Long selectAllKeywordsCount(Map<String, Object> map);

    List<Map<String, Object>> selectAllKeywords(Map<String, Object> map);

    KeywordsPrice selectOneBykeyword(String keywords);

    List<KeywordToPrice> selectRecommendWords(Map<String,Object> map);
}