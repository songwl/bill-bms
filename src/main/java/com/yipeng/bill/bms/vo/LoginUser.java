package com.yipeng.bill.bms.vo;

import com.yipeng.bill.bms.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 17/3/17.
 */
public class LoginUser extends User {

    private List<String> roles = new ArrayList<>(); //用户角色code集合

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String roleCode) {
        return roles.contains(roleCode);
    }
}
