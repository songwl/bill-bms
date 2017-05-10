package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.service.RankingUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/10.
 */
@Service
public class RankingUpdateServiceImpl implements RankingUpdateService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public int updateRanking(int TaskId, int RankLast, int RankFirst) {
       //通过订单taskId更新数据库
        Map<String,Object> params=new HashMap<>();
        params.put("webAppId",TaskId);
        List<Bill> billList=billMapper.selectAllSelective(params);
        //判断是否为空
        if (!CollectionUtils.isEmpty(billList))
        {
            //更新排名
            Bill bill=new Bill();
            bill.setId(billList.get(0).getId());
            bill.setNewRanking(RankLast);
            bill.setFirstRanking(RankFirst);
            billMapper.updateByPrimaryKeySelective(bill);

            return 1;
        }
        else
        {

            return 0;
        }

    }
}
