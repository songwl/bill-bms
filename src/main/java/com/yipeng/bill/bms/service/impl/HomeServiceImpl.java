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
            String yAxis="";
            if(AllbillCount<=100)
            {
                yAxis="10,20,30,40,50,60,70,80,90,100";
            }
            else if(AllbillCount<=200&&AllbillCount>100)
            {
              /*  yAxis="110,120,130,140,150,160,170,180,190,200";*/
                yAxis="10,20,30,40,50,60,70,80,90,100";
            }
            else if(AllbillCount<=300&&AllbillCount>200)
            {
                yAxis="210,220,230,240,250,260,270,280,290,300";
            }
            map.put("yAxis",yAxis);
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
            //循环获取上个月每天的达标数
            Map<String,Object> dateMap=new  HashMap<>();
            dateMap.put("userId",loginUser.getId());
            for(int i=0;i<monthPreCount;i++)
            {
                calendar.setTime(fistDate);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str=formatter.format(tomorrow);
                dateMap.put("date",str);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                seriesLastMonth+=keywordsCount+",";

            }
             //上个月的达标数
            map.put("seriesLastMonth",seriesLastMonth);
            //获取当前月份天数
            calendar.setTime(dateNow);
            int monthNowCount = calendar.get(Calendar.DAY_OF_MONTH);
            String seriesNowMonth="";
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(dateNow); // 设置为当前时间
            calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1); // 设置为下一个月
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
            for(int i=0;i<monthNowCount;i++)
            {
                calendar.setTime(fistDateNow);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = calendar.getTime();
                String str1=formatter.format(tomorrow);
                dateMap.put("date",str1);
                int keywordsCount=billCostMapper.selectByBillCostOfDay(dateMap);
                seriesNowMonth+=keywordsCount+",";

            }
            map.put("seriesNowMonth",seriesNowMonth);
            return map;
        }
        //渠道商和代理商
        else if(loginUser.hasRole("DISTRIBUTOR")||loginUser.hasRole("AGENT"))
        {
            //客户数
            //Role role=roleMapper.selectByRoleCode("AGENT");
            //params.put("roleId",role.getId());
            //Long count=UserCount(params);
           // Role role1=roleMapper.selectByRoleCode("CUSTOMER");
           // params.put("roleId",role1.getId());
            //Long count1=UserCount(params);
            //Long AllCount=count+count1;
            //.put("UserCount",AllCount);

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
            billparam.put("billAscription",loginUser.getId());
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
}
