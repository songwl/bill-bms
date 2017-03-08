package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface UserService {
    int SaveUser(User user);
    User getUserById(Long id);
    User getUserByName(String userName);
    List<User> findList(Map<String,Object> params);
}
