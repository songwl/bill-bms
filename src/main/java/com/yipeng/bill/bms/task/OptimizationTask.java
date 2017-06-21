package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.BillOptimizationMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.BillOptimization;
import com.yipeng.bill.bms.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/22.
 */
public class OptimizationTask {
    @Autowired
    private RemoteService remoteService;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillOptimizationMapper billOptimizationMapper;
    public void execute() throws IOException, NoSuchAlgorithmException {
        //统计每日的优化数据
        int offset = 0;
        int limit = 50; //每次查询50条
        while (true) {
            //1.查询有效的计费单Bill
            Map<String, Object> params = new HashMap<>();
            params.put("state", 2);
            params.put("opstate", 1);
            //初始排名
            params.put("offset", offset);
            params.put("limit", limit);

            List<Bill> billList = billMapper.selectBill(params);
            if (!CollectionUtils.isEmpty(billList)) {
                for (Bill bill : billList) {
                    //结算一次优化点击次数
                    //生成优化点击次数记录

                    BillOptimization billOptimization=new BillOptimization();
                    billOptimization.setBillId(bill.getId());
                    billOptimization.setOptimizationCount(bill.getDayOptimization());
                    billOptimization.setOptimizationDate(new Date());
                    billOptimizationMapper.insert(billOptimization);
                    //日优化
                     int single=bill.getDayOptimization();
                     //总优化
                     int All=bill.getAllOptimization();
                     bill.setAllOptimization(single+All);
                     billMapper.updateByPrimaryKeySelective(bill);
                }
            }
            if (CollectionUtils.isEmpty(billList) || billList.size()<limit) { //查询为空或者不足limit条,说明已查询结束
                break;
            }
            offset += limit; //查询下一页
        }



    }
}
