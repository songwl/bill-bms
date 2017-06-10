package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.inBox;
import com.yipeng.bill.bms.domain.noticepublish;
import com.yipeng.bill.bms.domain.sendBox;
import com.yipeng.bill.bms.vo.Feedback;

import java.util.List;
import java.util.Map;

public interface inBoxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(inBox record);

    int insertSelective(inBox record);

    inBox selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(inBox record);

    int updateByPrimaryKey(inBox record);

    Long selectCount(Map<String ,Object> params);

    List<Feedback> selectInBox(Map<String,Object> params);

}