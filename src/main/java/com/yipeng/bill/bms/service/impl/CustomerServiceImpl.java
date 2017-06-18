package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.FundItemSum;
import com.yipeng.bill.bms.service.CustomerService;
import com.yipeng.bill.bms.service.RoleService;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.vo.*;
import freemarker.template.utility.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private  FundItemMapper fundItemMapper;
    @Override
    public int savaUser(User user, int addMemberId, LoginUser loginUser,String realName,String contact,String phone,String qq, BigDecimal balance) {

        User user1=userMapper.selectByUserName(user.getUserName());
        if(user1!=null)
        {
            return  0;
        }
        else
        {
            //如果是超级管理员，创建的渠道商
            if(loginUser.hasRole("SUPER_ADMIN"))
            {
                user.setStatus(true);
                user.setCreateTime(new Date());
                user.setPassword(CryptoUtils.md5(user.getPassword()));
                user.setCreateUserId(loginUser.getId());
                user.setRealName(realName);
                user.setContact(contact);
                user.setPhone(phone);
                user.setQq(qq);
                user.setLoginCount(0);
                user.setDailiRole(0);
                user.setStatus(true);
                int num = userMapper.insert(user);
                //资金余额
                FundAccount fundAccount=new FundAccount();
                fundAccount.setUserId(user.getId());
                fundAccount.setBalance(balance);
                fundAccount.setCreateTime(new Date());
                fundAccount.setCreateUserId(loginUser.getId());
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
            else if(loginUser.hasRole("DISTRIBUTOR")||loginUser.hasRole("ASSISTANT"))
            {
                if(addMemberId==1)
                {
                    user.setStatus(true);
                    user.setCreateTime(new Date());
                    user.setPassword(CryptoUtils.md5(user.getPassword()));
                    if(loginUser.hasRole("ASSISTANT"))
                    {
                        user.setCreateUserId(loginUser.getCreateUserId());
                    }
                    else
                    {
                        user.setCreateUserId(loginUser.getId());
                    }

                    user.setRealName(realName);
                    user.setContact(contact);
                    user.setPhone(phone);
                    user.setQq(qq);
                    user.setLoginCount(0);
                    user.setDailiRole(0);
                    int num = userMapper.insert(user);
                    //资金余额
                    FundAccount fundAccount=new FundAccount();
                    fundAccount.setUserId(user.getId());
                    fundAccount.setBalance(balance);
                    fundAccount.setCreateTime(new Date());
                    fundAccount.setCreateUserId(loginUser.getId());
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
                //代理商
                else if(addMemberId==2)
                {
                    user.setStatus(true);
                    user.setCreateTime(new Date());
                    user.setPassword(CryptoUtils.md5(user.getPassword()));
                    if(loginUser.hasRole("ASSISTANT"))
                    {
                        user.setCreateUserId(loginUser.getCreateUserId());
                    }
                    else
                    {
                        user.setCreateUserId(loginUser.getId());
                    }
                    user.setLoginCount(0);
                    user.setDailiRole(0);
                    int num = userMapper.insert(user);
                    //资金余额
                    FundAccount fundAccount=new FundAccount();
                    fundAccount.setUserId(user.getId());
                    fundAccount.setBalance(balance);
                    fundAccount.setCreateTime(new Date());
                    fundAccount.setCreateUserId(loginUser.getId());
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
                //助理
                else
                {
                    user.setStatus(true);
                    user.setCreateTime(new Date());
                    user.setPassword(CryptoUtils.md5(user.getPassword()));
                    user.setCreateUserId(loginUser.getId());
                    user.setLoginCount(0);
                    user.setDailiRole(0);
                    int num = userMapper.insert(user);
                    Role role=roleService.getRoleByRoleCode(Roles.ASSISTANT.name());
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
                user.setCreateUserId(loginUser.getId());
                user.setLoginCount(0);
                user.setDailiRole(0);
                int num = userMapper.insert(user);
                //资金余额
                FundAccount fundAccount=new FundAccount();
                fundAccount.setUserId(user.getId());
                fundAccount.setBalance(balance);
                fundAccount.setCreateTime(new Date());
                fundAccount.setCreateUserId(loginUser.getId());
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
                customerListDetails.setDailiRole(user1.getDailiRole());
                customerListDetails.setRoleName(user.getRoles().get(0));
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
                customerListDetails.setDailiRole(user1.getDailiRole());
                customerListDetails.setRoleName(user.getRoles().get(0));
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
            if(user.hasRole("ASSISTANT"))
            {
                params.put("userId",user.getCreateUserId());
            }
            else
            {
                params.put("userId",user.getId());
            }

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
                customerListDetails.setDailiRole(user1.getDailiRole());
                customerListDetails.setRoleName(user.getRoles().get(0));
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

    /**
     * 待审核客户
     * @param params
     * @return
     */
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

    /**
     * 客户审核
     * @param customerId
     * @return
     */
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

    /**
     * 充值
     * @param params
     * @param user
     * @return
     */
    @Override
    public int Recharge(String customerId,String RechargeSum,LoginUser user) {

        Double  nums=Double.parseDouble(RechargeSum);
        FundAccount fundAccount=fundAccountMapper.selectByUserId(Long.parseLong(customerId));
        if(fundAccount==null)
        {
            FundAccount fundAccount1=new FundAccount();
            fundAccount1.setUserId(Long.parseLong(customerId));
            fundAccount1.setBalance(new BigDecimal(nums));
            fundAccount1.setCreateTime(new Date());
            fundAccount1.setCreateUserId(user.getId());
            fundAccount1.setUpdateTime(new Date());
            fundAccount1.setUpdateUserId(user.getId());
            fundAccountMapper.insert(fundAccount1);
            FundItem fundItem = new FundItem();
            fundItem.setFundAccountId(fundAccount1.getId());
            fundItem.setChangeAmount(new BigDecimal(nums));
            FundAccount fundAccount2=fundAccountMapper.selectByPrimaryKey(fundAccount1.getId());
            fundItem.setBalance(fundAccount2.getBalance());
            fundItem.setChangeTime(new Date());
            fundItem.setItemType("recharge"); //充值
            fundItemMapper.insert(fundItem);
            return  1;
        }
        else
        {
            Double sum;
            sum=nums +Double.parseDouble(fundAccount.getBalance().toString());
            FundAccount fundAccount1=new FundAccount();
            User user1=userMapper.selectByPrimaryKey(Long.parseLong(customerId));
            fundAccount1.setId(fundAccount.getId());
            fundAccount1.setUserId(user1.getId());
            fundAccount1.setBalance(new BigDecimal(sum));
            fundAccount1.setUpdateTime(new Date());
            fundAccount1.setUpdateUserId(user.getId());
            fundAccountMapper.updateByPrimaryKeySelective(fundAccount1);

            FundItem fundItem = new FundItem();
            fundItem.setFundAccountId(fundAccount.getId());
            fundItem.setChangeAmount(new BigDecimal(nums));
            FundAccount fundAccount2=fundAccountMapper.selectByPrimaryKey(fundAccount1.getId());
            fundItem.setBalance(fundAccount2.getBalance());
            fundItem.setChangeTime(new Date());
            fundItem.setItemType("recharge"); //充值
            fundItemMapper.insert(fundItem);
            return  1;
        }




    }

    /**
     * 退款
     * @param customerId
     * @param RechargeSum
     * @param user
     * @return
     */
    @Override
    public int Refound(String customerId, String RechargeSum, LoginUser user) {
        Double  nums=Double.parseDouble(RechargeSum);
        FundAccount fundAccount=fundAccountMapper.selectByUserId(Long.parseLong(customerId));
        if(fundAccount!=null)
        {
            Double sum;
            if(nums<=Double.parseDouble(fundAccount.getBalance().toString()))
            {
                sum=Double.parseDouble(fundAccount.getBalance().toString())-nums ;
                FundAccount fundAccount1=new FundAccount();
                User user1=userMapper.selectByPrimaryKey(Long.parseLong(customerId));
                fundAccount1.setId(fundAccount.getId());
                fundAccount1.setUserId(user1.getId());
                fundAccount1.setBalance(new BigDecimal(sum));
                fundAccount1.setUpdateTime(new Date());
                fundAccount1.setUpdateUserId(user.getId());
                fundAccountMapper.updateByPrimaryKeySelective(fundAccount1);

                FundItem fundItem = new FundItem();
                fundItem.setFundAccountId(fundAccount.getId());
                fundItem.setChangeAmount(new BigDecimal(nums));
                FundAccount fundAccount2=fundAccountMapper.selectByPrimaryKey(fundAccount1.getId());
                fundItem.setBalance(fundAccount2.getBalance());
                fundItem.setChangeTime(new Date());
                fundItem.setItemType("refund"); //退款
                fundItemMapper.insert(fundItem);
                return  1;

            }
            else
            {
                return  2;
            }

        }
        else
        {
            return  0;
        }




    }

    /**
     * 获取资金明细列表
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> fundAccountList(Map<String, Object> params,LoginUser user) {
        //判断角色获取对应的客户
        //管理员
        Long offset=Long.parseLong(params.get("offset").toString()) ;
        Long i=offset;
        if(user.hasRole("SUPER_ADMIN"))
        {
            Role role=roleMapper.selectByRoleCode("DISTRIBUTOR");
            params.put("roleId",role.getId());
            List<FundItemSum> fundItemSumList= fundItemMapper.selectByAdmin(params);
            for (FundItemSum item :fundItemSumList
                    ) {
                i++;
                FundAccount fundAccount=fundAccountMapper.selectByPrimaryKey(item.getFundAccountId());
                item.setId(i);
                if(fundAccount!=null)
                {
                    User user1=userMapper.selectByPrimaryKey(fundAccount.getUserId());
                    item.setUserName(user1.getUserName());
                }
            }
            Long total=fundItemMapper.selectByAdminCount(params);
            Map<String,Object> map=new HashMap<>();
            map.put("rows",fundItemSumList);
            map.put("total",total);
            return map;
        }
        //操作员
        else if(user.hasRole("COMMISSIONER"))
        {
            Role role=roleMapper.selectByRoleCode("DISTRIBUTOR");
            params.put("roleId",role.getId());
            params.put("userId",user.getId());
            List<FundItemSum> fundItemSumList= fundItemMapper.selectByZhuanYuan(params);
            for (FundItemSum item :fundItemSumList
                    ) {
                i++;
                FundAccount fundAccount=fundAccountMapper.selectByPrimaryKey(item.getFundAccountId());
                item.setId(i);
                if(fundAccount!=null)
                {
                    User user1=userMapper.selectByPrimaryKey(fundAccount.getUserId());
                    item.setUserName(user1.getUserName());
                }
            }
            Long total=fundItemMapper.selectByZhuanYuanCount(params);
            Map<String,Object> map=new HashMap<>();
            map.put("rows",fundItemSumList);
            map.put("total",total);
            return map;
        }
        //渠道商和代理商
        else if(user.hasRole("DISTRIBUTOR")||user.hasRole("AGENT")||user.hasRole("ASSISTANT"))
        {
            if(user.hasRole("ASSISTANT"))
            {
                params.put("createId",user.getCreateUserId());
            }
            else
            {
                params.put("createId",user.getId());
            }


            List<FundItemSum> fundItemSumList= fundItemMapper.selectByDAgent(params);

            for (FundItemSum item :fundItemSumList
                    ) {
                i++;
                item.setId(i);
                FundAccount fundAccount=fundAccountMapper.selectByPrimaryKey(item.getFundAccountId());
                if(fundAccount!=null)
                {
                    User user1=userMapper.selectByPrimaryKey(fundAccount.getUserId());
                    item.setUserName(user1.getUserName());
                }
            }
            Long total=fundItemMapper.selectByDAgentCount(params);
            Map<String,Object> map=new HashMap<>();
            map.put("rows",fundItemSumList);
            map.put("total",total);
            return map;

        }
        //客户
        else
        {
            params.put("userId",user.getId());

            List<FundItemSum> fundItemSumList= fundItemMapper.selectByCustomer(params);
            Long total=fundItemMapper.selectByCustomerCount(params);
            for (FundItemSum item :fundItemSumList
                    ) {
                FundAccount fundAccount=fundAccountMapper.selectByPrimaryKey(item.getFundAccountId());
                if(fundAccount!=null)
                {
                    User user1=userMapper.selectByPrimaryKey(fundAccount.getUserId());
                    item.setUserName(user1.getUserName());
                }
            }
            Map<String,Object> map=new HashMap<>();
            map.put("total",total);
            map.put("rows",fundItemSumList);
            return map;
        }

    }

    /**
     * 客户代理权限
     * @param params
     * @param user
     * @return
     */
    @Override
    public int updateDailiRole(Map<String, String[]> params, LoginUser loginUser) {
        String[] checkboxLength=params.get("length");
        int  length=Integer.parseInt(checkboxLength[0]);
        if(length!=0||loginUser!=null)
        {
            for(int i=0;i<length;i++)
            {
                String[] id=params.get("selectContent["+i+"][customerId]");
                Long  userId=Long.parseLong(id[0]);
                User user=new User();
                user.setId(userId);
                //设置拥有代理权限
                user.setDailiRole(1);
                user.setUpdateTime(new Date());
                user.setUpdateUserId(loginUser.getId());
                int a=userMapper.updateByPrimaryKeySelective(user);

            }
            return  1;
        }
        else
        {
            return 0;
        }
    }
}
