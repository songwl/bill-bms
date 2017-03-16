package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.vo.BillDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
    public String saveBill(User user , String search, String url, String keyword, Long rankend, BigDecimal price, Long rankend1, BigDecimal price1, Long rankend2, BigDecimal price2, Long rankend3, BigDecimal price3) {

        String[] urls=url.split("\n");
        String[] keywords=keyword.split("\n");
        String errorDetails="";
        if(urls.length==keywords.length)
        {

             for(int i=0;i<urls.length;i++) {
                 //先查询是否订单已经存在
                 Map<String, Object> params = new HashMap();
                 params.put("website", urls[i]);
                 params.put("keywords", keywords[i]);
                 List<Bill> billList = billMapper.selectAllSelective(params);
                 if (billList != null) {
                     Boolean bool = true;
                     for (Bill bill : billList
                             ) {
                         //查询每个订单对应的搜索引擎名
                         BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                         if (billSearchSupport.getSearchSupport().equals(search)) {
                             bool = false;
                             errorDetails += +(i + 1) + "网址：" + urls[i] + "  关键词：" + keywords[i] + " ";

                         }
                     }

                     if (bool) {
                         //订单主表
                         Bill bill = new Bill();
                         bill.setWebsite(urls[i]);
                         bill.setKeywords(keywords[i]);
                         bill.setCreateUserId(user.getId());
                         bill.setCreateTime(new Date());
                         bill.setFirstRanking(51);
                         bill.setNewRanking(51);
                         bill.setWebAppId(123456);
                         bill.setStandardDays(0);
                         bill.setDayOptimization(1);
                         bill.setAllOptimization(1);
                         bill.setState(1);
                         Long billId = billMapper.insert(bill);
                         //订单引擎表
                         BillSearchSupport billSearchSupport = new BillSearchSupport();
                         billSearchSupport.setBillId(bill.getId());
                         billSearchSupport.setSearchSupport(search);
                         billSearchSupportMapper.insert(billSearchSupport);
                         //订单单价表
                         BillPrice billPrice = new BillPrice();
                         billPrice.setBillId(bill.getId());
                         billPrice.setPrice(price);
                         billPrice.setBillRankingStandard(rankend);
                         billPrice.setInMemberId(user.getId());
                         billPrice.setCreateTime(new Date());
                         billPriceMapper.insert(billPrice);
                         if (price1 != null && rankend1 != null) {
                             BillPrice billPrice1 = new BillPrice();
                             billPrice1.setBillId(bill.getId());
                             billPrice1.setPrice(price1);
                             billPrice1.setBillRankingStandard(rankend1);
                             billPrice1.setInMemberId(user.getId());
                             billPrice1.setCreateTime(new Date());
                             billPriceMapper.insert(billPrice1);
                             if (price2 != null && rankend2 != null) {
                                 BillPrice billPrice2 = new BillPrice();
                                 billPrice2.setBillId(bill.getId());
                                 billPrice2.setPrice(price2);
                                 billPrice2.setBillRankingStandard(rankend2);
                                 billPrice2.setInMemberId(user.getId());
                                 billPrice2.setCreateTime(new Date());
                                 billPriceMapper.insert(billPrice2);
                                 if (price3 != null && rankend3 != null) {
                                     BillPrice billPrice3 = new BillPrice();
                                     billPrice3.setBillId(bill.getId());
                                     billPrice3.setPrice(price);
                                     billPrice3.setBillRankingStandard(rankend3);
                                     billPrice3.setInMemberId(user.getId());
                                     billPrice3.setCreateTime(new Date());
                                     billPriceMapper.insert(billPrice3);

                                 }
                             }
                         }

                     }
                 }
             }
        }



        return  errorDetails;
    }

    /**
     * 实现不同价导入
     * @param user
     * @param dfsearch
     * @param dfurl
     * @param dfkeyword
     * @param dfrankend
     * @param dfprice
     * @return
     */
    @Override
    public String savaDiffrentBill(User user, String dfsearch, String dfurl, String dfkeyword, Long dfrankend, String dfprice) {
        String[] dfurls=dfurl.split("\n");
        String[] dfkeywords=dfkeyword.split("\n");
        String[] dfprices=dfprice.split("\n");
        String errorDetails="";
        if(dfurls.length==dfkeywords.length&&dfkeywords.length==dfprices.length)
        {
            for(int i=0;i<dfurls.length;i++)
            {
                //先查询是否订单已经存在
                Map<String,Object> params=new HashMap();
                params.put("website",dfurls[i]);
                params.put("keywords",dfkeywords[i]);
                List<Bill> billList=billMapper.selectAllSelective(params);
            //还有else判断
              if(billList!=null)
              {
                  Boolean bool=true;
                  for (Bill bill:billList
                          ) {
                     //查询每个订单对应的搜索引擎名
                      BillSearchSupport billSearchSupport=billSearchSupportMapper.selectByBillId(bill.getId());
                      if (billSearchSupport.getSearchSupport().equals(dfsearch))
                      {
                          bool=false;
                          errorDetails+=+(i+1)+"网址："+dfurls[i]+"  关键词："+dfkeywords[i]+" ";

                      }
                  }
                  if(bool)
                  {

                      Bill bill=new Bill();
                      bill.setWebsite(dfurls[i]);
                      bill.setKeywords(dfkeywords[i]);
                      bill.setCreateUserId(user.getId());
                      bill.setCreateTime(new Date());
                      bill.setFirstRanking(51);
                      bill.setNewRanking(51);
                      bill.setWebAppId(123456);
                      bill.setStandardDays(0);
                      bill.setDayOptimization(1);
                      bill.setAllOptimization(1);
                      bill.setState(1);
                      Long billId=billMapper.insert(bill);
                      //订单引擎表
                      BillSearchSupport billSearchSupport=new BillSearchSupport();
                      billSearchSupport.setBillId(bill.getId());
                      billSearchSupport.setSearchSupport(dfsearch);
                      billSearchSupportMapper.insert(billSearchSupport);
                      //订单单价表
                      BillPrice billPrice=new BillPrice();
                      billPrice.setBillId(bill.getId());
                      BigDecimal bd=new BigDecimal(dfprices[i]);
                      billPrice.setPrice(bd);
                      billPrice.setBillRankingStandard(dfrankend);
                      billPrice.setInMemberId(user.getId());
                      billPrice.setCreateTime(new Date());
                      billPriceMapper.insert(billPrice);

                  }
              }

            }
        }

        return errorDetails;
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
            if(billPrice.size()>0)
            {
                BillPrice billPrice1= billPrice.get(0);
                billDetails1.setPriceOne(billPrice1.getPrice());
                if(billPrice.size()>=2)
                {
                    BillPrice billPrice2= billPrice.get(1);
                    billDetails1.setPriceTwo(billPrice2.getPrice());
                }
            }




            billDetails1.setStandardDays(bill.getStandardDays());
            billDetails1.setDayOptimization(bill.getDayOptimization());
            billDetails1.setAllOptimization(bill.getAllOptimization());
            //查询今日消费
            List<BillCost> billCost=billCostMapper.selectByBillId(bill.getId());
            if(billCost.size()>0)
            {
                for (BillCost billCost1:billCost
                        ) {
                    String costDate= formatter.format(billCost1.getCostDate());
                    String costDate1= formatter.format(new Date());
                    if(costDate.equals(costDate1))
                    {
                        billDetails1.setDayConsumption(billCost1.getCostAmount());
                    }
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

    @Override
    public int updateBillPrice(Map<String, String[]>  params,User user) {



        return 0;
    }

}
