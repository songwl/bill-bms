package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public int saveUser(User user) {   return  userMapper.insert(user);    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByName(String userName) {  return  userMapper.selectByUserName(userName);    }

    @Override
    public Map<String, Object> findList(int limit, int offset) {

        List<User> users=userMapper.selectList(limit,offset);
        long total=userMapper.getUserListCount();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("total",total);
        modelMap.put("rows",users);
        return  modelMap;
    }

    @Override
    public boolean login(String userName, String password) {
        User user=userMapper.selectByUserName(userName);
        if(user!=null)
        {
            if(user.getUserName().equals(userName)&&user.getPassword().equals(CryptoUtils.md5(password)))
            {
                return true;
            }
        }

            return  false;

    }

    @Override
    public Long getUserListCount() {
        return  userMapper.getUserListCount();
    }

}
