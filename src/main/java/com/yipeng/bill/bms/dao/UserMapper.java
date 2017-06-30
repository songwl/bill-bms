package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User selectByPrimaryKeySelective(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String userName);

    List<User> selectList(int limit, int offset);

    Long getUserListCount();

    List<User> userCreater(Long createId);

    /**
     * 获取角色
     *
     * @param params
     * @return
     */
    List<User> getQueryUserAll(Map<String, Object> params);

    /**
     * 搜索框的客户
     *
     * @param userId
     * @return
     */
    List<User> getUserByCreateId(Long userId);

    //待审核客户
    List<User> selectByReviewUser(Map<String, Object> params);

    //待审核客户数量
    Long selectByReviewUserCount();

    //用户权限
    List<User> getUserRoleByCreateId(Map<String, Object> params);

    //用户权限个数
    Long getUserRoleByCreateIdCount(Map<String, Object> params);

    List<User> getUserBillAscription(Map<String, Object> params);

    Long getUserBillAscriptionCount(Map<String, Object> params);

    List<User> getSearchUserBillAscription(Map<String, Object> params);

    List<User> selectAllUsers(String role);

    List<User> selectAddressee(String currentid);

    //获取已开通权限的渠道商
    List<User> selectDistributor();

    //根据用户id得到角色名称
    String selectRoleName(String currentId);

    List<User> selectUserNameById(Map<String, Object> map);

    List<Map<String, Object>> selectCustomer(Long userId);
}