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
     * 相同价导入
     * @param bill
     * @return
     */
    @Override
    public String saveSameBill(Map<String, String[]>  params,User user ) {

        String[] urlsArr=params.get("url");
        String[] keywordsArr=params.get("keyword");
        String[] rankend=params.get("rankend");
        String[] price=params.get("price");
        String[] rankend1=params.get("rankend1");
        String[] price1=params.get("price1");
        String[] rankend2=params.get("rankend2");
        String[] price2=params.get("price2");
        String[] rankend3=params.get("rankend3");
        String[] price3=params.get("price3");
        String[] urls=urlsArr[0].split("\n");
        String[] keywords=keywordsArr[0].split("\n");
        String[] search=params.get("search");
        String errorDetails="";
        if(urls.length==keywords.length) {

            for (int i = 0; i < urls.length; i++) {
                //先查询是否订单已经存在
                Map<String, Object> params1 = new HashMap();
                params1.put("website", urls[i]);
                params1.put("keywords", keywords[i]);
                List<Bill> billList = billMapper.selectAllSelective(params1);
                //存在 判断搜索引擎是否一致
                if (billList.size() > 0) {
                    Boolean bool = true;
                    for (Bill bill : billList
                            ) {
                        //查询每个订单对应的搜索引擎名
                        BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                        if (billSearchSupport.getSearchSupport().equals(search[0])) {
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
                        bill.setState(0);
                        Long billId = billMapper.insert(bill);
                        //订单引擎表
                        BillSearchSupport billSearchSupport = new BillSearchSupport();
                        billSearchSupport.setBillId(bill.getId());
                        billSearchSupport.setSearchSupport(search[0]);
                        billSearchSupportMapper.insert(billSearchSupport);
                        //订单单价表
                        BillPrice billPrice = new BillPrice();
                        billPrice.setBillId(bill.getId());
                        BigDecimal bigDecimalprice = new BigDecimal(price[0]);
                        billPrice.setPrice(bigDecimalprice);
                        billPrice.setBillRankingStandard(Long.parseLong(rankend[0]));
                        billPrice.setInMemberId(user.getId());
                        billPrice.setCreateTime(new Date());
                        billPriceMapper.insert(billPrice);
                        if (price1[0] != "" && rankend1[0] != "") {
                            BillPrice billPrice1 = new BillPrice();
                            billPrice1.setBillId(bill.getId());
                            BigDecimal bigDecimalprice1 = new BigDecimal(price1[0]);
                            billPrice1.setPrice(bigDecimalprice1);
                            billPrice1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                            billPrice1.setInMemberId(user.getId());
                            billPrice1.setCreateTime(new Date());
                            billPriceMapper.insert(billPrice1);
                            if (price2[0] != "" && rankend2[0] != "") {
                                BillPrice billPrice2 = new BillPrice();
                                billPrice2.setBillId(bill.getId());
                                BigDecimal bigDecimalprice2 = new BigDecimal(price2[0]);
                                billPrice2.setPrice(bigDecimalprice2);
                                billPrice2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                                billPrice2.setInMemberId(user.getId());
                                billPrice2.setCreateTime(new Date());
                                billPriceMapper.insert(billPrice2);
                                if (price3[0] != "" && rankend3[0] != "") {
                                    BillPrice billPrice3 = new BillPrice();
                                    billPrice3.setBillId(bill.getId());
                                    BigDecimal bigDecimalprice3 = new BigDecimal(price3[0]);
                                    billPrice3.setPrice(bigDecimalprice3);
                                    billPrice3.setBillRankingStandard(Long.parseLong(rankend3[0]));
                                    billPrice3.setInMemberId(user.getId());
                                    billPrice3.setCreateTime(new Date());
                                    billPriceMapper.insert(billPrice3);

                                }
                            }
                        }

                    }
                }
                //不存在 直接录入
                else {
                    //订单主表
                    Bill bill1 = new Bill();
                    bill1.setWebsite(urls[i]);
                    bill1.setKeywords(keywords[i]);
                    bill1.setCreateUserId(user.getId());
                    bill1.setCreateTime(new Date());
                    bill1.setFirstRanking(51);
                    bill1.setNewRanking(51);
                    bill1.setWebAppId(123456);
                    bill1.setStandardDays(0);
                    bill1.setDayOptimization(1);
                    bill1.setAllOptimization(1);
                    bill1.setState(0);
                    Long billId1 = billMapper.insert(bill1);
                    //订单引擎表
                    BillSearchSupport billSearchSupport1 = new BillSearchSupport();
                    billSearchSupport1.setBillId(bill1.getId());
                    billSearchSupport1.setSearchSupport(search[0]);
                    billSearchSupportMapper.insert(billSearchSupport1);
                    //订单单价表
                    BillPrice billPriceA = new BillPrice();
                    billPriceA.setBillId(bill1.getId());
                    BigDecimal bigDecimalprice = new BigDecimal(price[0]);
                    billPriceA.setPrice(bigDecimalprice);
                    billPriceA.setBillRankingStandard(Long.parseLong(rankend[0]));
                    billPriceA.setInMemberId(user.getId());
                    billPriceA.setCreateTime(new Date());
                    billPriceMapper.insert(billPriceA);
                    if (price1[0] != "" && rankend1[0] != "") {
                        BillPrice billPriceA1 = new BillPrice();
                        billPriceA1.setBillId(bill1.getId());
                        BigDecimal bigDecimalprice1 = new BigDecimal(price1[0]);
                        billPriceA1.setPrice(bigDecimalprice1);
                        billPriceA1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                        billPriceA1.setInMemberId(user.getId());
                        billPriceA1.setCreateTime(new Date());
                        billPriceMapper.insert(billPriceA1);
                        if (price2[0] != "" && rankend2[0] != "") {
                            BillPrice billPriceA2 = new BillPrice();
                            billPriceA2.setBillId(bill1.getId());
                            BigDecimal bigDecimalprice2 = new BigDecimal(price2[0]);
                            billPriceA2.setPrice(bigDecimalprice2);
                            billPriceA2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                            billPriceA2.setInMemberId(user.getId());
                            billPriceA2.setCreateTime(new Date());
                            billPriceMapper.insert(billPriceA2);
                            if (price3[0] != "" && rankend3[0] != "") {
                                BillPrice billPriceA3 = new BillPrice();
                                billPriceA3.setBillId(bill1.getId());
                                BigDecimal bigDecimalprice3 = new BigDecimal(price3[0]);
                                billPriceA3.setPrice(bigDecimalprice3);
                                billPriceA3.setBillRankingStandard(Long.parseLong(rankend3[0]));
                                billPriceA3.setInMemberId(user.getId());
                                billPriceA3.setCreateTime(new Date());
                                billPriceMapper.insert(billPriceA3);

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
     * @return
     */
    @Override
    public String savaDiffrentBill(Map<String, String[]>  params,User user) {


        String[] dfurlsArr=params.get("dfurl");
        String[] dfkeywordsArr=params.get("dfkeyword");
        String[] dfpricesArr=params.get("dfprice");
        String[] dfurls=dfurlsArr[0].split("\n");
        String[] dfkeywords=dfkeywordsArr[0].split("\n");
        String[] dfprices=dfpricesArr[0].split("\n");
        String[] dfsearch=params.get("dfsearch");
        String[] dfrankend=params.get("dfrankend");
        String errorDetails="";
        if(dfurls.length==dfkeywords.length&&dfkeywords.length==dfprices.length)
        {
            for(int i=0;i<dfurls.length;i++)
            {
                //先查询是否订单已经存在
                Map<String,Object> params1=new HashMap();
                params1.put("website",dfurls[i]);
                params1.put("keywords",dfkeywords[i]);
                List<Bill> billList=billMapper.selectAllSelective(params1);
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
                      bill.setState(0);
                      Long billId=billMapper.insert(bill);
                      //订单引擎表
                      BillSearchSupport billSearchSupport=new BillSearchSupport();
                      billSearchSupport.setBillId(bill.getId());
                      billSearchSupport.setSearchSupport(dfsearch[0]);
                      billSearchSupportMapper.insert(billSearchSupport);
                      //订单单价表
                      BillPrice billPrice=new BillPrice();
                      billPrice.setBillId(bill.getId());
                      BigDecimal bd=new BigDecimal(dfprices[i]);
                      billPrice.setPrice(bd);
                      billPrice.setBillRankingStandard(Long.parseLong(dfrankend[0]));
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
