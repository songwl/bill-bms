package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.dao.offersetMapper;
import com.yipeng.bill.bms.dao.orderLeaseMapper;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.offerset;
import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.model.LeaseHall;
import com.yipeng.bill.bms.service.SiteLeaseService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SiteLeaseServiceImpl implements SiteLeaseService {
    @Autowired
    private orderLeaseMapper orderLeaseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private offersetMapper offersetMapper;

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
        }/* else if (loginUser.hasRole("AGENT")) {
            User user = userMapper.selectByPrimaryKey(loginUser.getId());
            offerset offerset = offersetMapper.selectByUserId(user.getCreateUserId());
            map.put("rote", offerset.getRate());
        }*/ else {
            map.put("rote", 1);
        }
        List<Map<String, Object>> leaseList = orderLeaseMapper.selectByWebsite(map);
        return leaseList;
    }

    @Override
    public ResultMessage DivideOrder(Map<String, Object> map) {
        int num = orderLeaseMapper.updateByWebsite(map);
        ResultMessage resultMessage = ResultMessage.create();
        resultMessage.setCode(num > 0 ? 1 : 0);
        return resultMessage;
    }

    @Override
    public ResultMessage ReserveOrder(String website, int type, LoginUser loginUser) {
        Map<String, Object> map = new HashMap<>();
        orderLease orderLease = orderLeaseMapper.selectReserveByWebsite(website);
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
}
