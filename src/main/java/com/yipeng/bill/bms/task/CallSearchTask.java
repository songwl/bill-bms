package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.dao.UserRoleMapper;
import com.yipeng.bill.bms.domain.UserRole;
import com.yipeng.bill.bms.service.SearchRateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/20.
 */
public class CallSearchTask {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private SearchRateService searchRateService;
    public void execute()
    {
        //统计每个用户的的搜索引擎完成率
        Map<String,Object> userMap=new HashMap<>();
        userMap.put("status",1);
        List<UserRole> userRoleList=userRoleMapper.selectByAllUserRole(userMap);

        for (UserRole userRole:userRoleList
                ) {
            int a=searchRateService.updateSearchRate(userRole);
        }
    }

}
