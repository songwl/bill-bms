package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.vo.BillDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/3/10.
 */
@Service
public class BillServiceimpl implements BillService {
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private BillSearchSupportMapper billSearchSupportMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BillCostMapper billCostMapper;
    /**
     * 实现新增订单(判断都未做)
     * @param bill
     * @return
     */
    @Override
    public int saveBill(User user , String search,String url,String keyword,Long rankend,Long price,Long rankend1,Long price1,Long rankend2,Long price2,Long rankend3,Long price3) {

        String[] urls=url.split("\n");
        String[] keywords=keyword.split("\n");
        if(urls.length==keywords.length)
        {
             for(int i=0;i<urls.length;i++)
             {
                //订单主表
                 Bill bill=new Bill();
                 bill.setWebsite(urls[i]);
                 bill.setKeywords(keywords[i]);
                 bill.setCreateUserId(user.getId());
                 bill.setCreateTime(new Date());
                 bill.setFirstRanking(51);
                 bill.setNewRanking(51);
                 bill.setWebAppId(123456);
                 bill.setDayOptimization(1);
                 bill.setAllOptimization(1);
                 bill.setState(1);
                 Long billId=billMapper.insert(bill);
                 //订单引擎表
                 BillSearchSupport billSearchSupport=new BillSearchSupport();
                 billSearchSupport.setBillId(bill.getId());
                 billSearchSupport.setSearchSupport(search);
                 billSearchSupportMapper.insert(billSearchSupport);
                 //订单单价表
                 BillPrice billPrice=new BillPrice();
                 billPrice.setBillId(bill.getId());
                 billPrice.setPrice(price);
                 billPrice.setBillRankingStandard(rankend);
                 billPrice.setInMemberId(user.getId());
                 billPrice.setCreateTime(new Date());
                 billPriceMapper.insert(billPrice);

             }

        }



        return  1;
    }

    /**
     * 实现通过主键ID查订单
     * @param id
     * @return
     */
    @Override
    public Bill getBillById(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    /**
     * 实现通过参数来查询集合
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findBillList(Map<String, Object> params) {
        //先查出订单
        List<Bill> bills=billMapper.selectList(params);
        //视图模型
        List<BillDetails>  billDetails=new ArrayList<BillDetails>();
        //循环判断填充数据
        for (Bill bill:bills
             ) {
            BillDetails billDetails1=new BillDetails();
            billDetails1.setId(bill.getId());
            //获取客户名称
            Object object=params.get("user");
            User user=(User)object;
            billDetails1.setUserName(user.getUserName());
            billDetails1.setKeywords(bill.getKeywords());
            billDetails1.setWebsite(bill.getWebsite());
            //获取搜索引擎名称
            BillSearchSupport billSearchSupport=billSearchSupportMapper.selectByBillId(bill.getId());
            billDetails1.setSearchName(billSearchSupport.getSearchSupport());
            //转换时间("yy-MM-dd")
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(bill.getCreateTime());
            billDetails1.setCreateTime(dateString);
            billDetails1.setFirstRanking(bill.getFirstRanking());
            billDetails1.setNewRanking(bill.getNewRanking());

            List<BillPrice>  billPrice=billPriceMapper.selectByBillId(bill.getId());
            BillPrice billPrice1= billPrice.get(0);
            billDetails1.setPriceOne(billPrice1.getPrice());
           // if(billPrice.get(1)!=null)
           // {
             //   BillPrice billPrice2= billPrice.get(1);
             //   billDetails1.setPriceTwo(billPrice2.getPrice());
            //}
            billDetails1.setDayOptimization(bill.getDayOptimization());
            billDetails1.setAllOptimization(bill.getAllOptimization());
            //查询今日消费
            List<BillCost> billCost=billCostMapper.selectByBillId(bill.getId());
            for (BillCost billCost1:billCost
                 ) {
                String costDate= formatter.format(billCost1.getCostDate());
                String costDate1= formatter.format(new Date());
                if(costDate.equals(costDate1))
                {
                    billDetails1.setDayConsumption(billCost1.getCostAmount());
                }
            }
           billDetails1.setState(bill.getState());


            billDetails.add(billDetails1);
        }


        Long total=billMapper.getBillListCount();
        Map<String, Object> modelMap = new HashMap();
        modelMap.put("total",total);
        modelMap.put("rows",billDetails);
        return modelMap;
    }

}
