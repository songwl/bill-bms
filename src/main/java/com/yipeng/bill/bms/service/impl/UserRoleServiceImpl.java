package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.UserRoleMapper;
import com.yipeng.bill.bms.domain.UserRole;
import com.yipeng.bill.bms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/8.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
     @Autowired
      private UserRoleMapper userRoleMapper;
    @Override
    public int saveUserRole(UserRole userRole) {
        return userRoleMapper.insert(userRole);
    }
}
