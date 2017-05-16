package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.BillPrice;
import com.yipeng.bill.bms.domain.FundAccount;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.service.HomeService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.runtime.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/4/10.
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BillCostMapper billCostMapper;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private  BillPriceMapper billPriceMapper;
    /**
     * 首页详情
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> homeDetails(LoginUser loginUser) {
        //转换时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String daynow=formatter.format(new Date());
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        //当前时间
        Date dateNow=null;
        try {
            dateNow = format1.parse(daynow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取上一个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        //上一个月的日期
        Date preMonth=null;
        try {
            preMonth = format1.parse( formatter.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //上一个月的天数
        int monthPreCount=0;
        monthPreCount=getDaysOfMonth(preMonth);

        calendar.setTime(dateNow);
        int monthNowCount = calendar.get(Calendar.DAY_OF_MONTH);
        String seriesNowMonth="";
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateNow); // 设置为当前时间
        calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH)+1); // 设置为下一个月
        //下一个月的日期
        Date nextMonth=null;
        try {
            nextMonth = format1.parse( formatter.format(calendar1.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, String> mapss = getFirstday_Lastday_Month(nextMonth);
        String fistDaysNow=mapss.get("first");
        //将第一日转换为Date格式
        Date fistDateNow=null;
        try {
            fistDateNow = format1.parse(fistDaysNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Map<String, Object> map=new HashMap<>();
        Map<String, Object> params=new HashMap<>();
        params.put("userId",loginUser.getId());
        if(loginUser.hasRole("SUPER_ADMIN"))
        {

            //客户数
            Role role=roleMapper.selectByRoleCode("DISTRIBUTOR");
            params.put("roleId",role.getId());

            Long count=UserCount(params);
            map.put("UserCount",count);
            //本月消费
            Double MonthConsumption=MonthConsumption(params);
            map.put("MonthConsumption",MonthConsumption);
            params.put("state",2);

            //本日消费
            Double DayConsumption=DayConsumption(params);
            map.put("DayConsumption",DayConsumption);

            //当前任务数
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListCount(params);
            map.put("AllbillCount",AllbillCount);
            //今日达标数
            //1,先获取对应所有的订单
            Map<String,Object> billparam=new HashMap<>();
            billparam.put("userId",loginUser.getId());
            billparam.put("state",2);
            //订单数（优化中）
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
                    billPrice.setInMemberId(loginUser.getId());
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
            map.put("standardSum",standardSum);

            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            //总完成率(今日达标数/总订单数)
            double AllCompleteness=0;
            if(billList.size()>0)
            {
                AllCompleteness=((double)standardSum/billList.size())*100;
            }

            map.put("AllCompleteness",df.format(AllCompleteness));
            //百度完成率
            String baidu="百度";
            Double baiduCompleteness=searchCompleteness(baidu,loginUser);
            map.put("baiduCompleteness",df.format(baiduCompleteness));
            //百度wap完成率
            String baiduWap="手机百度";
            Double baiduWapCompleteness=searchCompleteness(baiduWap,loginUser);
            map.put("baiduWapCompleteness",df.format(baiduWapCompleteness));
            //360完成率
            String sanliuling="360";
            Double sanliulingCompleteness=searchCompleteness(sanliuling,loginUser);
            map.put("sanliulingCompleteness",df.format(sanliulingCompleteness));

            //搜狗完成率
            String sougou="搜狗";
            Double sougouCompleteness=searchCompleteness(sougou,loginUser);
            map.put("sougouCompleteness",df.format(sougouCompleteness));

            //手机360
            String sanliulingWap="手机360";
            Double sanliulingWapCompleteness=searchCompleteness(sanliulingWap,loginUser);
            map.put("sanliulingWapCompleteness",df.format(sanliulingWapCompleteness));
            //手机搜狗
            String sougouWap="手机搜狗";
            Double sougouWapWapCompleteness=searchCompleteness(sougouWap,loginUser);
            map.put("sougouWapWapCompleteness",df.format(sougouWapWapCompleteness));
            //手机搜狗
            String shenma="神马";
            Double shenmaCompleteness=searchCompleteness(shenma,loginUser);
            map.put("shenmaCompleteness",df.format(shenmaCompleteness));
            //某个月的第一天和最后一天
            Map<String, String> maps = getFirstday_Lastday_Month(dateNow);
            String fistDay=maps.get("first");
            //将第一日转换为Date格式
            Date fistDate=null;
            try {
                fistDate = format1.parse(fistDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String seriesLastMonth="";
            //上个月消费
            String seriesLastMonthSum="";
            //循环获取上个月每天的达标数
            Map<String,Object> dateMap=new  HashMap<>();
            dateMap.put("userId",loginUser.getId());
            //判断Y轴任务数的显示
            int MaxYbylast=0;
            //判断Y轴消费的显示
            double MaxYbylastCost=0;
            for(int i=0;i<monthPreCount;i++)
            {
                calendar.setTime(fistDate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str=formatter.format(tomorrow);
                dateMap.put("date",str);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);

                //比较达标最大数
                if(MaxYbylast<=keywordsCount)
                {
                    MaxYbylast=keywordsCount;
                }
                //比较消费最大数
                if(MaxYbylastCost<=keywordsSum)
                {
                    MaxYbylastCost=keywordsSum;
                }
                seriesLastMonth+=keywordsCount+",";
                seriesLastMonthSum+=keywordsSum+",";

            }
            //上个月的达标数
            map.put("seriesLastMonth",seriesLastMonth);
            map.put("seriesLastMonthSum",seriesLastMonthSum);
            //判断Y轴的显示
            int MaxYbyNew=0;
            double MaxYbyNewCost=0;
            String seriesNowMonthSum="";
            for(int i=0;i<monthNowCount;i++)
            {
                calendar.setTime(fistDateNow);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str1=formatter.format(tomorrow);
                dateMap.put("date",str1);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);
                //比较达标最大数
                if(MaxYbyNew<=keywordsCount)
                {
                    MaxYbyNew=keywordsCount;
                }
                if(MaxYbyNewCost<=keywordsSum)
                {
                    MaxYbyNewCost=keywordsSum;
                }
                seriesNowMonth+=keywordsCount+",";
                seriesNowMonthSum+=keywordsSum+",";

            }
            String yAxis="";
            if(MaxYbyNew!=0)
            {
                yAxis=getYAxis(MaxYbyNew);
            }
            else
            {
                yAxis=getYAxis(MaxYbylast);
            }

            String yAxisSum="";
            if(MaxYbyNewCost!=0)
            {
                yAxisSum=getYAxisSum(MaxYbyNewCost);
            }
            else
            {
                yAxisSum=getYAxisSum(MaxYbylastCost);
            }

            map.put("yAxis",yAxis);
            map.put("yAxisSum",yAxisSum);

            map.put("seriesNowMonth",seriesNowMonth);
            map.put("seriesNowMonthSum",seriesNowMonthSum);
            return map;
        }
        //渠道商和代理商
        else if(loginUser.hasRole("DISTRIBUTOR")||loginUser.hasRole("AGENT"))
        {
            //账户余额
            FundAccount fundAccount=fundAccountMapper.selectByUserId(loginUser.getId());
            if (fundAccount==null)
            {

                map.put("balance",0);
            }
            else
            {
                map.put("balance",fundAccount.getBalance());

            }
            //月总消费
            Double MonthConsumption=MonthConsumption(params);
            map.put("MonthConsumption",MonthConsumption);
            //本日消费
            Double DayConsumption=DayConsumption(params);
            map.put("DayConsumption",DayConsumption);
            //当前任务数
            params.put("state",2);
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListCount(params);
            map.put("AllbillCount",AllbillCount);

            //今日达标数
            //1,先获取对应所有的订单
            Map<String,Object> billparam=new HashMap<>();
            billparam.put("userId",loginUser.getId());
            billparam.put("state",2);
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
                    billPrice.setInMemberId(loginUser.getId());
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
            map.put("standardSum",standardSum);

            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            //总完成率(今日达标数/总订单数)
            double AllCompleteness=0;
            if(billList.size()>0)
            {
                AllCompleteness=((double)standardSum/billList.size())*100;
            }
            map.put("AllCompleteness",df.format(AllCompleteness));
            //百度完成率
            String baidu="百度";
            Double baiduCompleteness=searchCompleteness(baidu,loginUser);
            map.put("baiduCompleteness",df.format(baiduCompleteness));
            //百度wap完成率
            String baiduWap="手机百度";
            Double baiduWapCompleteness=searchCompleteness(baiduWap,loginUser);
            map.put("baiduWapCompleteness",df.format(baiduWapCompleteness));
            //360完成率
            String sanliuling="360";
            Double sanliulingCompleteness=searchCompleteness(sanliuling,loginUser);
            map.put("sanliulingCompleteness",df.format(sanliulingCompleteness));

            //搜狗完成率
            String sougou="搜狗";
            Double sougouCompleteness=searchCompleteness(sougou,loginUser);
            map.put("sougouCompleteness",df.format(sougouCompleteness));
            //手机360
            String sanliulingWap="手机360";
            Double sanliulingWapCompleteness=searchCompleteness(sanliulingWap,loginUser);
            map.put("sanliulingWapCompleteness",df.format(sanliulingWapCompleteness));
            //手机搜狗
            String sougouWap="手机搜狗";
            Double sougouWapWapCompleteness=searchCompleteness(sougouWap,loginUser);
            map.put("sougouWapWapCompleteness",df.format(sougouWapWapCompleteness));
            //手机搜狗
            String shenma="神马";
            Double shenmaCompleteness=searchCompleteness(shenma,loginUser);
            map.put("shenmaCompleteness",df.format(shenmaCompleteness));
            //某个月的第一天和最后一天
            Map<String, String> maps = getFirstday_Lastday_Month(dateNow);
            String fistDay=maps.get("first");
            //将第一日转换为Date格式
            Date fistDate=null;
            try {
                fistDate = format1.parse(fistDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //上个月达标数
            String seriesLastMonth="";
            //上个月消费
            String seriesLastMonthSum="";
            //循环获取上个月每天的达标数
            Map<String,Object> dateMap=new  HashMap<>();
            dateMap.put("userId",loginUser.getId());
            //判断Y轴的显示
            int MaxYbylast=0;
            //判断Y轴消费的显示
            double MaxYbylastCost=0;
            for(int i=0;i<monthPreCount;i++)
            {
                calendar.setTime(fistDate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str=formatter.format(tomorrow);
                dateMap.put("date",str);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);
                //比较达标最大数
                if(MaxYbylast<=keywordsCount)
                {
                    MaxYbylast=keywordsCount;
                }
                //比较消费最大数
                if(MaxYbylastCost<=keywordsSum)
                {
                    MaxYbylastCost=keywordsSum;
                }
                seriesLastMonth+=keywordsCount+",";
                seriesLastMonthSum+=keywordsSum+",";
            }
            //上个月的达标数
            map.put("seriesLastMonth",seriesLastMonth);
            map.put("seriesLastMonthSum",seriesLastMonthSum);
            //判断Y轴的显示
            int MaxYbyNew=0;
            double MaxYbyNewCost=0;
            String seriesNowMonthSum="";
            for(int i=0;i<monthNowCount;i++)
            {
                calendar.setTime(fistDateNow);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str1=formatter.format(tomorrow);
                dateMap.put("date",str1);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);
                //比较达标最大数
                if(MaxYbyNew<=keywordsCount)
                {
                    MaxYbyNew=keywordsCount;
                }
                if(MaxYbyNewCost<=keywordsSum)
                {
                    MaxYbyNewCost=keywordsSum;
                }
                seriesNowMonth+=keywordsCount+",";
                seriesNowMonthSum+=keywordsSum+",";
            }
            String yAxis="";
            if(MaxYbyNew!=0)
            {
                yAxis=getYAxis(MaxYbyNew);
            }
            else
            {
                yAxis=getYAxis(MaxYbylast);
            }
            String yAxisSum="";
            if(MaxYbyNewCost!=0)
            {
                yAxisSum=getYAxisSum(MaxYbyNewCost);
            }
            else
            {
                yAxisSum=getYAxisSum(MaxYbylastCost);
            }


            map.put("yAxis",yAxis);
            map.put("yAxisSum",yAxisSum);
            map.put("seriesNowMonth",seriesNowMonth);
            map.put("seriesNowMonthSum",seriesNowMonthSum);
            return map;
        }
        //操作员
        else  if (loginUser.hasRole("COMMISSIONER"))
        {
            //客户数
            params.put("ascription",loginUser.getId());
            params.put("inMemberId",loginUser.getCreateUserId());
            Long UserCount=userMapper.getUserBillAscriptionCount(params);
            map.put("UserCount",UserCount);
            //当前任务数
            params.put("state",2);
            params.put("userId",loginUser.getCreateUserId());
            params.put("billAscription",loginUser.getId());
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListCount(params);
            map.put("AllbillCount",AllbillCount);

            //月总消费
            Calendar now =Calendar.getInstance();
            params.put("year",now.get(Calendar.YEAR));
            params.put("month",now.get(Calendar.MONTH)+1);
            params.put("createId",loginUser.getCreateUserId());
            Double sum=billCostMapper.MonthConsumptionCommissioner(params);
            map.put("MonthConsumption",sum);
            //今日消费
            params.put("day",now.get(Calendar.DATE));
            Double sum1=billCostMapper.MonthConsumptionCommissioner(params);
            map.put("DayConsumption",sum1);

            //今日达标数
            //1,先获取对应所有的订单
            Map<String,Object> billparam=new HashMap<>();
            billparam.put("userId",loginUser.getCreateUserId());
            billparam.put("billAscription",loginUser.getCreateUserId());
            billparam.put("state",2);
            List<Bill> billList=billMapper.selectByInMemberId(params);
            //判断哪些订单今日达标
            int standardSum=0;//今天达标数
            if(billList.size()>0)
            {
                for (Bill bill:billList
                        ) {
                    //对应订单排名标准
                    BillPrice billPrice=new BillPrice();
                    billPrice.setBillId(bill.getId());
                    billPrice.setInMemberId(loginUser.getCreateUserId());
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
            map.put("standardSum",standardSum);

            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            //总完成率(今日达标数/总订单数)
            double AllCompleteness=0;
            if(billList.size()>0)
            {
                AllCompleteness=((double)standardSum/billList.size())*100;
            }
            map.put("AllCompleteness",df.format(AllCompleteness));
            //百度完成率
            String baidu="百度";
            Double baiduCompleteness=searchCompletenessBycmm(baidu,loginUser);
            map.put("baiduCompleteness",df.format(baiduCompleteness));
            //百度wap完成率
            String baiduWap="手机百度";
            Double baiduWapCompleteness=searchCompletenessBycmm(baiduWap,loginUser);
            map.put("baiduWapCompleteness",df.format(baiduWapCompleteness));
            //360完成率
            String sanliuling="360";
            Double sanliulingCompleteness=searchCompletenessBycmm(sanliuling,loginUser);
            map.put("sanliulingCompleteness",df.format(sanliulingCompleteness));
            //搜狗完成率
            String sougou="搜狗";
            Double sougouCompleteness=searchCompletenessBycmm(sougou,loginUser);
            map.put("sougouCompleteness",df.format(sougouCompleteness));
            //手机360
            String sanliulingWap="手机360";
            Double sanliulingWapCompleteness=searchCompletenessBycmm(sanliulingWap,loginUser);
            map.put("sanliulingWapCompleteness",df.format(sanliulingWapCompleteness));
            //手机搜狗
            String sougouWap="手机搜狗";
            Double sougouWapWapCompleteness=searchCompletenessBycmm(sougouWap,loginUser);
            map.put("sougouWapWapCompleteness",df.format(sougouWapWapCompleteness));
            //神马
            String shenma="神马";
            Double shenmaCompleteness=searchCompletenessBycmm(shenma,loginUser);
            map.put("shenmaCompleteness",df.format(shenmaCompleteness));
            //某个月的第一天和最后一天
            Map<String, String> maps = getFirstday_Lastday_Month(dateNow);
            String fistDay=maps.get("first");
            //将第一日转换为Date格式
            Date fistDate=null;
            try {
                fistDate = format1.parse(fistDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String seriesLastMonth="";
            //上个月消费
            String seriesLastMonthSum="";
            //循环获取上个月每天的达标数
            Map<String,Object> dateMap=new  HashMap<>();
            dateMap.put("userId",loginUser.getCreateUserId());
            dateMap.put("billAscription",loginUser.getId());
            //判断Y轴的显示
            int MaxYbylast=0;
            //判断Y轴消费的显示
            double MaxYbylastCost=0;
            for(int i=0;i<monthPreCount;i++)
            {
                calendar.setTime(fistDate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str=formatter.format(tomorrow);
                dateMap.put("date",str);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);
                //比较达标最大数
                if(MaxYbylast<=keywordsCount)
                {
                    MaxYbylast=keywordsCount;
                }
                seriesLastMonth+=keywordsCount+",";
                //比较消费最大数
                if(MaxYbylastCost<=keywordsSum)
                {
                    MaxYbylastCost=keywordsSum;
                }
                seriesLastMonth+=keywordsCount+",";
                seriesLastMonthSum+=keywordsSum+",";

            }
            //上个月的达标数
            map.put("seriesLastMonth",seriesLastMonth);
            map.put("seriesLastMonthSum",seriesLastMonthSum);
            //判断Y轴的显示
            //判断Y轴的显示
            int MaxYbyNew=0;
            double MaxYbyNewCost=0;
            String seriesNowMonthSum="";
            for(int i=0;i<monthNowCount;i++)
            {
                calendar.setTime(fistDateNow);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str1=formatter.format(tomorrow);
                dateMap.put("date",str1);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);
                //比较达标最大数
                if(MaxYbyNew<=keywordsCount)
                {
                    MaxYbyNew=keywordsCount;
                }
                if(MaxYbyNewCost<=keywordsSum)
                {
                    MaxYbyNewCost=keywordsSum;
                }
                seriesNowMonth+=keywordsCount+",";
                seriesNowMonthSum+=keywordsSum+",";
            }
            String yAxis="";
            if(MaxYbyNew!=0)
            {
                yAxis=getYAxis(MaxYbyNew);
            }
            else
            {
                yAxis=getYAxis(MaxYbylast);
            }
            String yAxisSum="";
            if(MaxYbyNewCost!=0)
            {
                yAxisSum=getYAxisSum(MaxYbyNewCost);
            }
            else
            {
                yAxisSum=getYAxisSum(MaxYbylastCost);
            }


            map.put("yAxis",yAxis);
            map.put("yAxisSum",yAxisSum);
            map.put("seriesNowMonth",seriesNowMonth);
            map.put("seriesNowMonthSum",seriesNowMonthSum);
            return map;
        }
        else if (loginUser.hasRole("CUSTOMER"))
        {
            //账户余额
            FundAccount fundAccount=fundAccountMapper.selectByUserId(loginUser.getId());
            if (fundAccount==null)
            {

                map.put("balance",0);
            }
            else
            {
                map.put("balance",fundAccount.getBalance());

            }
            //月总消费
            Calendar now =Calendar.getInstance();
            params.put("year",now.get(Calendar.YEAR));
            params.put("month",now.get(Calendar.MONTH)+1);
            Double sum=billCostMapper.MonthConsumptionCustomer(params);
            map.put("MonthConsumption",sum);
            //今日消费
            params.put("day",now.get(Calendar.DATE));
            Double sum1=billCostMapper.MonthConsumptionCustomer(params);
            map.put("DayConsumption",sum1);
            //当前任务数
            params.put("state",2);
            Long billCount=billMapper.getBillListByCmmCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListByCmmCount(params);
            map.put("AllbillCount",AllbillCount);
            //今日达标数
            List<Bill> billList=billMapper.getBillCountByCmm(params);
            int standardSum=0;//今天达标数
            if(billList.size()>0)//判断哪些订单今日达标
            {
                for (Bill bill:billList
                        ) {
                    //对应订单排名标准
                    BillPrice billPrice=new BillPrice();
                    billPrice.setBillId(bill.getId());
                    billPrice.setOutMemberId(loginUser.getId());
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
            map.put("standardSum",standardSum);

            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            //总完成率(今日达标数/总订单数)
            double AllCompleteness=0;
            if(billList.size()>0)
            {
                AllCompleteness=((double)standardSum/billList.size())*100;
            }
            map.put("AllCompleteness",df.format(AllCompleteness));
            //百度完成率
            String baidu="百度";
            Double baiduCompleteness=searchCompletenessBycus(baidu,loginUser);
            map.put("baiduCompleteness",df.format(baiduCompleteness));
            //百度wap完成率
            String baiduWap="手机百度";
            Double baiduWapCompleteness=searchCompletenessBycus(baiduWap,loginUser);
            map.put("baiduWapCompleteness",df.format(baiduWapCompleteness));
            //360完成率
            String sanliuling="360";
            Double sanliulingCompleteness=searchCompletenessBycus(sanliuling,loginUser);
            map.put("sanliulingCompleteness",df.format(sanliulingCompleteness));
            //搜狗完成率
            String sougou="搜狗";
            Double sougouCompleteness=searchCompletenessBycus(sougou,loginUser);
            map.put("sougouCompleteness",df.format(sougouCompleteness));
            //手机360
            String sanliulingWap="手机360";
            Double sanliulingWapCompleteness=searchCompletenessBycus(sanliulingWap,loginUser);
            map.put("sanliulingWapCompleteness",df.format(sanliulingWapCompleteness));
            //手机搜狗
            String sougouWap="手机搜狗";
            Double sougouWapWapCompleteness=searchCompletenessBycus(sougouWap,loginUser);
            map.put("sougouWapWapCompleteness",df.format(sougouWapWapCompleteness));
            //神马
            String shenma="神马";
            Double shenmaCompleteness=searchCompletenessBycus(shenma,loginUser);
            map.put("shenmaCompleteness",df.format(shenmaCompleteness));
            //某个月的第一天和最后一天
            Map<String, String> maps = getFirstday_Lastday_Month(dateNow);
            String fistDay=maps.get("first");
            //将第一日转换为Date格式
            Date fistDate=null;
            try {
                fistDate = format1.parse(fistDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String seriesLastMonth="";
            //上个月消费
            String seriesLastMonthSum="";
            //循环获取上个月每天的达标数
            Map<String,Object> dateMap=new  HashMap<>();
            dateMap.put("outMemberId",loginUser.getId());
            //判断Y轴的显示
            int MaxYbylast=0;
            //判断Y轴消费的显示
            double MaxYbylastCost=0;
            for(int i=0;i<monthPreCount;i++)
            {
                calendar.setTime(fistDate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str=formatter.format(tomorrow);
                dateMap.put("date",str);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);
                //比较达标最大数
                if(MaxYbylast<=keywordsCount)
                {
                    MaxYbylast=keywordsCount;
                }
                seriesLastMonth+=keywordsCount+",";
                //比较消费最大数
                if(MaxYbylastCost<=keywordsSum)
                {
                    MaxYbylastCost=keywordsSum;
                }
                seriesLastMonth+=keywordsCount+",";
                seriesLastMonthSum+=keywordsSum+",";

            }
            //上个月的达标数
            map.put("seriesLastMonth",seriesLastMonth);
            map.put("seriesLastMonthSum",seriesLastMonthSum);
            //判断Y轴的显示
            //判断Y轴的显示
            int MaxYbyNew=0;
            double MaxYbyNewCost=0;
            String seriesNowMonthSum="";
            for(int i=0;i<monthNowCount;i++)
            {
                calendar.setTime(fistDateNow);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str1=formatter.format(tomorrow);
                dateMap.put("date",str1);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                Double keywordsSum=billCostMapper.selectByBillCostOfDaySum(dateMap);
                //比较达标最大数
                if(MaxYbyNew<=keywordsCount)
                {
                    MaxYbyNew=keywordsCount;
                }
                if(MaxYbyNewCost<=keywordsCount)
                {
                    MaxYbyNewCost=keywordsSum;
                }
                seriesNowMonth+=keywordsCount+",";
                seriesNowMonthSum+=keywordsSum+",";
            }
            String yAxis="";
            if(MaxYbyNew!=0)
            {
                yAxis=getYAxis(MaxYbyNew);
            }
            else
            {
                yAxis=getYAxis(MaxYbylast);
            }
            String yAxisSum="";
            if(MaxYbyNewCost!=0)
            {
                yAxisSum=getYAxisSum(MaxYbyNewCost);
            }
            else
            {
                yAxisSum=getYAxisSum(MaxYbylastCost);
            }


            map.put("yAxis",yAxis);
            map.put("yAxisSum",yAxisSum);
            map.put("seriesNowMonth",seriesNowMonth);
            map.put("seriesNowMonthSum",seriesNowMonthSum);
            return map;
        }

        return null;
    }





    //客户数
    public Long UserCount(Map<String, Object> params)
    {
        Long Count=userMapper.getUserRoleByCreateIdCount(params);
        return  Count;
    }
    //本月总消费
    public  Double MonthConsumption(Map<String, Object> params)
    {
        Calendar now =Calendar.getInstance();
        params.put("year",now.get(Calendar.YEAR));
        params.put("month",now.get(Calendar.MONTH)+1);
        Double sum=billCostMapper.MonthConsumption(params);
        return sum;
    }
    //今日消费
    public  Double DayConsumption(Map<String, Object> params)
    {
        Calendar now =Calendar.getInstance();
        params.put("year",now.get(Calendar.YEAR));
        params.put("month",now.get(Calendar.MONTH)+1);
        params.put("day",now.get(Calendar.DATE));
        Double sum=billCostMapper.MonthConsumption(params);
        return sum;
    }
    //搜索引擎完成率（管理员，渠道商，代理商）
    public  Double searchCompleteness(String search, LoginUser loginUser)
    {
        //今日达标数
        //1,先获取对应所有的订单
        Map<String,Object> billparam=new HashMap<>();
        billparam.put("userId",loginUser.getId());
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
                billPrice.setInMemberId(loginUser.getId());
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
    public  Double searchCompletenessBycmm(String search, LoginUser loginUser)
    {
        //今日达标数
        //1,先获取对应所有的订单
        Map<String,Object> billparam=new HashMap<>();
        billparam.put("userId",loginUser.getCreateUserId());
        billparam.put("state",2);
        billparam.put("billAscription",loginUser.getId());
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
                billPrice.setInMemberId(loginUser.getCreateUserId());
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
    public  Double searchCompletenessBycus(String search, LoginUser loginUser)
    {
        //今日达标数
        //1,先获取对应所有的订单
        Map<String,Object> billparam=new HashMap<>();
        billparam.put("userId",loginUser.getId());
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
                billPrice.setOutMemberId(loginUser.getId());
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

    //获取一个月的天数
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 某一个月第一天和最后一天
     * @param date
     * @return
     */
    private static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }


    public String getYAxis(int max)
    {
        String yAxis="";
        if(max<=50)
        {
            yAxis="0,5,10,15,20,25,30,35,40,45";
        }
        else if(max<=100&&max>50)
        {

            yAxis="50,55,60,65,70,75,80,85,90,95";
        }
        else if(max>100&&max<=200)
        {

            yAxis="100,110,120,130,140,150,160,170,180,190";
        }
        else if(max<=300&&max>200)
        {
            yAxis="200,210,220,230,240,250,260,270,280,290";
        }
        else if(max<=400&&max>300)
        {
            yAxis="300,310,320,330,340,350,360,370,380,390";
        }
        else if(max<=500&&max>400)
        {
            yAxis="400,410,420,430,440,450,460,470,480,490";
        }
        else if(max<=600&&max>500)
        {
            yAxis="500,510,520,530,540,550,560,570,580,590";
        }
        else if(max<=700&&max>600)
        {
            yAxis="600,610,620,630,640,650,660,670,680,690";
        }
        else if(max<=800&&max>700)
        {
            yAxis="700,710,720,730,740,750,760,770,780,790";
        }

        else if(max<=900&&max>800)
        {
            yAxis="800,810,820,830,840,850,860,870,880,890";
        }

        else if(max<=1000&&max>900)
        {
            yAxis="900,910,920,930,940,950,960,970,980,990";
        }

        else if(max<=1100&&max>1000)
        {
            yAxis="1000,1010,1020,1030,1040,1050,1060,1070,1080,1090";
        }
        else if(max<=1200&&max>1100)
        {
            yAxis="1100,1110,1120,1130,1140,1150,1160,1170,1180,1190";
        }
        else if(max<=1300&&max>1200)
        {
            yAxis="1200,1210,1220,1230,1240,1250,1260,1270,1280,1290";
        }

        else if(max<=1400&&max>1300)
        {
            yAxis="1300,1310,1320,1330,1340,1350,1360,1370,1380,1390";
        }

        else if(max<=1500&&max>1400)
        {
            yAxis="1400,1410,1420,1430,1440,1450,1460,1470,1480,1490";
        }
        else if(max<=1600&&max>1500)
        {
            yAxis="1500,1510,1520,1530,1540,1550,1560,1570,1580,1590";
        }

        else if(max<=1700&&max>1600)
        {
            yAxis="1600,1610,1620,1630,1640,1650,1660,1670,1680,1690";
        }
        else if(max<=1800&&max>1700)
        {
            yAxis="1700,1710,1720,1730,1740,1750,1760,1770,1780,1790";
        }
        else if(max<=1900&&max>1800)
        {
            yAxis="1800,1810,1820,1830,1840,1850,1860,1870,1880,1890";
        }
        else if(max<=2000&&max>1900)
        {
            yAxis="1900,1910,1920,1930,1940,1950,1960,1970,1980,1990";
        }


        return yAxis;
    }

    public String getYAxisSum(double max)
    {
        String yAxis= "";
        if(max<=50)
        {
            yAxis="0,5,10,15,20,25,30,35,40,45";
        }
        else if(max<=100&&max>50)
        {

            yAxis="50,55,60,65,70,75,80,85,90,95";
        }
        else if(max>100&&max<=200)
        {

            yAxis="100,110,120,130,140,150,160,170,180,190";
        }
        else if(max<=300&&max>200)
        {
            yAxis="200,210,220,230,240,250,260,270,280,290";
        }
        else if(max<=400&&max>300)
        {
            yAxis="300,310,320,330,340,350,360,370,380,390";
        }
        else if(max<=500&&max>400)
        {
            yAxis="400,410,420,430,440,450,460,470,480,490";
        }
        else if(max<=600&&max>500)
        {
            yAxis="500,510,520,530,540,550,560,570,580,590";
        }
        else if(max<=700&&max>600)
        {
            yAxis="600,610,620,630,640,650,660,670,680,690";
        }
        else if(max<=800&&max>700)
        {
            yAxis="700,710,720,730,740,750,760,770,780,790";
        }

        else if(max<=900&&max>800)
        {
            yAxis="800,810,820,830,840,850,860,870,880,890";
        }

        else if(max<=1000&&max>900)
        {
            yAxis="900,910,920,930,940,950,960,970,980,990";
        }

        else if(max<=1100&&max>1000)
        {
            yAxis="1000,1010,1020,1030,1040,1050,1060,1070,1080,1090";
        }
        else if(max<=1200&&max>1100)
        {
            yAxis="1100,1110,1120,1130,1140,1150,1160,1170,1180,1190";
        }
        else if(max<=1300&&max>1200)
        {
            yAxis="1200,1210,1220,1230,1240,1250,1260,1270,1280,1290";
        }

        else if(max<=1400&&max>1300)
        {
            yAxis="1300,1310,1320,1330,1340,1350,1360,1370,1380,1390";
        }

        else if(max<=1500&&max>1400)
        {
            yAxis="1400,1410,1420,1430,1440,1450,1460,1470,1480,1490";
        }
        else if(max<=1600&&max>1500)
        {
            yAxis="1500,1510,1520,1530,1540,1550,1560,1570,1580,1590";
        }

        else if(max<=1700&&max>1600)
        {
            yAxis="1600,1610,1620,1630,1640,1650,1660,1670,1680,1690";
        }
        else if(max<=1800&&max>1700)
        {
            yAxis="1700,1710,1720,1730,1740,1750,1760,1770,1780,1790";
        }
        else if(max<=1900&&max>1800)
        {
            yAxis="1800,1810,1820,1830,1840,1850,1860,1870,1880,1890";
        }
        else if(max<=2000&&max>1900)
        {
            yAxis="1900,1910,1920,1930,1940,1950,1960,1970,1980,1990";
        }



        return yAxis;
    }

}
