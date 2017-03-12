package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.UserRole;
import com.yipeng.bill.bms.service.RoleService;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public int saveUser(User user) {
        user.setStatus(false);
        user.setCreateTime(new Date());
        user.setPassword(CryptoUtils.md5(user.getPassword()));

        int c = userMapper.insert(user);

        Role role = roleService.getRoleByRoleCode(Roles.DISTRIBUTOR.name());

        if (role!=null) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());
            userRoleService.saveUserRole(userRole);
        }
        return  c;
    }

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
    public Long getUserListCount() {
        return  userMapper.getUserListCount();
    }

}
