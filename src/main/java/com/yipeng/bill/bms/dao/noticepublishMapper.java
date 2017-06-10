package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.noticepublish;

import java.util.List;
import java.util.Map;

public interface noticepublishMapper {
    int deleteByPrimaryKey(Long id);

    int insert(noticepublish record);

    int insertSelective(noticepublish record);

    noticepublish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(noticepublish record);

    int updateByPrimaryKey(noticepublish record);

    Long getCount(Map<String,Object> params);

    List<noticepublish> selectByInUser(Map<String,Object> params);
}