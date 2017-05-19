package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.SearchRateService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/5/19.
 */
@Service
public class SearchRateServiceImpl implements SearchRateService {

    @Autowired
    private SearchenginecompletionrateMapper searchenginecompletionrateMapper;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillCostMapper billCostMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Override
    public int updateSearchRate(UserRole userRole) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        try{

         Searchenginecompletionrate searchenginecompletionrate=new Searchenginecompletionrate();
        //组合对象
        searchenginecompletionrate.setUserid(userRole.getUserId());

        double allcompleteness=allcompleteness(userRole);//总完成率
        searchenginecompletionrate.setAllcompleteness(Double.parseDouble(df.format(allcompleteness)));

        double baiducompleteness=baiduCompleteness(userRole);//百度完成率
        searchenginecompletionrate.setBaiducompleteness(Double.parseDouble(df.format(baiducompleteness)));

        double baiduwapcompleteness=baiduWapCompleteness(userRole);//手机百度完成率
        searchenginecompletionrate.setBaiduwapcompleteness(Double.parseDouble(df.format(baiduwapcompleteness)));

        double sanliulingcompleteness=sanliulingCompleteness(userRole);//360
        searchenginecompletionrate.setSanliulingcompleteness(Double.parseDouble(df.format(sanliulingcompleteness)));

        double sougouCompleteness=sougouCompleteness(userRole);//搜狗
        searchenginecompletionrate.setSougoucompleteness(Double.parseDouble(df.format(sougouCompleteness)));

        double shenmaCompleteness=shenmaCompleteness(userRole);//神马
        searchenginecompletionrate.setShenmacompleteness(Double.parseDouble(df.format(shenmaCompleteness)));

        searchenginecompletionrate.setCreatetime(new Date());

         searchenginecompletionrateMapper.insert(searchenginecompletionrate);
        }catch (Exception e)
        {
            throw e;
        }
        return 0;
    }

    //总完成率
    public double allcompleteness(UserRole userRole) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        double result=0;

        if(userRole.getRoleId()==1||userRole.getRoleId()==4||userRole.getRoleId()==5)
        {
            //1,先获取对应所有的订单
            Map<String,Object> billListparams=new HashMap<>();
            billListparams.put("userId",userRole.getUserId());
            billListparams.put("state",2);
            //订单数（优化中）
            List<Bill> billList=billMapper.selectByInMemberId(billListparams);
            Map<String,Object> billparam=new HashMap<>();
            billparam.put("userId",userRole.getUserId());
            Date tomorrow = calendar.getTime();
            String str1=formatter.format(tomorrow);
            billparam.put("date",str1);
            int keywordsCount=billCostMapper.selectByBillCostOfDay(billparam);


            //总完成率(今日达标数/总订单数)
            double AllCompleteness=0;
            if(billList.size()>0)
            {
                AllCompleteness=((double)keywordsCount/billList.size())*100;
            }
            else
            {
                AllCompleteness=0.00;
            }
            result=AllCompleteness;

        }
        //操作员
        else  if (userRole.getRoleId()==3)
        {
            //1,先获取对应所有的订单
            Map<String,Object> billparam=new HashMap<>();
            User user1=userMapper.selectByPrimaryKey(userRole.getUserId());
            billparam.put("userId",user1.getCreateUserId());
            billparam.put("billAscription",userRole.getUserId());
            billparam.put("state",2);
            List<Bill> billList=billMapper.selectByInMemberId(billparam);
            Date tomorrow = calendar.getTime();
            String str1=formatter.format(tomorrow);
            billparam.put("date",str1);
            int keywordsCount=billCostMapper.selectByBillCostOfDay(billparam);
            //总完成率(今日达标数/总订单数)
            double AllCompleteness=0;
            if(billList.size()>0)
            {
                AllCompleteness=((double)keywordsCount/billList.size())*100;
            }
            else
            {
                AllCompleteness=0.00;
            }
            result=AllCompleteness;
        }
        else if (userRole.getRoleId()==6)
        {
            Map<String,Object> billListparams=new HashMap<>();
            billListparams.put("state",2);
            billListparams.put("userId",userRole.getUserId());
            List<Bill> billList=billMapper.getBillCountByCmm(billListparams);

            Map<String,Object> dateMap=new  HashMap<>();
            dateMap.put("outMemberId",userRole.getUserId());
            Date tomorrow = calendar.getTime();
            String str1=formatter.format(tomorrow);
            dateMap.put("date",str1);
            int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
            //总完成率(今日达标数/总订单数)
            double AllCompleteness=0;
            if(billList.size()>0)
            {
                AllCompleteness=((double)keywordsCount/billList.size())*100;
            }
            else
            {
                AllCompleteness=0.00;
            }
            result=AllCompleteness;
        }
        return   result;
    }
    //百度完成率
    public double baiduCompleteness(UserRole userRole) {
        double result=0;
        String baidu="百度";
        if(userRole.getRoleId()==1||userRole.getRoleId()==4||userRole.getRoleId()==5)
        {

            Double baiduCompleteness=searchCompleteness(baidu,userRole);

            result=baiduCompleteness;
        }
        //操作员
        else  if (userRole.getRoleId()==3)
        {

            Double baiduCompleteness=searchCompletenessBycmm(baidu,userRole);
            result=baiduCompleteness;
        }
        else if (userRole.getRoleId()==6)
        {

            Double baiduCompleteness=searchCompletenessBycus(baidu,userRole);
            result=baiduCompleteness;
        }
        return result;
    }
    //手机百度完成率
    public double baiduWapCompleteness(UserRole userRole) {
        String baiduWap="手机百度";
        double result=0;
        if(userRole.getRoleId()==1||userRole.getRoleId()==4||userRole.getRoleId()==5)
        {

            Double baiduWapCompleteness=searchCompleteness(baiduWap,userRole);
            result=baiduWapCompleteness;
        }
        //操作员
        else  if (userRole.getRoleId()==3)
        {

            Double baiduWapCompleteness=searchCompletenessBycmm(baiduWap,userRole);
            result=baiduWapCompleteness;

        }
        else if (userRole.getRoleId()==6)
        {

            Double baiduWapCompleteness=searchCompletenessBycus(baiduWap,userRole);
            result=baiduWapCompleteness;
        }
        return result;
    }
    //360
    public double sanliulingCompleteness(UserRole userRole) {

        String sanliuling="360";
        double result=0;
        if(userRole.getRoleId()==1||userRole.getRoleId()==4||userRole.getRoleId()==5)
        {

            Double sanliulingCompleteness=searchCompleteness(sanliuling,userRole);
            result=sanliulingCompleteness;
        }
        //操作员
        else  if (userRole.getRoleId()==3)
        {

            Double sanliulingCompleteness=searchCompletenessBycmm(sanliuling,userRole);
            result=sanliulingCompleteness;

        }
        else if (userRole.getRoleId()==6)
        {
            Double sanliulingCompleteness=searchCompletenessBycus(sanliuling,userRole);
            result=sanliulingCompleteness;
        }
        return result;
    }
    //搜狗
    public double sougouCompleteness(UserRole userRole) {

        String sougou="搜狗";
        double result=0;
        if(userRole.getRoleId()==1||userRole.getRoleId()==4||userRole.getRoleId()==5)
        {

            Double sougouCompleteness=searchCompleteness(sougou,userRole);
            result=sougouCompleteness;
        }
        //操作员
        else  if (userRole.getRoleId()==3)
        {
            Double sougouCompleteness=searchCompletenessBycmm(sougou,userRole);
            result=sougouCompleteness;

        }
        else if (userRole.getRoleId()==6)
        {
            Double sougouCompleteness=searchCompletenessBycus(sougou,userRole);
            result=sougouCompleteness;
        }
        return result;
    }
    //神马
    public double shenmaCompleteness(UserRole userRole) {

        String shenma="神马";
        double result=0;
        if(userRole.getRoleId()==1||userRole.getRoleId()==4||userRole.getRoleId()==5)
        {

            Double shenmaCompleteness=searchCompleteness(shenma,userRole);
            result=shenmaCompleteness;

        }
        //操作员
        else  if (userRole.getRoleId()==3)
        {
            Double shenmaCompleteness=searchCompletenessBycmm(shenma,userRole);
            result=shenmaCompleteness;

        }
        else if (userRole.getRoleId()==6)
        {
            Double shenmaCompleteness=searchCompleteness(shenma,userRole);
            result=shenmaCompleteness;
        }
        return result;
    }
    //搜索引擎完成率（管理员，渠道商，代理商）
    public  Double searchCompleteness(String search, UserRole userRole)
    {
        //今日达标数
        //1,先获取对应所有的订单
        Map<String,Object> billparam=new HashMap<>();
        billparam.put("userId",userRole.getUserId());
        billparam.put("state",2);
        billparam.put("searchName",search);
        List<Bill> billList=billMapper.selectByInMemberId(billparam);
        //判断哪些订单今日达标
        int standardSum=0;//今天达标数
        if(billList.size()>0)
        {
            for (Bill bill:billList
                    ) {
                //对应订单排名标准
                BillPrice billPrice=new BillPrice();
                billPrice.setBillId(bill.getId());
                billPrice.setInMemberId(userRole.getUserId());
                List<BillPrice> billPriceList=new ArrayList<>();
                billPriceList=billPriceMapper.selectByBillPrice(billPrice);
                //判断今日订单达到哪个标准
                for (BillPrice item:billPriceList
                        ) {
                    if(bill.getNewRanking()<=item.getBillRankingStandard())
                    {
                        standardSum+=1;
                        break;
                    }
                }
            }
        }
        double Completeness=0.0;
        if(billList.size()>0)
        {
            Completeness=((double)standardSum/billList.size())*100;
        }

        return Completeness;
    }
    //搜索引擎完成率（操作员）
    public  Double searchCompletenessBycmm(String search, UserRole userRole)
    {
        //今日达标数
        //1,先获取对应所有的订单
        Map<String,Object> billparam=new HashMap<>();
        User user1=userMapper.selectByPrimaryKey(userRole.getUserId());
        billparam.put("userId",user1.getCreateUserId());
        billparam.put("state",2);
        billparam.put("billAscription",userRole.getUserId());
        billparam.put("searchName",search);
        List<Bill> billList=billMapper.selectByInMemberId(billparam);
        //判断哪些订单今日达标
        int standardSum=0;//今天达标数
        if(billList.size()>0)
        {
            for (Bill bill:billList
                    ) {
                //对应订单排名标准
                BillPrice billPrice=new BillPrice();
                billPrice.setBillId(bill.getId());
                billPrice.setInMemberId(user1.getCreateUserId());
                List<BillPrice> billPriceList=new ArrayList<>();
                billPriceList=billPriceMapper.selectByBillPrice(billPrice);
                //判断今日订单达到哪个标准
                for (BillPrice item:billPriceList
                        ) {
                    if(bill.getNewRanking()<=item.getBillRankingStandard())
                    {
                        standardSum+=1;
                        break;
                    }
                }
            }
        }
        double Completeness=0.0;
        if(billList.size()>0)
        {
            Completeness=((double)standardSum/billList.size())*100;
        }

        return Completeness;
    }
    //搜索引擎完成率（客户）
    public  Double searchCompletenessBycus(String search,  UserRole userRole)
    {
        //今日达标数
        //1,先获取对应所有的订单
        Map<String,Object> billparam=new HashMap<>();
        billparam.put("userId",userRole.getUserId());
        billparam.put("state",2);
        billparam.put("searchName",search);
        List<Bill> billList=billMapper.getBillCountByCmm(billparam);
        //判断哪些订单今日达标
        int standardSum=0;//今天达标数
        if(billList.size()>0)
        {
            for (Bill bill:billList
                    ) {
                //对应订单排名标准
                BillPrice billPrice=new BillPrice();
                billPrice.setBillId(bill.getId());
                billPrice.setOutMemberId(userRole.getUserId());
                List<BillPrice> billPriceList=new ArrayList<>();
                billPriceList=billPriceMapper.selectByBillPrice(billPrice);
                //判断今日订单达到哪个标准
                for (BillPrice item:billPriceList
                        ) {
                    if(bill.getNewRanking()<=item.getBillRankingStandard())
                    {
                        standardSum+=1;
                        break;
                    }
                }
            }
        }
        double Completeness=0.0;
        if(billList.size()>0)
        {
            Completeness=((double)standardSum/billList.size())*100;
        }

        return Completeness;
    }
}
