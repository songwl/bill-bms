package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.RoleMapper;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by song on 17/3/12.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public Role getRoleById(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public Role getRoleByRoleCode(String roleCode) {
        return roleMapper.selectByRoleCode(roleCode);
    }
}
