package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.orderLeaseMapper;
import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.model.LeaseHall;
import com.yipeng.bill.bms.service.SiteLeaseService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SiteLeaseServiceImpl implements SiteLeaseService {
    @Autowired
    orderLeaseMapper orderLeaseMapper;

    @Override
    public Map<String, Object> GetMission(Map<String, Object> params, LoginUser loginUser) {
        params.put("allotId", loginUser.getId());
        Long total = orderLeaseMapper.selectByAllotIdCount(params);
        List<LeaseHall> leaseHallList = orderLeaseMapper.selectByAllotId(params);
        for (LeaseHall item:leaseHallList
             ) {
            params.put("website", item.getWebsite());
            item.setKeywordNum(orderLeaseMapper.selectByKeywordCount(params));
            item.setHomePageNum(orderLeaseMapper.selectHomePageCount(params));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", leaseHallList);
        return map;
    }

    @Override
    public List<HallDetails> GetDetails(String website,LoginUser loginUser) {
        Map<String,Object> map=new HashMap<>();
        map.put("website",website);
        map.put("allotId",loginUser.getId());
        List<HallDetails> leaseList=orderLeaseMapper.selectByWebsite(map);
        return leaseList;
    }
}
