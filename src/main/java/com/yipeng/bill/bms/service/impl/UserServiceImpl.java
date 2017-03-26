package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.RoleService;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.LoginUser;
import com.yipeng.bill.bms.vo.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Override
    public int saveUser(User user) {
        user.setStatus(false);
        user.setCreateTime(new Date());
        user.setLoginCount(0);
        user.setPassword(CryptoUtils.md5(user.getPassword()));
        //Role role1= roleMapper.selectByRoleCode("SUPER_ADMIN");
        user.setCreateUserId(new Long(1));
        int c = userMapper.insert(user);
        Role role = roleService.getRoleByRoleCode(Roles.DISTRIBUTOR.name());
        if (role!=null) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());
            userRoleMapper.insert(userRole);

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

    /**
     * 获取用户个数
     * @return
     */
    @Override
    public Long getUserListCount() {
        return  userMapper.getUserListCount();
    }

    /**
     * 查找当前登录对象对应的客户
     * @param params
     * @return
     */
    @Override
    public List<User> userCreater(Map<String, Long> params) {
        Long createId=params.get("createId");
        List<User> userList=userMapper.userCreater(createId);
        List<User> userList1=new ArrayList<>() ;
        for (User user:userList
             ) {
            List<UserRole> userRoles=userRoleService.findUserRolesByUserId(user.getId());

            if(userRoles!=null)
            {
                UserRole userRole= userRoles.get(0);
                if(userRole.getRoleId()==6)
                {
                    User user1=new User();
                    user1.setId(user.getId());
                    user1.setUserName(user.getUserName());
                    userList1.add(user1);
                }
            }
        }
        return userList1;
    }

    /**
     * 获取所有操作员
     * @param params
     * @return
     */
    @Override
    public List<User> getUserAll(Map<String, Long> params) {
          long userRole=params.get("role");
          List<UserRole>  userRoleList=userRoleMapper.selectByRoleId(userRole);
          List<User> userList =new ArrayList<>();
          if(userRoleList!=null)
          {
              for (UserRole userRole1: userRoleList
                   ) {
                  User user=userMapper.selectByPrimaryKey(userRole1.getUserId());
                  userList.add(user);
              }

          }


        return userList;
    }

    /**
     * 搜索框获取的客户
     * @param loginUser
     * @return
     */
    @Override
    public List<User> getSearchUser(LoginUser loginUser,String way) {
        List<User> userList=new ArrayList<>();

        if(way.equals("1"))
        {
            List<User> userList1=userMapper.getUserByCreateId(loginUser.getId());
            return  userList1;
        }
        else if(way.equals("2"))
        {

            if (loginUser.hasRole("SUPER_ADMIN"))
            {
                List<User> userList1=  userMapper.getUserByCreateId(loginUser.getId());
                for (User user:userList1
                        ) {
                    UserRole userRole=userRoleMapper.selectByUserId(user.getId());
                    if(userRole.getRoleId()==4)
                    {
                        userList.add(user);
                    }
                }
                return  userList;
            }
            else if (loginUser.hasRole("COMMISSIONER"))
            {
                Map<String,Object> params=new HashMap<>();
                params.put("ascription",loginUser.getId());
                params.put("inMemberId",loginUser.getCreateUserId());
                List<User> userList1=userMapper.getSearchUserBillAscription(params);
                for (User user1: userList1
                        ) {

                    userList.add(user1);
                }

                return  userList;

            }
            else
            {
                List<User> userList1=userMapper.getUserByCreateId(loginUser.getId());
                for (User user: userList1
                     ) {
                    UserRole userRole=userRoleMapper.selectByUserId(user.getId());
                    Role role=roleMapper.selectByPrimaryKey(userRole.getRoleId());
                    if(role.getRoleCode().equals("AGENT"))
                    {
                        userList.add(user);
                    }
                }

                return  userList;

            }

        }
        else
        {
            return  userList;
        }


    }

}
