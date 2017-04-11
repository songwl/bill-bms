package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.CustomerRankingParam;
import com.yipeng.bill.bms.vo.CustomerRankingResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by song on 17/4/11.
 * 排名更新任务
 */
public class RankingTask {

    @Autowired
    private RemoteService remoteService;

    public void execute(){
        //1.获取自己数据库数据（需要同步排名的记录）

        //2.根据数据作为参数 customerRankingParam，httpclient请求这个别人的接口
        CustomerRankingParam customerRankingParam = new CustomerRankingParam();
        CustomerRankingResult customerRankingResult = remoteService.getCustomerRanking(customerRankingParam);

        //3.根据别人返回的数据 customerRankingResult ，处理自己的业务（更新排名等操作）

    }
}
