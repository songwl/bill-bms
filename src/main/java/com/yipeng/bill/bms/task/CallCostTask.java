package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.BillPriceMapper;
import com.yipeng.bill.bms.dao.LogsMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.BillPrice;
import com.yipeng.bill.bms.domain.Logs;
import com.yipeng.bill.bms.service.BillAccountAndItemService;
import com.yipeng.bill.bms.service.BillCallCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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
    private BillPriceMapper billPriceMapper;
    @Autowired
    private LogsMapper logsMapper;

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
        //统计每个用户的资金流水和余额
        Map<String,Object> map=new  HashMap<>();
        Calendar now =Calendar.getInstance();
        map.put("year",now.get(Calendar.YEAR));
        map.put("month",now.get(Calendar.MONTH)+1);
        map.put("day",now.get(Calendar.DATE));
        map.put("itemType","cost");
        //在这里开始计算流水和用户余额
        List<BillPrice> billPriceList=billPriceMapper.selectByOutmemberList();//获取所有人付款人
        if(!CollectionUtils.isEmpty(billPriceList)) {

            try {
                  for (BillPrice item : billPriceList
                        ) {
                    map.put("userId", item.getOutMemberId());
                    try
                    {
                        int a=     billAccountAndItemService.BillAccountAndItem(map);
                    }
                    catch (Exception e)
                    {
                        Logs logs = new Logs();
                        logs.setOpobj("1");
                        logs.setOpremake("错误Id："+item.getOutMemberId()+" 错误信息："+e.getMessage());
                        logs.setOptype(11111);
                        logs.setUserid(new Long(1));
                        logs.setCreatetime(new Date());
                        int a = logsMapper.insert(logs);
                        continue;
                    }


                }
            } catch (Exception e) {
                Logs logs = new Logs();
                logs.setOpobj("1");
                logs.setOpremake( " 错误信息："+e.getMessage()+"错误付款人："+map.get("userId"));
                logs.setOptype(11111);
                logs.setUserid(new Long(1));
                logs.setCreatetime(new Date());
                int a = logsMapper.insert(logs);
                System.out.print(e);
            }
        }




    }

}
