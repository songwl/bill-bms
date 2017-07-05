package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.SiteLeaseService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SiteLeaseServiceImpl implements SiteLeaseService {
    @Autowired
    private orderLeaseMapper orderLeaseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private offersetMapper offersetMapper;
    @Autowired
    private FundItemMapper fundItemMapper;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private penaltyRecordMapper penaltyRecordMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private KeywordsPriceMapper keywordsPriceMapper;
    @Autowired
    private BillSearchSupportMapper billSearchSupportMapper;
    @Autowired
    private leaseOverdueTbMapper leaseOverdueTbMapper;


    @Override
    public Map<String, Object> AdminGetMission(Map<String, Object> params, LoginUser loginUser) {
        Long total = orderLeaseMapper.selectAllCount(params);
        List<Map<String, Object>> leaseHallList = orderLeaseMapper.selectAll(params);
        for (int i = 0; i < leaseHallList.size(); i++) {
            params.put("website", leaseHallList.get(i).get("website"));
            if (leaseHallList.get(i).get("receiveId") != null) {
                leaseHallList.get(i).put("username", userMapper.selectByPrimaryKey(Long.parseLong(leaseHallList.get(i).get("receiveId").toString())).getUserName());
            }
            leaseHallList.get(i).put("keywordNum", orderLeaseMapper.selectByKeywordCount(params));
            leaseHallList.get(i).put("homePageNum", orderLeaseMapper.selectHomePageCount(params));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", leaseHallList);
        return map;
    }

    @Override
    public Map<String, Object> GetMission(Map<String, Object> params, LoginUser loginUser) {
        params.put("allotId", loginUser.getId());
        Long total = orderLeaseMapper.selectByAllotIdCount(params);
        List<Map<String, Object>> leaseHallList = orderLeaseMapper.selectByAllotId(params);
        for (int i = 0; i < leaseHallList.size(); i++) {
            params.put("website", leaseHallList.get(i).get("website"));
            if (leaseHallList.get(i).get("receiveId") != null) {
                leaseHallList.get(i).put("username", userMapper.selectByPrimaryKey(Long.parseLong(leaseHallList.get(i).get("receiveId").toString())).getUserName());
            }
            leaseHallList.get(i).put("keywordNum", orderLeaseMapper.selectByKeywordCount(params));
            leaseHallList.get(i).put("homePageNum", orderLeaseMapper.selectHomePageCount(params));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", leaseHallList);
        return map;
    }

    @Override
    public Map<String, Object> GetReceiveIdMission(Map<String, Object> params, LoginUser loginUser) {
        params.put("receiveId", loginUser.getId());
        Long total = orderLeaseMapper.selectByReceiveIdCount(params);
        List<Map<String, Object>> leaseHallList = orderLeaseMapper.selectByReceiveId(params);
        for (int i = 0; i < leaseHallList.size(); i++) {
            params.put("website", leaseHallList.get(i).get("website"));
            if (leaseHallList.get(i).get("receiveId") != null) {
                leaseHallList.get(i).put("username", userMapper.selectByPrimaryKey(Long.parseLong(leaseHallList.get(i).get("receiveId").toString())).getUserName());
            }
            leaseHallList.get(i).put("keywordNum", orderLeaseMapper.selectByKeywordCount(params));
            leaseHallList.get(i).put("homePageNum", orderLeaseMapper.selectHomePageCount(params));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", leaseHallList);
        return map;
    }

    @Override
    public Map<String, Object> GetAgentIdMission(Map<String, Object> params, LoginUser loginUser) {
        params.put("receiveId", loginUser.getCreateUserId());
        params.put("reserveId", loginUser.getId());
        Long total = orderLeaseMapper.selectByReceiveIdCount(params);
        List<Map<String, Object>> leaseHallList = orderLeaseMapper.selectByReceiveId(params);
        for (int i = 0; i < leaseHallList.size(); i++) {
            params.put("website", leaseHallList.get(i).get("website"));
            if (leaseHallList.get(i).get("receiveId") != null) {
                leaseHallList.get(i).put("username", userMapper.selectByPrimaryKey(Long.parseLong(leaseHallList.get(i).get("receiveId").toString())).getUserName());
            }
            leaseHallList.get(i).put("keywordNum", orderLeaseMapper.selectByKeywordCount(params));
            leaseHallList.get(i).put("homePageNum", orderLeaseMapper.selectHomePageCount(params));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", leaseHallList);
        return map;
    }

    @Override
    public List<Map<String, Object>> GetDetails(String website, LoginUser loginUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("website", website);
        //map.put("allotId", loginUser.getId());
        if (loginUser.hasRole("DISTRIBUTOR")) {
            offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
            map.put("rote", offerset.getRate());
        } else if (loginUser.hasRole("AGENT")) {
            offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
            offerset offerset1 = offersetMapper.selectByUserId(loginUser.getCreateUserId());
            double rote = offerset.getRate() * offerset1.getRate();
            rote = Double.parseDouble(new java.text.DecimalFormat("#.00").format(rote));
            map.put("rote", rote);
        } else {
            map.put("rote", 1);
        }
        List<Map<String, Object>> leaseList = orderLeaseMapper.selectByWebsite(map);
        return leaseList;
    }

    @Override
    public ResultMessage DivideOrder(Map<String, Object> map) {
        int num = orderLeaseMapper.updateByWebsiteNoReserve(map);
        ResultMessage resultMessage = ResultMessage.create();
        resultMessage.setCode(num > 0 ? 1 : 0);
        return resultMessage;
    }

    @Override
    public ResultMessage ReserveOrder(String website, int type, LoginUser loginUser) {
        Map<String, Object> map = new HashMap<>();
        orderLease orderLease = orderLeaseMapper.selectReserveByWebsite(website);
        if (orderLease.getOrderstate() != 2 && orderLease.getOrderstate() != 3) {
            ResultMessage resultMessage1 = ResultMessage.create();
            resultMessage1.setCode(-2);
            resultMessage1.setMessage("该订单状态不允许预定");
            return resultMessage1;
        }
        String[] arr = {website};
        map.put("arr", arr);
        map.put("reservetime", new Date());
        if (type == 3) {
            if (orderLease.getReserveid().equals("")) {
                map.put("reserveid", loginUser.getId() + ",");
            } else {
                map.put("reserveid", orderLease.getReserveid() + loginUser.getId() + ",");
            }
            map.put("orderstate", type);
        } else {
            String reserveId = orderLease.getReserveid();
            String[] arr1 = reserveId.split(",");
            String reserve = "";
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i].equals(loginUser.getId().toString())) {
                    continue;
                }
                reserve += arr1[i] + ",";
            }
            if (reserve == "") {
                map.put("orderstate", type);
            }
            map.put("reserveid", reserve);
        }
        int num = orderLeaseMapper.updateByWebsite(map);
        ResultMessage resultMessage = ResultMessage.create();
        resultMessage.setCode(num > 0 ? 1 : 0);
        return resultMessage;
    }

    @Override
    public List<User> GetReserve(String website, LoginUser loginUser) {
        Map<String, Object> map = new HashMap<>();
        orderLease orderLease = orderLeaseMapper.selectReserveByWebsite(website);
        String[] arr = orderLease.getReserveid().split(",");
        map.put("arr", arr);
        List<User> userList = userMapper.selectUserNameById(map);
        return userList;
    }

    @Override
    public int Recharge(Map<String, Object> map, BigDecimal sumMoney, LoginUser loginUser) {
        FundAccount fundAccount = fundAccountMapper.selectByUserId(Long.parseLong(map.get("reserveid").toString()));
        if (fundAccount == null) {
            return 0;
        }
        fundAccount.setBalance(fundAccount.getBalance().add(sumMoney));
        fundAccount.setUpdateTime(new Date());
        fundAccount.setUpdateUserId(loginUser.getId());
        int num = fundAccountMapper.updateByPrimaryKeySelective(fundAccount);
        penaltyRecord penaltyRecord = new penaltyRecord();
        penaltyRecord.setFundaccountid(fundAccount.getId());
        penaltyRecord.setInitialmoney(Double.parseDouble(fundAccount.getBalance().subtract(sumMoney).toString()));
        penaltyRecord.setHappenmoney(Double.parseDouble(sumMoney.toString()));
        penaltyRecord.setBalancemoney(Double.parseDouble(fundAccount.getBalance().toString()));
        penaltyRecord.setUpdatetime(new Date());
        penaltyRecord.setItemtype(1);
        int num1 = penaltyRecordMapper.insert(penaltyRecord);
        /*FundItem fundItem = new FundItem();
        fundItem.setFundAccountId(fundAccount.getId());
        fundItem.setChangeAmount(sumMoney);
        fundItem.setBalance(fundAccount.getBalance());
        fundItem.setChangeTime(new Date());
        fundItem.setItemType("penalty");
        int num1 = fundItemMapper.insert(fundItem);*/
        int num2 = orderLeaseMapper.updateByWebsite(map);
        return (num > 0 && num1 > 0 && num2 > 0) ? 1 : 0;
    }

    @Override
    public Map<String, Object> CustomerGetMission(Map<String, Object> params, LoginUser loginUser) {
        params.put("reserveId", loginUser.getCreateUserId());
        params.put("customerId", loginUser.getId());
        Long total = orderLeaseMapper.selectByCustomerCount(params);
        List<Map<String, Object>> leaseHallList = orderLeaseMapper.selectByCustomer(params);
        for (int i = 0; i < leaseHallList.size(); i++) {
            params.put("website", leaseHallList.get(i).get("website"));
            if (leaseHallList.get(i).get("receiveId") != null) {
                leaseHallList.get(i).put("username", userMapper.selectByPrimaryKey(Long.parseLong(leaseHallList.get(i).get("receiveId").toString())).getUserName());
            }
            leaseHallList.get(i).put("keywordNum", orderLeaseMapper.selectByKeywordCount(params));
            leaseHallList.get(i).put("homePageNum", orderLeaseMapper.selectHomePageCount(params));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", leaseHallList);
        return map;
    }

    @Override
    public Map<String, Object> OrderDetails(Map<String, Object> map, LoginUser loginUser) {
        //map.put("allotId", loginUser.getId());
        if (loginUser.hasRole("DISTRIBUTOR")) {//渠道商
            offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
            map.put("rote", offerset.getRate());
        } else if (loginUser.hasRole("AGENT")) {//代理商
            offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
            offerset offerset1 = offersetMapper.selectByUserId(loginUser.getCreateUserId());
            double rote = offerset.getRate() * offerset1.getRate();
            rote = Double.parseDouble(new java.text.DecimalFormat("#.00").format(rote));
            map.put("rote", rote);
        } else if (loginUser.hasRole("CUSTOMER")) {//客户
            offerset offerset = offersetMapper.selectByUserId(loginUser.getCreateUserId());//代理商倍率
            User user = userMapper.selectByPrimaryKey(loginUser.getCreateUserId());
            offerset offerset1 = offersetMapper.selectByUserId(user.getCreateUserId());//渠道商倍率
            offerset offerset2 = offersetMapper.selectByUserId(loginUser.getId());//客户倍率
            double rote = offerset.getRate() * offerset1.getRate() * offerset2.getRate();
            rote = Double.parseDouble(new java.text.DecimalFormat("#.00").format(rote));
            map.put("rote", rote);
        } else {
            map.put("rote", 1);
        }
        int total = orderLeaseMapper.selectOrderDetailsByWebsiteCount(map);
        List<Map<String, Object>> leaseList = orderLeaseMapper.selectOrderDetailsByWebsite(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("total", total);
        map1.put("rows", leaseList);
        return map1;
    }

    @Override
    public int Ordering(Long[] arr, String website, LoginUser loginUser) {
        String userId = loginUser.getId().toString();
        String creatUserId = loginUser.getCreateUserId().toString();
        List<orderLease> leaseList = orderLeaseMapper.selectAllByWebsite(website);
        for (orderLease item : leaseList
                ) {
            if (item.getKeywordstate() == 0) {//是必选词
                if (item.getCustomerid() == null || !item.getCustomerid().equals(userId) || item.getOrderstate() != 5) {
                    continue;
                } else {
                    List<BillPrice> billPriceList = billPriceMapper.selectByBillId(item.getOrderid());
                    offerset offerset = offersetMapper.selectByUserId(item.getReceiveid());
                    double DRate = offerset.getRate();//渠道商倍率
                    offerset offerset1 = offersetMapper.selectByUserId(Long.parseLong(item.getReserveid()));
                    double ARate = offerset1.getRate();//代理商倍率
                    offerset offerset2 = offersetMapper.selectByUserId(Long.parseLong(item.getCustomerid()));
                    double CRate = offerset2.getRate();//客户倍率
                    BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(item.getOrderid());
                    KeywordsPrice keywordsPrice = keywordsPriceMapper.selectOneBykeyword(item.getKeywords());
                    double price = 0d;
                    switch (billSearchSupport.getSearchSupport()) {
                        case "百度":
                            price = keywordsPrice.getPricebaidupc();
                            break;
                        case "百度手机":
                            price = keywordsPrice.getPricebaiduwap();
                            break;
                        case "360":
                            price = keywordsPrice.getPricesopc();
                            break;
                        case "搜狗":
                            price = keywordsPrice.getPricesogoupc();
                            break;
                        case "神马":
                            price = keywordsPrice.getPricesm();
                            break;
                    }
                    BigDecimal bd1 = new BigDecimal(price * DRate);//优化方与渠道商价格
                    BigDecimal bd2 = new BigDecimal(price * DRate * ARate);//渠道商与代理商价格
                    BigDecimal bd3 = new BigDecimal(price * ARate * DRate * CRate);//代理商与客户价格
                    for (BillPrice itm : billPriceList
                            ) {
                        if (itm.getInMemberId() == 1) {
                            itm.setOutMemberId(item.getReceiveid());
                            itm.setPrice(bd1);
                            billPriceMapper.updateByPrimaryKeySelective(itm);
                        } else {
                            itm.setOutMemberId(Long.parseLong(item.getReserveid()));
                            itm.setInMemberId(item.getReceiveid());
                            itm.setPrice(bd2);
                            billPriceMapper.updateByPrimaryKeySelective(itm);
                        }
                    }
                    BillPrice billPrice = new BillPrice();
                    billPrice.setPrice(bd3);
                    billPrice.setInMemberId(Long.parseLong(item.getReserveid()));
                    billPrice.setOutMemberId(Long.parseLong(item.getCustomerid()));
                    billPrice.setBillRankingStandard(10l);
                    billPrice.setBillId(item.getOrderid());
                    billPrice.setCreateTime(new Date());
                    billPriceMapper.insert(billPrice);
                    item.setOrderstate(6);
                    orderLeaseMapper.updateByPrimaryKeySelective(item);
                }
            }
        }
        for (long item : arr
                ) {
            orderLease orderLease = orderLeaseMapper.selectByPrimaryKey(item);
            if (orderLease == null || orderLease.getOrderstate() != 5 || !orderLease.getReserveid().equals(creatUserId) || !orderLease.getCustomerid().equals(userId) || orderLease.getKeywordstate() == 0) {
                continue;
            } else {
                List<BillPrice> billPriceList = billPriceMapper.selectByBillId(orderLease.getOrderid());
                offerset offerset = offersetMapper.selectByUserId(orderLease.getReceiveid());
                double DRate = offerset.getRate();//渠道商倍率
                offerset offerset1 = offersetMapper.selectByUserId(Long.parseLong(orderLease.getReserveid()));
                double ARate = offerset1.getRate();//代理商倍率
                offerset offerset2 = offersetMapper.selectByUserId(Long.parseLong(orderLease.getCustomerid()));
                double CRate = offerset2.getRate();//客户倍率
                BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(orderLease.getOrderid());
                KeywordsPrice keywordsPrice = keywordsPriceMapper.selectOneBykeyword(orderLease.getKeywords());
                double price = 0d;
                switch (billSearchSupport.getSearchSupport()) {
                    case "百度":
                        price = keywordsPrice.getPricebaidupc();
                        break;
                    case "百度手机":
                        price = keywordsPrice.getPricebaiduwap();
                        break;
                    case "360":
                        price = keywordsPrice.getPricesopc();
                        break;
                    case "搜狗":
                        price = keywordsPrice.getPricesogoupc();
                        break;
                    case "神马":
                        price = keywordsPrice.getPricesm();
                        break;
                }
                BigDecimal bd1 = new BigDecimal(price * DRate);//优化方与渠道商价格
                BigDecimal bd2 = new BigDecimal(price * DRate * ARate);//渠道商与代理商价格
                BigDecimal bd3 = new BigDecimal(price * ARate * DRate * CRate);//代理商与客户价格
                for (BillPrice itm : billPriceList
                        ) {
                    if (itm.getInMemberId() == 1) {
                        itm.setOutMemberId(orderLease.getReceiveid());
                        itm.setPrice(bd1);
                        billPriceMapper.updateByPrimaryKeySelective(itm);
                    } else {
                        itm.setOutMemberId(Long.parseLong(orderLease.getReserveid()));
                        itm.setInMemberId(orderLease.getReceiveid());
                        itm.setPrice(bd2);
                        billPriceMapper.updateByPrimaryKeySelective(itm);
                    }
                }
                BillPrice billPrice = new BillPrice();
                billPrice.setPrice(bd3);
                billPrice.setInMemberId(Long.parseLong(orderLease.getReserveid()));
                billPrice.setOutMemberId(Long.parseLong(orderLease.getCustomerid()));
                billPrice.setBillRankingStandard(10l);
                billPrice.setBillId(orderLease.getOrderid());
                billPrice.setCreateTime(new Date());
                billPriceMapper.insert(billPrice);
                orderLease.setOrderstate(6);
                int num1 = orderLeaseMapper.updateByPrimaryKeySelective(orderLease);
            }
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> GetCustomer(LoginUser loginUser) {
        List<Map<String, Object>> mapList = userMapper.selectCustomer(loginUser.getId());
        return mapList;
    }

    @Override
    public int ConfirmCustomer(String website, LoginUser loginUser, String customer) {
        orderLease orderLease = orderLeaseMapper.selectReserveByWebsite(website);
        if (orderLease == null || !orderLease.getReserveid().equals(loginUser.getId() + "") || orderLease.getOrderstate() != 4) {
            return -2;
        }
        String[] arr = {website};
        Map<String, Object> map = new HashMap<>();
        map.put("customerid", customer);
        map.put("orderstate", 5);
        map.put("arr", arr);
        int num = orderLeaseMapper.updateByWebsite(map);
        return num > 0 ? 1 : 0;
    }

    @Override
    public void websiteLeaseOverdue() {
        //查询出所有订单状态在4到6之间的订单
        List<orderLease> orderLeaseList = orderLeaseMapper.selectOverdue();
        for (orderLease item : orderLeaseList
                ) {
            //查询某个网站里面是否包含已经订购的订单，如果未包含的话，判断从充值到现在是否超过六十天
            //如果超过六十天，则要将订单切换成可重新划分给渠道商的状态
            int num = orderLeaseMapper.selectHaveOrderCount(item.getWebsite());
            if (num == 0) {//没有包含已订购的订单
                Date date = item.getReservetime();//取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.MONTH, 2);//把月份往后增加两月.整数往后推,负数往前移动
                date = calendar.getTime();   //这个时间就是日期往后推一天的结果
                Date date1 = new Date();
                if (date.after(date1)) {//超过两个月了
                    String[] arr = {item.getWebsite()};
                    Map<String, Object> map = new HashMap<>();
                    map.put("arr", arr);
                    map.put("reserveid", "");
                    map.put("customerid", "");
                    map.put("orderstate", 3);
                    orderLeaseMapper.updateByWebsite(map);
                    leaseOverdueTb leaseOverdueTb = new leaseOverdueTb();
                    leaseOverdueTb.setKeyword(item.getKeywords());
                    leaseOverdueTb.setAllotid(item.getAllotid());
                    leaseOverdueTb.setKeywordstate(item.getKeywordstate());
                    leaseOverdueTb.setReceiveid(item.getReceiveid());
                    leaseOverdueTb.setReservetime(item.getReservetime());
                    leaseOverdueTb.setUpdatetime(new Date());
                    leaseOverdueTb.setReserveid(Long.parseLong(item.getReserveid()));
                    leaseOverdueTb.setOrderid(item.getOrderid());
                    leaseOverdueTb.setOrderstate(item.getOrderstate());
                    leaseOverdueTb.setWebsite(item.getWebsite());
                    leaseOverdueTbMapper.insert(leaseOverdueTb);
                }
            }
        }
    }
}
