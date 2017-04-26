package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.model.BillOptimizations;
import com.yipeng.bill.bms.model.DistributorData;
import com.yipeng.bill.bms.service.DataStatisticsService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/26.
 */
@Controller
@RequestMapping(value = "/dataStatistics")
public class DataStatisticsController extends  BaseController{

    @Autowired
    private DataStatisticsService dataStatisticsService;

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
            Map<String,Object> params=new HashedMap();
            params.put("loginUser",loginUser);
            List<BillOptimizations> optimizationsList=dataStatisticsService.getBillOptimization(params,loginUser);

            modelMap.put("optimizationsList",optimizationsList);
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
     * 统计调点击数据页面
     * @return
     */
    @RequestMapping(value = "/commissionerData")
    public  String commissionerData()
    {


        return  "/dataStatistics/commissionerData";
    }


}
