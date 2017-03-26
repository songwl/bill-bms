package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.FundAccount;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.UserRole;
import com.yipeng.bill.bms.service.CustomerService;
import com.yipeng.bill.bms.service.RoleService;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.vo.CustomerListDetails;
import com.yipeng.bill.bms.vo.LoginUser;
import com.yipeng.bill.bms.vo.Roles;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Override
    public int savaUser(User user, int addMemberId, Long userId,String realName,String contact,String phone,String qq, BigDecimal balance) {

        User user1=userMapper.selectByUserName(user.getUserName());
        if(user1!=null)
        {
            return  0;
        }
        else
        {
            List<UserRole> userRoles=userRoleService.findUserRolesByUserId(userId);
            UserRole userRole= userRoles.get(0);
            if(userRole!=null)
            {
                //如果是超级管理员，创建的渠道商
                if(userRole.getRoleId()==1)
                {
                    user.setStatus(true);
                    user.setCreateTime(new Date());
                    user.setPassword(CryptoUtils.md5(user.getPassword()));
                    user.setCreateUserId(userId);
                    user.setRealName(realName);
                    user.setContact(contact);
                    user.setPhone(phone);
                    user.setQq(qq);
                    user.setLoginCount(0);
                    user.setStatus(true);
                    int num = userMapper.insert(user);
                    //资金余额
                    FundAccount fundAccount=new FundAccount();
                    fundAccount.setUserId(user.getId());
                    fundAccount.setBalance(balance);
                    fundAccount.setCreateTime(new Date());
                    fundAccount.setCreateUserId(userId);
                   fundAccountMapper.insert(fundAccount);

                    //用户权限
                    Role role=roleService.getRoleByRoleCode(Roles.DISTRIBUTOR.name());
                    if(role!=null)
                    {
                        UserRole userRole1 = new UserRole();
                        userRole1.setUserId(user.getId());
                        userRole1.setRoleId(role.getId());
                        userRoleService.saveUserRole(userRole1);
                    }
                    return  num;

                }
                //如果是渠道商，判断创建用户
                else if(userRole.getRoleId()==4)
                {
                    if(addMemberId==1)
                    {
                        user.setStatus(true);
                        user.setCreateTime(new Date());
                        user.setPassword(CryptoUtils.md5(user.getPassword()));
                        user.setCreateUserId(userId);
                        user.setRealName(realName);
                        user.setContact(contact);
                        user.setPhone(phone);
                        user.setQq(qq);
                        user.setLoginCount(0);
                        int num = userMapper.insert(user);
                        //资金余额
                        FundAccount fundAccount=new FundAccount();
                        fundAccount.setUserId(user.getId());
                        fundAccount.setBalance(balance);
                        fundAccount.setCreateTime(new Date());
                        fundAccount.setCreateUserId(userId);
                        fundAccountMapper.insert(fundAccount);

                        Role role=roleService.getRoleByRoleCode(Roles.CUSTOMER.name());
                        if(role!=null)
                        {
                            UserRole userRole1 = new UserRole();
                            userRole1.setUserId(user.getId());
                            userRole1.setRoleId(role.getId());
                            userRoleService.saveUserRole(userRole1);
                        }
                        return  num;
                    }
                    else
                    {
                        user.setStatus(true);
                        user.setCreateTime(new Date());
                        user.setPassword(CryptoUtils.md5(user.getPassword()));
                        user.setCreateUserId(userId);
                        user.setLoginCount(0);
                        int num = userMapper.insert(user);
                        //资金余额
                        FundAccount fundAccount=new FundAccount();
                        fundAccount.setUserId(user.getId());
                        fundAccount.setBalance(balance);
                        fundAccount.setCreateTime(new Date());
                        fundAccount.setCreateUserId(userId);
                        fundAccountMapper.insert(fundAccount);

                        Role role=roleService.getRoleByRoleCode(Roles.AGENT.name());
                        if(role!=null)
                        {
                            UserRole userRole1 = new UserRole();
                            userRole1.setUserId(user.getId());
                            userRole1.setRoleId(role.getId());
                            userRoleService.saveUserRole(userRole1);
                        }
                        return  num;
                    }

                }
                //如果是代理商，创建客户
                else
                {
                    user.setStatus(true);
                    user.setCreateTime(new Date());
                    user.setPassword(CryptoUtils.md5(user.getPassword()));
                    user.setCreateUserId(userId);
                    user.setLoginCount(0);
                    int num = userMapper.insert(user);
                    //资金余额
                    FundAccount fundAccount=new FundAccount();
                    fundAccount.setUserId(user.getId());
                    fundAccount.setBalance(balance);
                    fundAccount.setCreateTime(new Date());
                    fundAccount.setCreateUserId(userId);
                    fundAccountMapper.insert(fundAccount);

                    Role role=roleService.getRoleByRoleCode(Roles.CUSTOMER.name());
                    if(role!=null)
                    {
                        UserRole userRole1 = new UserRole();
                        userRole1.setUserId(user.getId());
                        userRole1.setRoleId(role.getId());
                        userRoleService.saveUserRole(userRole1);
                    }
                    return  num;
                }

            }

        }
        return  0;
    }

    /**
     * 获取客户列表
     * @param params
     * @return
     */
    @Override
    public  Map<String, Object> getCustomerList(Map<String, Object> params,LoginUser user) {

        //模型对象
        List<CustomerListDetails> customerListDetailsList=new ArrayList<>();

        int limit=Integer.parseInt(params.get("limit").toString()) ;
        int offset=Integer.parseInt(params.get("offset").toString()) ;
        int i=offset;
        //管理员
        if(user.hasRole("SUPER_ADMIN"))
        {
            Role role=roleMapper.selectByRoleCode("DISTRIBUTOR");
            params.put("roleId",role.getId());
            params.put("userId",user.getId());
             List<User> userList=userMapper.getUserRoleByCreateId(params);
             Long total=userMapper.getUserRoleByCreateIdCount(params);
            for (User user1: userList
                 ) {
                     i++;
                     CustomerListDetails customerListDetails=new CustomerListDetails();
                     customerListDetails.setId(i);
                     customerListDetails.setCustomerId(user1.getId());
                     customerListDetails.setUserName(user1.getUserName());
                     customerListDetails.setRealName(user1.getRealName());
                     customerListDetails.setContact(user1.getContact());
                     customerListDetails.setPhone(user1.getPhone());
                     customerListDetails.setQq(user1.getQq());
                     customerListDetails.setStatus(user1.getStatus());
                     customerListDetails.setCreateTime(DateUtils.formatDate(user1.getCreateTime()));

                     if(user1.getLastLoginTime()!=null)
                     {
                         customerListDetails.setLastLoginTime(DateUtils.formatDate(user1.getLastLoginTime()));
                     }
                     customerListDetails.setLoginCount(user1.getLoginCount());

                     int count=billPriceMapper.selectBillCount(user1.getId());

                     FundAccount fundAccount= fundAccountMapper.selectByUserId(user1.getId());
                     if(fundAccount!=null)
                     {
                         customerListDetails.setBalance(fundAccount.getBalance());
                     }
                     customerListDetails.setMissionCount(count);
                     customerListDetailsList.add(customerListDetails);

            }
            Map<String,Object> map=new HashMap<>();
            map.put("rows",customerListDetailsList);
            map.put("total",total);

            return  map;

        }
        //操作员
        else if(user.hasRole("COMMISSIONER"))
        {
            params.put("ascription",user.getId());
            params.put("inMemberId",user.getCreateUserId());
            List<User> userList=userMapper.getUserBillAscription(params);
            Long total=userMapper.getUserBillAscriptionCount(params);
            for (User user1: userList
                 ) {

                i++;
                CustomerListDetails customerListDetails=new CustomerListDetails();
                customerListDetails.setId(i);
                customerListDetails.setCustomerId(user1.getId());
                customerListDetails.setUserName(user1.getUserName());
                customerListDetails.setRealName(user1.getRealName());
                customerListDetails.setContact(user1.getContact());
                customerListDetails.setStatus(user1.getStatus());
                customerListDetails.setPhone(user1.getPhone());
                customerListDetails.setQq(user1.getQq());
                customerListDetails.setCreateTime(DateUtils.formatDate(user1.getCreateTime()));

                if(user1.getLastLoginTime()!=null)
                {
                    customerListDetails.setLastLoginTime(DateUtils.formatDate(user1.getLastLoginTime()));
                }
                customerListDetails.setLoginCount(user1.getLoginCount());

                int count=billPriceMapper.selectBillCount(user1.getId());

                FundAccount fundAccount= fundAccountMapper.selectByUserId(user1.getId());
                if(fundAccount!=null)
                {
                    customerListDetails.setBalance(fundAccount.getBalance());
                }
                customerListDetails.setMissionCount(count);
                customerListDetailsList.add(customerListDetails);
            }
            Map<String,Object> map=new HashMap<>();
            map.put("rows",customerListDetailsList);
            map.put("total",total);

            return  map;
        }
        //其他
        else
        {

            params.put("userId",user.getId());
            List<User> userList=userMapper.getUserRoleByCreateId(params);
            Long total=userMapper.getUserRoleByCreateIdCount(params);
            for (User user1: userList
                    ) {
                i++;
                CustomerListDetails customerListDetails=new CustomerListDetails();
                customerListDetails.setId(i);
                customerListDetails.setCustomerId(user1.getId());
                customerListDetails.setUserName(user1.getUserName());
                customerListDetails.setRealName(user1.getRealName());
                customerListDetails.setContact(user1.getContact());
                customerListDetails.setStatus(user1.getStatus());
                customerListDetails.setPhone(user1.getPhone());
                customerListDetails.setQq(user1.getQq());
                customerListDetails.setCreateTime(DateUtils.formatDate(user1.getCreateTime()));

                if(user1.getLastLoginTime()!=null)
                {
                    customerListDetails.setLastLoginTime(DateUtils.formatDate(user1.getLastLoginTime()));
                }
                customerListDetails.setLoginCount(user1.getLoginCount());

                int count=billPriceMapper.selectBillCount(user1.getId());

                FundAccount fundAccount= fundAccountMapper.selectByUserId(user1.getId());
                if(fundAccount!=null)
                {
                    customerListDetails.setBalance(fundAccount.getBalance());
                }
                customerListDetails.setMissionCount(count);
                customerListDetailsList.add(customerListDetails);

            }
            Map<String,Object> map=new HashMap<>();
            map.put("rows",customerListDetailsList);
            map.put("total",total);

            return  map;
        }



    }

    @Override
    public Map<String, Object> getCustomerReviewList(Map<String, Object> params) {
        int limit=Integer.parseInt(params.get("limit").toString()) ;
        int offset=Integer.parseInt(params.get("offset").toString()) ;
        int i=offset;
        List<CustomerListDetails> customerListDetailsList=new ArrayList<>();
       List<User> userList=userMapper.selectByReviewUser(params);
        for (User user: userList
             ) {
            CustomerListDetails customerListDetails=new CustomerListDetails();
            i++;
            customerListDetails.setId(i);
            customerListDetails.setCustomerId(user.getId());
            customerListDetails.setUserName(user.getUserName());
            customerListDetails.setRealName(user.getRealName());
            customerListDetails.setContact(user.getContact());
            customerListDetails.setCreateTime(DateUtils.formatDate(user.getCreateTime()));
            customerListDetailsList.add(customerListDetails);

        }
        Long total=userMapper.selectByReviewUserCount();
        Map<String,Object> map=new HashMap<>();
        map.put("rows",customerListDetailsList);
        map.put("total",total);

        return  map;


    }

    @Override
    public int customerAudit(Long customerId) {
        User user=userMapper.selectByPrimaryKey(customerId);
        if (user!=null)
        {
            user.setStatus(true);
            userMapper.updateByPrimaryKey(user);
            return  1;
        }

        return 0;
    }
}
