package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.OperatorService;
import com.yipeng.bill.bms.service.RoleService;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.vo.CustomerListDetails;
import com.yipeng.bill.bms.vo.LoginUser;
import com.yipeng.bill.bms.vo.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 鱼在我这里。 on 2017/3/26.
 */
@Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private offersetMapper offersetMapper;
    @Autowired
    private UserPowerMapper userPowerMapper;


    @Override
    public int saveOperator(User user, LoginUser users, String roleName) {

        User user1 = userMapper.selectByUserName(user.getUserName());
        if (user1 != null) {
            return 0;
        } else {
            user.setStatus(true);
            user.setCreateTime(new Date());
            user.setPassword(CryptoUtils.md5(user.getPassword()));
            user.setLoginCount(0);
            user.setCreateUserId(users.getId());
            user.setQq(user.getQq());
            user.setPhone(user.getPhone());
            user.setContact(user.getContact());
            user.setRealName(user.getRealName());
            int num = userMapper.insert(user);
            Role role = null;
            if (roleName.equals("COMMISSIONER")) {
                role = roleService.getRoleByRoleCode(Roles.COMMISSIONER.name());
            } else {
                role = roleService.getRoleByRoleCode(Roles.ADMIN.name());
            }

            if (role != null) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(role.getId());
                userRoleService.saveUserRole(userRole);
            }
            return num;
        }

    }

    /**
     * 获取操作员列表
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getOperator(Map<String, Object> params) {

        //模型对象
        List<CustomerListDetails> customerListDetailsList = new ArrayList<>();

        List<User> userList = userMapper.getQueryUserAll(params);
        int limit = Integer.parseInt(params.get("limit").toString());
        int offset = Integer.parseInt(params.get("offset").toString());
        int i = offset;
        for (User user : userList
                ) {
            i++;
            CustomerListDetails customerListDetails = new CustomerListDetails();
            customerListDetails.setId(i);
            customerListDetails.setCustomerId(user.getId());
            customerListDetails.setUserName(user.getUserName());
            customerListDetails.setRealName(user.getRealName());
            customerListDetails.setContact(user.getContact());
            customerListDetails.setStatus(user.getStatus());
            customerListDetails.setQq(user.getQq());
            customerListDetails.setPhone(user.getPhone());
            customerListDetails.setCreateTime(DateUtils.formatDate(user.getCreateTime()));
            if (user.getLastLoginTime() != null) {
                customerListDetails.setLastLoginTime(DateUtils.formatDate(user.getLastLoginTime()));
            }
            if (user.getLoginCount() != null) {
                customerListDetails.setLoginCount(user.getLoginCount());
            }
            //查询是否拥有出租平台权限
            offerset offerset = offersetMapper.selectByUserId(user.getId());
            if (offerset == null) {
                customerListDetails.setLeaseRole(0);
            } else {
                if (offerset.getLeasepower() == 1) {
                    customerListDetails.setLeaseRole(1);
                } else {
                    customerListDetails.setLeaseRole(0);
                }
            }
            UserPower userPower = userPowerMapper.selectByUserId(user.getId());
            if (userPower == null) {
                customerListDetails.setUserPower(1);
            } else {
                customerListDetails.setUserPower(userPower.getPowerid());
            }

            customerListDetailsList.add(customerListDetails);

        }
        Map<String, Object> modelMap = new HashMap();
        Role role = roleMapper.selectByPrimaryKey(Long.parseLong(params.get("roleId").toString()));
        int roleCount = userRoleMapper.getCount(role.getId());
        modelMap.put("total", roleCount);
        modelMap.put("rows", customerListDetailsList);

        return modelMap;

    }

    /**
     * 修改信息
     *
     * @return
     */
    @Override
    public int updateOperator(User user, LoginUser loginUser) {

        user.setUpdateUserId(loginUser.getId());
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return 1;
    }

    /**
     * 重置密码
     *
     * @return
     */
    @Override
    public int updatePwd(User user, LoginUser loginUser) {
        user.setUpdateUserId(loginUser.getId());
        user.setUpdateTime(new Date());
        user.setPassword(CryptoUtils.md5("123456"));
        userMapper.updateByPrimaryKeySelective(user);
        return 1;
    }

    @Override
    public int deleteUser(User user, LoginUser loginUser) {

        if (user.getId() != null) {
            userRoleMapper.deleteByUserId(user.getId());
            fundAccountMapper.deleteByUserId(user.getId());
            userMapper.deleteByPrimaryKey(user.getId());
            return 1;
        } else {
            return 0;
        }


    }
}
