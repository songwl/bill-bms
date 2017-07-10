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

    /**
     * 管理员订单管理
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> manageListByAdminTable(Map<String, Object> params, LoginUser loginUser) {


         return null;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String daynow = formatter.format(new Date());
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = null;
        try {
            dateNow = format1.parse(daynow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //参数对象
        int limit = Integer.parseInt(params.get("limit").toString());
        int offset = Integer.parseInt(params.get("offset").toString());
        int i = offset;
        if (loginUser.hasRole("COMMISSIONER")) {
            params.put("userId", loginUser.getCreateUserId());
            params.put("state", 2);//订单状态
            params.put("billType", 1);//订单属性
            params.put("billAscription", loginUser.getId());
        } else {
            if (loginUser.hasRole("ASSISTANT")) {
                params.put("userId", loginUser.getCreateUserId());
            } else {
                params.put("userId", loginUser.getId());
            }

            params.put("state", 2);//订单状态
            params.put("billType", 1);//订单属性
        }
        List<Bill> billList = billMapper.selectBillGroupByWebsite(params);
        //视图对象
        if (!CollectionUtils.isEmpty(billList)) {
            List<BillManageList> billManageListList = new ArrayList<BillManageList>();
            for (Bill item : billList
                    ) {
                //填充对象
                BillManageList billManageList = new BillManageList();
                billManageList.setId(i += 1);
                billManageList.setSqlId(item.getId());
                //客户
                BillPrice billPrice = new BillPrice();
                billPrice.setBillId(item.getId());
                if (loginUser.hasRole("ASSISTANT") || loginUser.hasRole("COMMISSIONER")) {
                    billPrice.setInMemberId(loginUser.getCreateUserId());
                } else {
                    billPrice.setInMemberId(loginUser.getId());
                }

                List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);//通过单价表的outmemberId获取用户
                if (!CollectionUtils.isEmpty(billPriceList)) {
                    Long userId = billPriceList.get(0).getOutMemberId();
                    User user = userMapper.selectByPrimaryKey(userId);
                    billManageList.setUserName(user.getUserName());
                }
                billManageList.setWebSite(item.getWebsite());
                //一个网址对应的关键词数
                Map<String, Object> websiteParams = new HashMap<>();
                websiteParams.put("website", item.getWebsite());
                List<Bill> bills = billMapper.selectAllSelective(websiteParams);

                if (!CollectionUtils.isEmpty(bills)) {
                    billManageList.setBillCount(bills.size());//任务数
                    int count = 0;//达标数
                    double dayConsumption = 0;
                    double monthConsumption = 0;
                    for (Bill billitem : bills
                            ) {
                        BillPrice billPrices = new BillPrice();
                        billPrices.setBillId(billitem.getId());
                        if (loginUser.hasRole("COMMISSIONER") || loginUser.hasRole("ASSISTANT")) {
                            billPrices.setInMemberId(loginUser.getCreateUserId());
                        } else {
                            billPrices.setInMemberId(loginUser.getId());
                        }
                        List<BillPrice> billPriceLists = billPriceMapper.selectByBillPrice(billPrices);
                        //循环判断当前关键词是否达标
                        if (!CollectionUtils.isEmpty(billPriceLists)) {
                            for (BillPrice billPriceItem : billPriceLists
                                    ) {
                                if (billitem.getNewRanking() <= billPriceItem.getBillRankingStandard()) {
                                    count += 1;
                                    //消费表
                                    Map<String, Object> priceMap = new HashMap<>();
                                    priceMap.put("BillId", billitem.getId());
                                    priceMap.put("date", dateNow);
                                    priceMap.put("priceId", billPriceItem.getId());
                                    BillCost billCost1 = billCostMapper.selectByBillIdAndDate(priceMap);
                                    if (billCost1 != null) {
                                        dayConsumption += (billCost1.getCostAmount().doubleValue());
                                    }
                                    break;
                                }
                            }
                        }
                        //计算每个关键词本月的消费
                        //monthConsumption+=billCostMapper.selectByBillCostOfMonth()


                    }
                    double keywordsCompletionRate = ((double) count / bills.size()) * 100;
                    //关键词达标率
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                    billManageList.setKeywordsCompletionRate(df.format(keywordsCompletionRate).toString());
                    //优化天数
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式时间
                    Date date = item.getCreateTime();
                    Date now = new Date();
                    int days = (int) ((now.getTime() - date.getTime()) / (1000 * 3600 * 24));//两个日期相差的天数
                    billManageList.setOptimizationDays(days);
                    billManageList.setDayConsumption(Double.parseDouble(df.format(dayConsumption)));


                }
                billManageListList.add(billManageList);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("rows", billManageListList);
            map.put("total", billList.size());
            return map;
        } else {
            return null;
        }

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
                        BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                        billDetails.setSearchName(billSearchSupport.getSearchSupport());
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
        cal.add(Calendar.DATE,-1);
        Date yesterDay=cal.getTime();
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
