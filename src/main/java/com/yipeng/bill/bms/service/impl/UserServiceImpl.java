package com.yipeng.bill.bms.service.Impl;

import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int SaveUser(User user) {
        return  userMapper.insert(user);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByName(String userName) {
        return  userMapper.selectByUserName(userName);
    }

    @Override
    public List<User> findList(Map<String, Object> params) {
        return userMapper.selectList(params);
    }
}
