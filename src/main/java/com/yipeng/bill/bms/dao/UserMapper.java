package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String userName);

    List<User> selectList( int limit, int offset);
    Long getUserListCount();
    List<User> userCreater(Long createId);

    /**
     * 获取角色
     * @param params
     * @return
     */
    List<User> getQueryUserAll(Map<String,Object> params);

    /**
     * 搜索框的客户
     * @param userId
     * @return
     */
    List<User> getUserByCreateId(Long userId);

    List<User> selectByReviewUser(Map<String,Object> params);

    Long  selectByReviewUserCount();
 


    List<User> getUserRoleByCreateId(Map<String,Object> params);
    Long getUserRoleByCreateIdCount(Map<String,Object> params);

    List<User> getUserBillAscription(Map<String,Object> params);
    Long getUserBillAscriptionCount(Map<String,Object> params);

    List<User> getSearchUserBillAscription(Map<String,Object> params);
 
}