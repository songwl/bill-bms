package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface UserService {
    /**
     * 新增用户
     * @param user
     * @return
     */
    int saveUser(User user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    User getUserByName(String userName);

    /**
     * 根据参数查询用户列表
     * @param params
     * @return
     */
    List<User> findList(Map<String,Object> params);

    /**
     * 判断登录
     * @param userName
     * @param password
     * @return
     */
    boolean login(String userName,String password);
}
