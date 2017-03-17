package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.Role;

/**
 * Created by song on 17/3/12.
 */
public interface RoleService {

    Role getRoleById(Long roleId);

    Role getRoleByRoleCode(String roleCode);

}
