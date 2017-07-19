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
        if (keywordsPrice == null) {
            return 1;
        }
        Long IndexBaiduAll = Long.parseLong(json1.get("IndexBaiduAll").toString());
        Long IndexSoAll = Long.parseLong(json1.get("IndexSoAll").toString());
        Long BaiduCollectionCount = Long.parseLong(json1.get("BaiduCollectionCount").toString());
        Long BaiduHomepageCount = Long.parseLong(json1.get("BaiduHomepageCount").toString());
        double index = 0;//指数
        double BasicOffer = 0;//基础
        if (IndexBaiduAll != 0) {
            index = IndexBaiduAll;
        } else {
            index = IndexSoAll;
        }
        BasicOffer = ((index / 120) + (BaiduCollectionCount / 1300000)) + (BaiduHomepageCount / 500 + 1);
        double price = 0;//价格
        int len = keywordsPrice.getKeywords().length();
        switch (len) {
            case 1:
                if (BasicOffer * 3 < 30) {
                    price = 30;
                } else {
                    price = BasicOffer * 3;
                }
                break;
            case 2:
                if (BasicOffer * 2 < 6) {
                    price = 6;
                } else {
                    price = BasicOffer * 2;
                }
                break;
            case 3:
                if (BasicOffer * 1.4 < 5) {
                    price = 5;
                } else {
                    price = BasicOffer * 1.4;
                }
                break;
            case 4:
                if (BasicOffer * 1.3 < 4) {
                    price = 4;
                } else {
                    price = BasicOffer * 1.3;
                }
                break;
            case 5:
                if (BasicOffer * 1.2 < 3) {
                    price = 3;
                } else {
                    price = BasicOffer * 1.2;
                }
                break;
            case 6:
                if (BasicOffer * 1.1 < 2.5) {
                    price = 2.5;
                } else {
                    price = BasicOffer * 1.1;
                }
                break;
            default:
                if (BasicOffer < 2) {
                    price = 2;
                } else {
                    price = BasicOffer;
                }
                break;
        }
        double PriceBaiduPc = price;
        double PriceBaiduWap = price * 1.2;
        double PriceSoPc = price * 0.3;
        double PriceSogouPc = price * 0.2;
        double PriceSm = price * 0.3;
        keywordsPrice.setIndexbaiduall(Integer.parseInt(json1.get("IndexBaiduAll").toString()));
        keywordsPrice.setIndexbaiduwap(Integer.parseInt(json1.get("IndexBaiduWap").toString()));
        keywordsPrice.setIndexsoall(Integer.parseInt(json1.get("IndexSoAll").toString()));
        keywordsPrice.setBaiducollectioncount(Integer.parseInt(json1.get("BaiduCollectionCount").toString()));
        keywordsPrice.setBaiduhomepagecount(Integer.parseInt(json1.get("BaiduHomepageCount").toString()));
        keywordsPrice.setDegree(Integer.parseInt(json1.get("Degree").toString()));
        keywordsPrice.setPricebaidupc(PriceBaiduPc);
        keywordsPrice.setPricebaiduwap(PriceBaiduWap);
        keywordsPrice.setPricesopc(PriceSoPc);
        keywordsPrice.setPricesowap(PriceSoPc);
        keywordsPrice.setPricesogoupc(PriceSogouPc);
        keywordsPrice.setPricesogouwap(PriceSogouPc);
        keywordsPrice.setPricesm(PriceSm);
        keywordsPrice.setUpdatetime(new Date());
        int a = keywordsPriceMapper.updateByPrimaryKeySelective(keywordsPrice);
        return a > 0 ? 1 : 2;
    }

}
