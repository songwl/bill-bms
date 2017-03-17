package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.UserRole;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface UserRoleService {
    /**
     * 新增用户角色关系
     * @param userRole
     * @return
     */
    int saveUserRole(UserRole userRole);

    List<UserRole> findUserRolesByUserId(Long userId);
}
