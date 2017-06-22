package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.vo.LoginUser;

import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/3/18.
 */
public interface OperatorService {

    /**
     * 添加操作员
     * @param user
     * @return
     */
    int saveOperator(User user, LoginUser users,String roleName);

    /**
     * 获取操作员列表
     * @param params
     * @return
     */
    Map<String, Object> getOperator(Map<String, Object> params);
    /**
     * 修改信息
     * @param params
     * @return
     */
    int updateOperator(User user, LoginUser loginUser);
    /**
     * 重置密码
     * @param params
     * @return
     */
    int updatePwd(User user, LoginUser loginUser);

    int deleteUser(User user, LoginUser loginUser);
}
