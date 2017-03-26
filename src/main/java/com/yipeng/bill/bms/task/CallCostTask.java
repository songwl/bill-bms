package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.BillCallCostService;
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
    }

}
