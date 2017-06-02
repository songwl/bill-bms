package com.yipeng.bill.bms.service.impl;

import com.google.gson.JsonObject;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.KeywordsPriceMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.KeywordsPrice;
import com.yipeng.bill.bms.service.RankingUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private KeywordsPriceMapper keywordsPriceMapper;
    @Override
    public int updateKeywords(JsonObject json1) {
        KeywordsPrice keywordsPrice = keywordsPriceMapper.selectByTaskId(Integer.parseInt(json1.get("TaskId").toString()));
        if(keywordsPrice==null)
        {
            return 1;
        }
        keywordsPrice.setIndexbaiduall(Integer.parseInt(json1.get("IndexBaiduAll").toString()));
        keywordsPrice.setIndexbaiduwap(Integer.parseInt(json1.get("IndexBaiduWap").toString()));
        keywordsPrice.setIndexsoall(Integer.parseInt(json1.get("IndexSoAll").toString()));
        keywordsPrice.setBaiducollectioncount(Integer.parseInt(json1.get("BaiduCollectionCount").toString()));
        keywordsPrice.setBaiduhomepagecount(Integer.parseInt(json1.get("BaiduHomepageCount").toString()));
        keywordsPrice.setDegree(Integer.parseInt(json1.get("Degree").toString()));
        keywordsPrice.setPricebaidupc(Double.parseDouble(json1.get("PriceBaiduPc").toString()));
        keywordsPrice.setPricebaiduwap(Double.parseDouble(json1.get("PriceBaiduWap").toString()));
        keywordsPrice.setPricesopc(Double.parseDouble(json1.get("PriceSoPc").toString()));
        keywordsPrice.setPricesowap(Double.parseDouble(json1.get("PriceSoWap").toString()));
        keywordsPrice.setPricesogoupc(Double.parseDouble(json1.get("PriceSogouPc").toString()));
        keywordsPrice.setPricesogouwap(Double.parseDouble(json1.get("PriceSogouWap").toString()));
        keywordsPrice.setPricesm(Double.parseDouble(json1.get("PriceSm").toString()));
        keywordsPrice.setUpdatetime(new Date());
        int a = keywordsPriceMapper.updateByPrimaryKeySelective(keywordsPrice);
        return a > 0 ? 1 : 2;
    }
}
