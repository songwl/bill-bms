package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.User;

import java.util.List;
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
    int saveOperator(User user);

    /**
     * 获取操作员列表
     * @param params
     * @return
     */
    Map<String, Object> getOperator(Map<String, Object> params);
    /**
     * 冻结账户
     * @param params
     * @return
     */
    int updateUserState(Long userId);

}
