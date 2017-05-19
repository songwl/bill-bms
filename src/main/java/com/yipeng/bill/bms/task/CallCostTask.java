package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.BillAccountAndItemService;
import com.yipeng.bill.bms.service.BillCallCostService;
import com.yipeng.bill.bms.service.SearchRateService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by song on 17/3/26.
 * 自动计算费用任务
 */
public class CallCostTask {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillCallCostService billCallCostService;
    @Autowired
     private BillAccountAndItemService billAccountAndItemService;
    @Autowired
    private  BillPriceMapper billPriceMapper;
    @Autowired
    private  UserRoleMapper userRoleMapper;

    @Autowired
    private SearchRateService searchRateService;
    public void execute(){
        int offset = 0;
        int limit = 50; //每次查询50条

        while (true) {

            //1.查询有效的计费单Bill
            Map<String, Object> params = new HashMap<>();
            params.put("state", 2);
            params.put("offset", offset);
            params.put("limit", limit);
            List<Bill> billList = billMapper.selectBill(params);
            if (!CollectionUtils.isEmpty(billList)) {
                for (Bill bill : billList) {
                    billCallCostService.updateCallCost(bill);

                }
            }
            if (CollectionUtils.isEmpty(billList) || billList.size()<limit) { //查询为空或者不足limit条,说明已查询结束
                break;
            }

            offset += limit; //查询下一页

        }
        Map<String,Object> map=new  HashMap<>();
        Calendar now =Calendar.getInstance();
        map.put("year",now.get(Calendar.YEAR));
        map.put("month",now.get(Calendar.MONTH)+1);
        map.put("day",now.get(Calendar.DATE));
        //在这里开始计算流水和用户余额
        List<BillPrice> billPriceList=billPriceMapper.selectByOutmemberList();//获取今日所有人付款人
        if(!CollectionUtils.isEmpty(billPriceList))
        {
            for (BillPrice item:billPriceList
                    ) {
                map.put("userId",item.getOutMemberId());
                billAccountAndItemService.BillAccountAndItem(map);
            }
        }

          Map<String,Object> userMap=new HashMap<>();
          userMap.put("status",1);
          List<UserRole> userRoleList=userRoleMapper.selectByAllUserRole(userMap);

        for (UserRole userRole:userRoleList
             ) {

           int a=searchRateService.updateSearchRate(userRole);
        }



    }

}
