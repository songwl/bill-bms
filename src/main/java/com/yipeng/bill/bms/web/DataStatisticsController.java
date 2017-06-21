package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.dao.BillCommissionerStatisticsMapper;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.BillCommissionerStatistics;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.model.BillOptimizations;
import com.yipeng.bill.bms.model.DistributorData;
import com.yipeng.bill.bms.service.DataStatisticsService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by Administrator on 2017/4/26.
 */
@Controller
@RequestMapping(value = "/dataStatistics")
public class DataStatisticsController extends BaseController {

    @Autowired
    private DataStatisticsService dataStatisticsService;
    @Autowired
    private BillCommissionerStatisticsMapper billCommissionerStatisticsMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 统计调点击数据页面
     * @return
     */
    @RequestMapping(value = "/getBillOptimization")
    public  String getBillOptimization(ModelMap modelMap)
    {
        LoginUser loginUser=this.getCurrentAccount();
        if(loginUser!=null)
        {

            List<BillOptimizations> billOptimizationsList=dataStatisticsService.getBillOptimization();
            modelMap.put("optimizationsList",billOptimizationsList);
        }

        return  "/dataStatistics/getBillOptimization";
    }

    /**
     * 统计调点击数据页面
     * @return
     */
    @RequestMapping(value = "/distributorData")
    public  String distributorData(ModelMap modelMap)
    {
        LoginUser loginUser=this.getCurrentAccount();
        if(loginUser!=null)
        {
            Map<String,Object> params=new HashedMap();
            params.put("loginUser",loginUser);
            List<DistributorData> distributorDataList=dataStatisticsService.distributorData(params,loginUser);

            modelMap.put("distributorDataList",distributorDataList);
        }
        return  "/dataStatistics/distributorData";
    }

    /**
     * 操作员统计数据
     * @return
     */
    @RequestMapping(value = "/commissionerData")
    public  String commissionerData(ModelMap modelMap)
    {
        Map<String, Object> mapCaozuo=new HashMap<>();
        Calendar now = Calendar.getInstance();
        mapCaozuo.put("year",now.get(Calendar.YEAR));
        mapCaozuo.put("month",now.get(Calendar.MONTH)+1);
        mapCaozuo.put("day",now.get(Calendar.DATE));
        LoginUser loginUser=this.getCurrentAccount();
        if(loginUser!=null)
        {
            Map<String,Object> params=new HashedMap();
            params.put("loginUser",loginUser);

            List<BillCommissionerStatistics> billCommissionerStatisticsList=billCommissionerStatisticsMapper.selectByGetAll(mapCaozuo);
           /* List<DistributorData> distributorDataList=dataStatisticsService.commissionerData(params,loginUser);
*/
           List<DistributorData> distributorDataList=new ArrayList<>();
            for (BillCommissionerStatistics item:billCommissionerStatisticsList
                 ) {
                DistributorData distributorData=new DistributorData();
                User user1=userMapper.selectByPrimaryKey(item.getUserid());
                distributorData.setUserName(user1.getUserName());
                distributorData.setWeekCost(Double.parseDouble(item.getWeekCost().toString()));
                distributorData.setMonthCost(Double.parseDouble(item.getMonthCost().toString()));
                distributorData.setAllCost(Double.parseDouble(item.getAllCost().toString()));
                distributorData.setBillCount(item.getBillCount());
                distributorData.setBillStandard(item.getBillApprovalRate().toString());
                distributorData.setKeywordsStandard(item.getKeywordsApprovalRate().toString());
                distributorData.setMonthAddBill(item.getBillMonthAddCount());
                distributorData.setExpectedPerformance(item.getUserExpectAchievement().toString());
                distributorDataList.add(distributorData);

            }
            modelMap.put("distributorDataList",distributorDataList);
        }


        return  "/dataStatistics/commissionerData";
    }


}
