package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.model.BillOptimizations;
import com.yipeng.bill.bms.model.DistributorData;
import com.yipeng.bill.bms.vo.LoginUser;

import java.util.List;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/4/26.
 */
public interface DataStatisticsService {
    /**
     * 统计调点击
     * @param params
     * @param user
     * @return
     */
    List<BillOptimizations> getBillOptimization();

    /**
     * 渠道商数据
     * @param params
     * @param user
     * @return
     */
    List<DistributorData> distributorData(Map<String,Object> params, LoginUser user);
    /**
     * 操作员数据
     * @param params
     * @param user
     * @return
     */
    List<DistributorData> commissionerData(Map<String,Object> params, LoginUser user);

}
