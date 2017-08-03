package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.BillManageList;
import com.yipeng.bill.bms.model.FundItemSum;
import com.yipeng.bill.bms.service.BillManageService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.BillDetails;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/5/2.
 */
@Service
public class BillManageServiceImpl implements BillManageService {

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BillCostMapper billCostMapper;
    @Autowired
    private BillSearchSupportMapper billSearchSupportMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private  LogsMapper logsMapper;
    /**
     * 管理员订单管理
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> manageListByAdminTable(Map<String, Object> params, LoginUser loginUser) {
        List<BillManageList> billManageListList = new ArrayList<BillManageList>();
        params.put("userId",loginUser.getId());
        List<Map<String,Object>> selectByGroupBillCount=billMapper.selectByGroupBillCount(params);//总的订单数
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
        String date=sm.format(new Date());
        params.put("dateTime", date);
        List<Map<String,Object>> selectByGroupBillDabiaoCount=billMapper.selectByGroupBillDabiaoCount(params);//达标数
        List<User> userList=userMapper.selectAllUsers("4");//所有渠道商
        int k=0;
        try
        {
            for(int i=0;i<selectByGroupBillCount.size();i++)
            {
                k++;
                BillManageList billManageList=new BillManageList();
                billManageList.setId(k);
                billManageList.setWebSite(selectByGroupBillCount.get(i).get("website").toString());
                //查询客户
                for (User item:userList
                     ) {
                    if(Long.parseLong(selectByGroupBillCount.get(i).get("kehu").toString())==item.getId())
                    {
                        billManageList.setUserName(item.getUserName());
                        break;
                    }
                }
                billManageList.setOptimizationDays(sm.format(selectByGroupBillCount.get(i).get("dateTime")));
                billManageList.setCommissioner(selectByGroupBillCount.get(i).get("caozuoyuan").toString());
                billManageList.setBillCount(Integer.parseInt(selectByGroupBillCount.get(i).get("num").toString()));
                Double AllCount=Double.parseDouble(selectByGroupBillCount.get(i).get("num").toString());
                Double DabiaoCount=0.0;
                for (int j=0;j<selectByGroupBillDabiaoCount.size();j++)
                {
                    if(selectByGroupBillCount.get(i).get("website").toString().equals(selectByGroupBillDabiaoCount.get(j).get("website").toString()))
                    {
                        DabiaoCount=Double.parseDouble(selectByGroupBillDabiaoCount.get(j).get("num").toString());
                        break;
                    }
                }
                billManageList.setKeywordsCompletionRate(String.format("%.2f", (DabiaoCount/AllCount)*100));
                billManageListList.add(billManageList);
            }
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }

         Map<String, Object> map = new HashMap<>();
         map.put("rows", billManageListList);
        return map;
    }

    /**
     * 订单管理（渠道商和操作员）
     *
     * @param params
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> manageListByOtherTable(Map<String, Object> params, LoginUser loginUser) {
        //转换时间
        if (loginUser.hasRole("COMMISSIONER")) {
            params.put("userId", loginUser.getCreateUserId());
            params.put("billAscription", loginUser.getId());
        } else {
            if (loginUser.hasRole("ASSISTANT")) {
                params.put("userId", loginUser.getCreateUserId());
            } else {
                params.put("userId", loginUser.getId());
            }

        }
        List<BillManageList> billManageListList = new ArrayList<BillManageList>();
        List<Map<String,Object>> selectByGroupBillCount=billMapper.selectByGroupBillCount(params);//总的订单数
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
        String date=sm.format(new Date());
        params.put("dateTime", date);
        List<Map<String,Object>> selectByGroupBillDabiaoCount=billMapper.selectByGroupBillDabiaoCount(params);//达标数
        List<User> userList=new ArrayList<>();
        if (loginUser.hasRole("COMMISSIONER"))
        {
            userList=userMapper.selectAllUsers("4");//所有渠道商
        }
        else {
            if (loginUser.hasRole("ASSISTANT")) {
                userList=userMapper.getUserByCreateId(loginUser.getCreateUserId());
            } else {
                userList=userMapper.getUserByCreateId(loginUser.getId());
            }
        }
        int k=0;
        try
        {
            for(int i=0;i<selectByGroupBillCount.size();i++)
            {
                k++;
                BillManageList billManageList=new BillManageList();
                billManageList.setId(k);
                billManageList.setWebSite(selectByGroupBillCount.get(i).get("website").toString());
                //查询客户
                for (User item:userList
                        ) {
                    if(Long.parseLong(selectByGroupBillCount.get(i).get("kehu").toString())==item.getId())
                    {
                        billManageList.setUserName(item.getUserName());
                        break;
                    }
                }
                billManageList.setOptimizationDays(sm.format(selectByGroupBillCount.get(i).get("dateTime")));
                billManageList.setCommissioner(selectByGroupBillCount.get(i).get("caozuoyuan").toString());
                billManageList.setBillCount(Integer.parseInt(selectByGroupBillCount.get(i).get("num").toString()));
                Double AllCount=Double.parseDouble(selectByGroupBillCount.get(i).get("num").toString());
                Double DabiaoCount=0.0;
                for (int j=0;j<selectByGroupBillDabiaoCount.size();j++)
                {
                    if(selectByGroupBillCount.get(i).get("website").toString().equals(selectByGroupBillDabiaoCount.get(j).get("website").toString()))
                    {
                        DabiaoCount=Double.parseDouble(selectByGroupBillDabiaoCount.get(j).get("num").toString());
                        break;
                    }
                }
                billManageList.setKeywordsCompletionRate(String.format("%.2f", (DabiaoCount/AllCount)*100));
                billManageListList.add(billManageList);
            }
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rows", billManageListList);
        return map;

    }

    @Override
    public Map<String, Object> getNewRankingTable(Map<String, Object> params, LoginUser loginUser) {

        int i = 0;
        List<BillDetails> billDetailsList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sm.format(new Date());
        //根据权限来判断
        //管理员
        if (loginUser.hasRole("SUPER_ADMIN")) {
            List<BillCost> billList = billCostMapper.selectByGetBillToOne();
            for (BillCost item : billList
                    ) {
                String costDate = sm.format(item.getCostDate());
                if (costDate.equals(dateNow)) {
                    i++;
                    Bill bill = billMapper.selectByPrimaryKey(item.gettBillId());
                    if (bill != null) {
                        BillDetails billDetails = new BillDetails();
                        billDetails.setdisplayId(i);
                        billDetails.setWebsite(bill.getWebsite());
                        billDetails.setKeywords(bill.getKeywords());
                        //获取搜索引擎
                        System.out.print(bill.getId());
                        try {
                            BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                            billDetails.setSearchName(billSearchSupport.getSearchSupport());
                        } catch (Exception e) {
                          Logs logs=new Logs();
                          logs.setOpobj("1");
                          logs.setOptype(1);
                          logs.setUserid(loginUser.getId());
                          logs.setOpremake("錯誤Id:"+bill.getId());
                            logsMapper.insert(logs);
                        }
                        //获取客户
                        BillPrice billPrice = new BillPrice();
                        billPrice.setInMemberId(loginUser.getId());
                        billPrice.setBillId(bill.getId());
                        List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
                        if (!CollectionUtils.isEmpty(billPriceList)) {
                            User user1 = userMapper.selectByPrimaryKey(billPriceList.get(0).getOutMemberId());
                            billDetails.setUserName(user1.getUserName());
                        }
                        billDetails.setNewRanking(bill.getNewRanking());
                        billDetails.setChangeRanking(bill.getChangeRanking());
                        billDetailsList.add(billDetails);
                    }
                }
            }
            map.put("rows", billDetailsList);
            return map;

        }
        //渠道商
        else if (loginUser.hasRole("DISTRIBUTOR") || loginUser.hasRole("ASSISTANT")) {
            Map<String, Object> mapParams = new HashMap<>();
            if (loginUser.hasRole("ASSISTANT")) {
                mapParams.put("outmemberId", loginUser.getCreateUserId());
            } else {
                mapParams.put("outmemberId", loginUser.getId());
            }

            List<BillCost> billList2 = billCostMapper.selectByQuDaoGetBillToOne(mapParams);
            for (BillCost item : billList2
                    ) {
                String costDate = sm.format(item.getCostDate());
                if (costDate.equals(dateNow)) {
                    i++;
                    Bill bill = billMapper.selectByPrimaryKey(item.gettBillId());
                    if (bill != null) {
                        BillDetails billDetails = new BillDetails();
                        billDetails.setdisplayId(i);
                        billDetails.setWebsite(bill.getWebsite());
                        billDetails.setKeywords(bill.getKeywords());
                        //获取搜索引擎
                        BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                        billDetails.setSearchName(billSearchSupport.getSearchSupport());
                        //获取客户
                        BillPrice billPrice = new BillPrice();
                        if (loginUser.hasRole("ASSISTANT")) {
                            billPrice.setInMemberId(loginUser.getCreateUserId());
                        } else {
                            billPrice.setInMemberId(loginUser.getId());
                        }

                        billPrice.setBillId(bill.getId());
                        List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
                        if (!CollectionUtils.isEmpty(billPriceList)) {
                            User user1 = userMapper.selectByPrimaryKey(billPriceList.get(0).getOutMemberId());
                            billDetails.setUserName(user1.getUserName());
                        }
                        billDetails.setNewRanking(bill.getNewRanking());
                        billDetails.setChangeRanking(bill.getChangeRanking());
                        billDetailsList.add(billDetails);
                    }
                }
            }
            map.put("rows", billDetailsList);
            return map;

        } else if (loginUser.hasRole("COMMISSIONER")) {
            Map<String, Object> mapParams = new HashMap<>();
            mapParams.put("billAscription", loginUser.getId());
            List<BillCost> billList2 = billCostMapper.selectByCaoZuoyuanGetBillToOne(mapParams);
            for (BillCost item : billList2
                    ) {
                String costDate = sm.format(item.getCostDate());
                if (costDate.equals(dateNow)) {
                    i++;
                    Bill bill = billMapper.selectByPrimaryKey(item.gettBillId());
                    if (bill != null) {
                        BillDetails billDetails = new BillDetails();
                        billDetails.setdisplayId(i);
                        billDetails.setWebsite(bill.getWebsite());
                        billDetails.setKeywords(bill.getKeywords());
                        //获取搜索引擎
                        BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                        billDetails.setSearchName(billSearchSupport.getSearchSupport());
                        //获取客户
                        BillPrice billPrice = new BillPrice();
                        billPrice.setInMemberId(loginUser.getCreateUserId());
                        billPrice.setBillId(bill.getId());
                        List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
                        if (!CollectionUtils.isEmpty(billPriceList)) {
                            User user1 = userMapper.selectByPrimaryKey(billPriceList.get(0).getOutMemberId());
                            billDetails.setUserName(user1.getUserName());
                        }
                        billDetails.setNewRanking(bill.getNewRanking());
                        billDetails.setChangeRanking(bill.getChangeRanking());
                        billDetailsList.add(billDetails);
                    }
                }
            }
            map.put("rows", billDetailsList);
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> performanceStatisticsTable(LoginUser loginUser, String searchTime) {
        //转换时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        //获取上一个月
        Calendar calendar = Calendar.getInstance();
        if (searchTime != null) {
            try {
                calendar.setTime(format1.parse(searchTime)); // 设置为当前时间
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            calendar.setTime(new Date());
        }
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        //上一个月的日期
        Date preMonth = null;
        try {
            preMonth = format1.parse(formatter.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取昨天
        Calendar cal = Calendar.getInstance();
        if (cal != null) {
            try {
                cal.setTime(format1.parse(searchTime)); // 设置为当前时间
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cal.setTime(new Date());
        }
        cal.add(Calendar.DATE, -1);
        Date yesterDay = cal.getTime();
        //获取渠道商的客户和代理商
        List<User> userList = userService.getSearchUser(loginUser, "2");
        Map<String, Object> params = new HashMap<>();
        params.put("userId", loginUser.getId());
        //上月消费
        Map<String, Object> paramsLast = new HashMap<>();
        paramsLast.put("userId", loginUser.getId());
        //昨天消费
        Map<String, Object> paramsYseterDay = new HashMap<>();
        paramsYseterDay.put("userId", loginUser.getId());
        List<FundItemSum> fundItemSumList = new ArrayList<>();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat smm = new SimpleDateFormat("yyyy-MM");
        if ("".equals(searchTime)) {
            searchTime = sm.format(new Date());
        }
        String monthTime = null;
        try {
            monthTime = smm.format(smm.parse(searchTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String monthLast = null;
        monthLast = smm.format(preMonth);
        //本日
        params.put("searchTime", searchTime);
        //昨日
        paramsYseterDay.put("searchTime", sm.format(yesterDay));
        //本月
        params.put("monthTime", monthTime);
        paramsLast.put("monthTime", monthLast);

        Long i = new Long(0);
        for (User item : userList
                ) {
            params.put("OutUserId", item.getId());
            paramsYseterDay.put("OutUserId", item.getId());
            paramsLast.put("OutUserId", item.getId());
            i++;

            Double sumDay = billCostMapper.selectByBillCaoZuoYuanDayCost(params);
            Double sumYesterDay = billCostMapper.selectByBillCaoZuoYuanDayCost(paramsYseterDay);
            Double sumMonth = billCostMapper.selectByBillCaoZuoYuanMonthCost(params);
            Double sumMonthLast = billCostMapper.selectByBillCaoZuoYuanMonthCost(paramsLast);
            FundItemSum fundItemSum = new FundItemSum();
            fundItemSum.setId(i);
            fundItemSum.setUserName(item.getUserName());

            fundItemSum.setChangeTime(searchTime);
            if (sumDay == null) {
                fundItemSum.setdayAccountSum(new BigDecimal(0));
            } else {
                fundItemSum.setdayAccountSum(new BigDecimal(sumDay));
            }

            if (sumYesterDay == null) {
                fundItemSum.setYesterDaySum(new BigDecimal(0));
            } else {
                fundItemSum.setYesterDaySum(new BigDecimal(sumYesterDay));
            }

            if (sumMonth == null) {
                fundItemSum.setChangeAmount(new BigDecimal(0));
            } else {
                fundItemSum.setChangeAmount(new BigDecimal(sumMonth));
            }
            if (sumMonthLast == null) {
                fundItemSum.setLastMonthSum(new BigDecimal(0));
            } else {
                fundItemSum.setLastMonthSum(new BigDecimal(sumMonthLast));
            }

            fundItemSumList.add(fundItemSum);

        }

        Map<String, Object> map = new HashMap<>();
        map.put("rows", fundItemSumList);
        return map;
    }

    /**
     * 下滑排名列表
     *
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> getDeclineBillTable(LoginUser loginUser) {
        List<Map<String, Object>> billList = new ArrayList<>();
        Map<String, Object> sqlMap = new HashMap<>();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        String today = sm.format(new Date());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
        String yesterday = sm.format(calendar.getTime());
        Long total;
        sqlMap.put("today", today);
        sqlMap.put("yesterday", yesterday);
        if (loginUser.hasRole("COMMISSIONER")) {
            sqlMap.put("ascriptionId", loginUser.getId());
            billList = billMapper.selectDeclineBillTable(sqlMap);
            total = billMapper.selectDeclineBillTableCount(sqlMap);
        } else {
            billList = billMapper.selectDeclineBillTable(sqlMap);
            total = billMapper.selectDeclineBillTableCount(sqlMap);
        }
        int displayId = 0;
        for (int i = 0; i < billList.size(); i++) {
            displayId++;
            billList.get(i).put("displayId", displayId);
        }
        Map<String, Object> viewMap = new HashMap<>();
        viewMap.put("rows", billList);
        viewMap.put("total", total);
        return viewMap;
    }

}
