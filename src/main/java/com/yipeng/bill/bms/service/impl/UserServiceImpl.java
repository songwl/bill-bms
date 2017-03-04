package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 17/3/4.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findList(Map<String, Object> params) {
        return userMapper.selectByParams(params);
    }
}
