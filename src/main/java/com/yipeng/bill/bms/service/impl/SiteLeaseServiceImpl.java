package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.model.LeaseHall;
import com.yipeng.bill.bms.service.SiteLeaseService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

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
        fundAccount.setBalance(fundAccount.getBalance().subtract(sumMoney));
        fundAccount.setUpdateTime(new Date());
        fundAccount.setUpdateUserId(loginUser.getId());
        int num = fundAccountMapper.updateByPrimaryKeySelective(fundAccount);
        FundItem fundItem = new FundItem();
        fundItem.setFundAccountId(fundAccount.getId());
        fundItem.setChangeAmount(sumMoney);
        fundItem.setBalance(fundAccount.getBalance());
        fundItem.setChangeTime(new Date());
        fundItem.setItemType("penalty");
        int num1 = fundItemMapper.insert(fundItem);
        int num2 = orderLeaseMapper.updateByWebsite(map);
        return (num2 > 0) ? 1 : 0;
    }

    @Override
    public Map<String, Object> CustomerGetMission(Map<String, Object> params, LoginUser loginUser) {
        params.put("reserveId", loginUser.getCreateUserId());
        Long total = orderLeaseMapper.selectByReserveIdCount(params);
        List<Map<String, Object>> leaseHallList = orderLeaseMapper.selectByReserveId(params);
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
            double rote = /*offerset.getRate() * offerset1.getRate()*/2;
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
        Map<String, Object> map = new HashMap<>();
        String[] arr1 = {website};
        map.put("arr", arr1);
        map.put("orderstate", 5);
        int num = orderLeaseMapper.updateByWebsite(map);
        if (num == 0) {
            return -1;
        }
        long userId = loginUser.getId();
        String creatUserId = loginUser.getCreateUserId().toString();
        for (long item : arr
                ) {
            orderLease orderLease = orderLeaseMapper.selectByPrimaryKey(item);
            if (orderLease == null || orderLease.getOrderstate() != 4 || !orderLease.getReserveid().equals(creatUserId)) {
                continue;
            }
            String[] ar = orderLease.getCustomerid().split(",");
            if (ar == null || !Arrays.asList(arr).contains(userId)) {//用户列表里面不包含当前登录人
                orderLease.setCustomerid(userId + ",");
            }
            orderLease.setOrderstate(5);
            int num1 = orderLeaseMapper.updateByPrimaryKeySelective(orderLease);
        }
        return 0;
    }
}
