package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByParams(Map<String,Object> params);
}