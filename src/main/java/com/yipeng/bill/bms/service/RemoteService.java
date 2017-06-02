package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.vo.CustomerOptimizationResult;
import com.yipeng.bill.bms.vo.CustomerRankingParam;
import com.yipeng.bill.bms.vo.CustomerRankingResult;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by song on 17/4/11.
 * 第三方远程服务
 */
public interface RemoteService {

    /**
     * 远程获取用户排名
     * @param customerRankingParam
     * @return
     */
    CustomerRankingResult getCustomerRanking(CustomerRankingParam customerRankingParam) throws IOException, NoSuchAlgorithmException;

    CustomerOptimizationResult getOptimizationApi(Map<String,String> params);


    String getPriceApiId(Map<String,String> params);
}
