package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.UserPowerMapper;
import com.yipeng.bill.bms.domain.UserPower;
import com.yipeng.bill.bms.service.StatisticsDataService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsDataServiceImpl implements StatisticsDataService {
    @Autowired
    private UserPowerMapper userPowerMapper;
    @Autowired
    private BillMapper billMapper;

    @Override
    public Map<String, Object> GetWebsiteMonitor(Map<String, Object> map, LoginUser loginUser) {
        UserPower userPower = userPowerMapper.selectByUserId(loginUser.getId());
        List<Long> longList = billMapper.selectByLeasePower(map);
        Map<String, Object> map1 = new HashMap<>();
        if (loginUser.hasRole("SUPER_ADMIN") || (userPower != null && userPower.getPowerid() == 0)) {//总账号
            map1.put("list", longList);
        } else {
            map1.put("list", new Long[]{loginUser.getId()});
        }
        List<Map<String, Object>> KeywordNumList = billMapper.selectKeywordNumByAscriptionRanking(map1);
        map1.put("ranking", 10);
        List<Integer> RankingList = billMapper.selectKeywordNumByAscription(map1);
        map1.put("ranking", 3);
        List<Integer> TopThreeList = billMapper.selectKeywordNumByAscription(map1);
        for (int i = 0; i < KeywordNumList.size(); i++) {
            KeywordNumList.get(i).put("Ranking", RankingList.get(i).toString());
            KeywordNumList.get(i).put("TopThree", TopThreeList.get(i).toString());
        }
        int total = billMapper.selectByLeasePowerCount();
        List<Map<String, Object>> mapList = KeywordNumList;
        Map<String, Object> map2 = new HashMap<>();
        map2.put("total", total);
        map2.put("rows", mapList);
        return map2;
    }

    @Override
    public Map<String, Object> LoadFa(LoginUser loginUser) {
        List<Long> longList = billMapper.selectAllByLeasePower();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("list", longList);
        int KeywordNum = billMapper.selectAllKeywordNumByAscription(map1);
        map1.put("ranking", 10);
        int Rankingnum = billMapper.selectAllKeywordNumByAscription(map1);
        map1.put("ranking", 3);
        int TopThreenum = billMapper.selectAllKeywordNumByAscription(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("UserNum", longList.size());
        map2.put("KeywordNum", KeywordNum);
        map2.put("Rankingnum", Rankingnum);
        map2.put("TopThreenum", TopThreenum);
        return map2;
    }
}
