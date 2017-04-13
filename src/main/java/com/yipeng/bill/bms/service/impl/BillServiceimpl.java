package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.Define;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.*;
import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
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
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RemoteService remoteService;
    /**
     * 相同价导入
     * @param bill
     * @return
     */
    @Override
    public String saveSameBill(Map<String, String[]>  params,LoginUser user ) {

        int state=0;
        if(user.hasRole("DISTRIBUTOR"))
        {
            state=1;
        }
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
        String[] customerId=params.get("customerId");
        String errorDetails="";
        if(urls.length==keywords.length) {
            for (int i = 0; i < urls.length; i++) {
                //先查询是否订单已经存在
                Map<String, Object> params1 = new HashMap();
                params1.put("website", urls[i]);
                params1.put("keywords", keywords[i]);
                List<Bill> billList = billMapper.selectAllSelective(params1);
                Map<String,Object> map=new HashMap<>();
                if(search[0].equals("360")||search[0].equals("搜狗"))
                {
                    String urlss="http://"+urls[i];
                    map.put("url",urlss);
                }
                else
                {
                    map.put("url",urls[i]);
                }

                map.put("keywords",keywords[i]);
                map.put("CreateUserId",user.getId());
                map.put("search",search[0]);
                map.put("price",price[0]);
                map.put("rankend",rankend[0]);
                map.put("customerId",customerId[0]);
                map.put("price1",price1[0]);
                map.put("rankend1",rankend1[0]);
                map.put("price2",price2[0]);
                map.put("rankend2",rankend2[0]);
                map.put("price3",price3[0]);
                map.put("rankend3",rankend3[0]);
                map.put("state",state);
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
                       this.insertPirce(map);
                    }
                }
                //不存在 直接录入
                else {
                    this.insertPirce(map);
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
    public String savaDiffrentBill(Map<String, String[]>  params,LoginUser user) {

        int state=0;
       if(user.hasRole("DISTRIBUTOR"))
       {
           state=1;
       }
        String[] dfurlsArr=params.get("dfurl");
        String[] dfkeywordsArr=params.get("dfkeyword");
        String[] dfpricesArr=params.get("dfprice");
        String[] dfurls=dfurlsArr[0].split("\n");
        String[] dfkeywords=dfkeywordsArr[0].split("\n");
        String[] dfprices=dfpricesArr[0].split("\n");
        String[] dfsearch=params.get("dfsearch");
        String[] dfrankend=params.get("dfrankend");
        String[] customerId=params.get("customerIds");
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
              if(billList!=null&&billList.size()>0)
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
                      if(dfsearch[0].equals("360")||dfsearch[0].equals("搜狗"))
                      {
                          String urls="http://"+dfurls[i];
                          bill.setWebsite(urls);
                      }
                      else
                      {
                          bill.setWebsite(dfurls[i]);
                      }

                      bill.setKeywords(dfkeywords[i]);
                      bill.setCreateUserId(user.getId());
                      bill.setUpdateUserId(user.getId());
                      bill.setCreateTime(new Date());
                      //默认排名为200（无排名）
                      bill.setFirstRanking(200);
                      bill.setNewRanking(200);
                      bill.setStandardDays(0);
                      bill.setDayOptimization(1);
                      bill.setAllOptimization(1);
                      bill.setState(state);
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
                      billPrice.setOutMemberId(new Long(customerId[0]));
                      billPrice.setCreateTime(new Date());
                      billPriceMapper.insert(billPrice);

                  }
              }
              else
              {


                  Bill bill=new Bill();
                  if(dfsearch[0].equals("360")||dfsearch[0].equals("搜狗"))
                  {
                      String urls="http://"+dfurls[i];
                      bill.setWebsite(urls);
                  }
                  else
                  {
                      bill.setWebsite(dfurls[i]);
                  }
                  bill.setKeywords(dfkeywords[i]);
                  bill.setCreateUserId(user.getId());
                  bill.setUpdateUserId(user.getId());
                  bill.setCreateTime(new Date());
                  //默认排名为200（无排名）
                  bill.setFirstRanking(200);
                  bill.setNewRanking(200);
                  bill.setStandardDays(0);
                  bill.setDayOptimization(1);
                  bill.setAllOptimization(1);
                  bill.setState(state);
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
                  billPrice.setOutMemberId(new Long(customerId[0]));
                  billPrice.setCreateTime(new Date());
                  billPriceMapper.insert(billPrice);
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
     * 实现通过参数来查询集合(待审核页面)
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> pendingAuditList(Map<String, Object> params, LoginUser user) {

        //视图模型
        List<BillDetails> billDetailsList = new ArrayList<BillDetails>();
        int limit=Integer.parseInt(params.get("limit").toString()) ;
        int offset=Integer.parseInt(params.get("offset").toString()) ;
        int i=offset;
        if(user.hasRole("SUPER_ADMIN"))
        {

            params.put("state",1);
           //查询所有订单状态为1的
             List<Bill> billList=billMapper.selectAll(params);
            for (Bill bill: billList
                 ) {
                i++;
                BillDetails billDetails=new BillDetails();
                billDetails.setdisplayId(i);
                billDetails.setId(bill.getId());
                User user1= userMapper.selectByPrimaryKey(bill.getUpdateUserId());
                billDetails.setUserName(user1.getUserName());
                billDetails.setKeywords(bill.getKeywords());
                billDetails.setWebsite(bill.getWebsite());
                //查出搜索引擎
                BillSearchSupport billSearchSupport=  billSearchSupportMapper.selectByBillId(bill.getId());
                billDetails.setSearchName(billSearchSupport.getSearchSupport());
                billDetails.setCreateTime(DateUtils.formatDate(bill.getCreateTime()));
                billDetails.setUpdateUserId(bill.getUpdateUserId());
                billDetails.setFirstRanking(bill.getFirstRanking());
                billDetails.setNewRanking(bill.getNewRanking());
                billDetails.setDayOptimization(bill.getDayOptimization());
                billDetails.setAllOptimization(billDetails.getAllOptimization());
                //当天消费(缺)
                //本月消费（缺）
                billDetails.setStandardDays(bill.getStandardDays());
                billDetails.setState(bill.getState());
                billDetailsList.add(billDetails);
            }
            Long total=billMapper.selectAllCount(1);
            Map<String,Object> map=new HashMap<>();
            map.put("total",total);
            map.put("rows",billDetailsList);
            return  map;

        }
        else
        {

            params.put("userId",user.getId());
            params.put("state",0);
            //查询所有订单状态为0的
            List<Bill> billList=billMapper.selectAgentBill(params);
            for (Bill bill: billList
                 ) {
                i++;
                BillDetails billDetails=new BillDetails();
                billDetails.setdisplayId(i);
                billDetails.setId(bill.getId());
                User user1= userMapper.selectByPrimaryKey(bill.getUpdateUserId());
                billDetails.setUserName(user1.getUserName());
                billDetails.setKeywords(bill.getKeywords());
                billDetails.setWebsite(bill.getWebsite());
                //查出搜索引擎
                BillSearchSupport billSearchSupport=  billSearchSupportMapper.selectByBillId(bill.getId());
                billDetails.setSearchName(billSearchSupport.getSearchSupport());
                billDetails.setCreateTime(DateUtils.formatDate(bill.getCreateTime()));
                billDetails.setUpdateUserId(bill.getUpdateUserId());
                billDetails.setFirstRanking(bill.getFirstRanking());
                billDetails.setNewRanking(bill.getNewRanking());
                billDetails.setDayOptimization(bill.getDayOptimization());
                billDetails.setAllOptimization(billDetails.getAllOptimization());
                //当天消费(缺)
                //本月消费（缺）
                billDetails.setStandardDays(bill.getStandardDays());
                billDetails.setState(bill.getState());
                billDetailsList.add(billDetails);
            }
            Long total=billMapper.selectAgentBillCount(params);
            Map<String,Object> map=new HashMap<>();
            map.put("total",total);
            map.put("rows",billDetailsList);
            return  map;

        }




    }

    /**
     * 调整价格
     * @param params
     * @param user
     * @return
     */
    @Override
    public int updateBillPrice(Map<String, String[]>  params,User user) {



        return 0;
    }

    /**
     * 渠道商审核订单，录入单价表数据
     * @param params 价格
     * @param user 当前登录对象
     * @return
     */
    @Override
    public int distributorPrice(Map<String, String[]> params, User user) {

        String[] bill=params.get("rankend1");
        String[] selectContent=params.get("selectContent[0][userName]");
        String[] checkboxLength=params.get("checkboxLength");
        String[] price1=params.get("price1");
        String[] price2=params.get("price2");
        String[] price3=params.get("price3");

        int  length=Integer.parseInt(checkboxLength[0]);
        for(int i=0;i<length;i++)
        {
            String[] id=params.get("selectContent["+i+"][id]");
            String[] price=params.get("price");
            String[] rankend=params.get("rankend");
            Long billId=Long.parseLong(id[0]);
            //判断渠道商订单价格是否已经存在(有BUG 如果管理员录入价格 bool会变成true)
            List<BillPrice> billPriceList= billPriceMapper.selectByBillId(billId);
            Boolean bool=true;
            //如果有了 就修改
             if(billPriceList.size()>0)
            {
                for (BillPrice billPrice1:billPriceList
                     ) {
                    if(billPrice1.getInMemberId().longValue()==user.getId().longValue())
                    {
                         bool=false;
                    }
                }
            }
            if(bool)
            {
                BillPrice billPrice=new BillPrice();
                billPrice.setBillId(billId);
                BigDecimal ret = null;
                ret= new BigDecimal((String)price[0]);
                billPrice.setPrice(ret);
                billPrice.setBillRankingStandard(Long.parseLong(rankend[0]));
                billPrice.setInMemberId(user.getId()); ;
                String[] updateUserId=params.get("selectContent["+i+"][updateUserId]");
                billPrice.setOutMemberId(Long.parseLong(updateUserId[0]));
                billPrice.setCreateTime(new Date());
                billPriceMapper.insert(billPrice);
                 Bill bill1=new Bill();
                 bill1.setId(billId);
                 bill1.setUpdateUserId(user.getId());
                 bill1.setState(1);
                 billMapper.updateByPrimaryKeySelective(bill1);
                if (!"NaN".equals(price1[0])) {
                    String[] id1=params.get("selectContent["+i+"][id]");
                    String[] rankend1=params.get("rankend1");
                    Long billId1=Long.parseLong(id1[0]);
                    BillPrice billPrice1=new BillPrice();
                    billPrice1.setBillId(billId1);
                    BigDecimal ret1 = null;
                    ret1= new BigDecimal((String)price1[0]);
                    billPrice1.setPrice(ret1);
                    billPrice1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                    billPrice1.setInMemberId(user.getId());
                    String[] updateUserId1=params.get("selectContent["+i+"][updateUserId]");
                    billPrice1.setOutMemberId(Long.parseLong(updateUserId1[0]));
                    billPrice1.setCreateTime(new Date());
                    billPriceMapper.insert(billPrice1);
                    if (!"NaN".equals(price2[0])) {
                        String[] id2=params.get("selectContent["+i+"][id]");
                        String[] rankend2=params.get("rankend2");
                        Long billId2=Long.parseLong(id2[0]);
                        BillPrice billPrice2=new BillPrice();
                        billPrice2.setBillId(billId2);
                        BigDecimal ret2 = null;
                        ret2= new BigDecimal((String)price2[0]);
                        billPrice2.setPrice(ret2);
                        billPrice2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                        billPrice2.setInMemberId(user.getId());
                        String[] updateUserId2=params.get("selectContent["+i+"][updateUserId]");
                        billPrice2.setOutMemberId(Long.parseLong(updateUserId2[0]));
                        billPrice2.setCreateTime(new Date());
                        billPriceMapper.insert(billPrice2);
                        if (!"NaN".equals(price3[0])) {
                            String[] id3=params.get("selectContent["+i+"][id]");
                            String[] rankend3=params.get("rankend3");
                            Long billId3=Long.parseLong(id3[0]);
                            BillPrice billPrice3=new BillPrice();
                            billPrice3.setBillId(billId3);
                            BigDecimal ret3 = null;
                            ret3= new BigDecimal((String)price3[0]);
                            billPrice3.setPrice(ret3);
                            billPrice3.setBillRankingStandard(Long.parseLong(rankend3[0]));
                            billPrice3.setInMemberId(user.getId());
                            String[] updateUserId3=params.get("selectContent["+i+"][updateUserId]");
                            billPrice3.setOutMemberId(Long.parseLong(updateUserId3[0]));
                            billPrice3.setCreateTime(new Date());
                            billPriceMapper.insert(billPrice3);
                        }
                    }
                }
            }


        }
        return 0;
    }

    /**
     * 优化方录入价格
     * @param params
     * @param user
     * @return
     */
    @Override
    public int adminPrice(Map<String, String[]> params, User user) {

        String[] bill = params.get("rankend1");
        String[] selectContent = params.get("selectContent[0][userName]");
        String[] checkboxLength = params.get("checkboxLength");
        String[] price1 = params.get("price1");
        String[] price2 = params.get("price2");
        String[] price3 = params.get("price3");
        String[] caozuoyuan = params.get("caozuoyuan");
        Long caozuoyuanId = Long.parseLong(caozuoyuan[0]);
        Md5_UrlEncode md5_urlEncode=new Md5_UrlEncode();
        int  length=Integer.parseInt(checkboxLength[0]);
        for(int i=0;i<length;i++)
        {
            String[] id=params.get("selectContent["+i+"][id]");
            String[] price=params.get("price");
            String[] rankend=params.get("rankend");
            Long billId=Long.parseLong(id[0]);
            //判断渠道商订单价格是否已经存在(有BUG 如果管理员录入价格 bool会变成true)
            List<BillPrice> billPriceList= billPriceMapper.selectByBillId(billId);
            Boolean bool=true;
            //如果有了 就修改
            if(billPriceList.size()>0)
            {
                for (BillPrice billPrice1:billPriceList
                        ) {
                    if(billPrice1.getInMemberId().longValue()==user.getId().longValue())
                    {
                        bool=false;
                    }
                }
            }
            if(bool)
            {
                CustomerRankingParam customerRankingParam=new CustomerRankingParam();
                Bill billA=billMapper.selectByPrimaryKey(billId);
                BillSearchSupport billSearchSupport=billSearchSupportMapper.selectByBillId(billId);
                int ApiId;
                String wAction = "AddSearchTask";
                String[] qkeyword = {billA.getKeywords()};

                String[] qurl = {billA.getWebsite()};
                int[] timeSet = { 12 };
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("keyword", qkeyword);
                jsonObj.put("url", qurl);
                jsonObj.put("time", System.currentTimeMillis());
                jsonObj.put("timeSet", timeSet);
                jsonObj.put("userId", Define.userId);
                jsonObj.put("businessType", 2006);
                 if (billSearchSupport.getSearchSupport().equals("百度"))
                 {
                     jsonObj.put("searchType", 1010);
                 }
                else if (billSearchSupport.getSearchSupport().equals("360"))
                {
                    jsonObj.put("searchType", 1015);
                }
                 else if (billSearchSupport.getSearchSupport().equals("搜狗"))
                 {
                     jsonObj.put("searchType", 1030);
                 }
                 else if (billSearchSupport.getSearchSupport().equals("手机百度"))
                 {
                     jsonObj.put("searchType", 7010);
                 }
                 else if (billSearchSupport.getSearchSupport().equals("手机360"))
                 {
                     jsonObj.put("searchType", 7015);
                 }
                 else if (billSearchSupport.getSearchSupport().equals("手机搜狗"))
                 {
                     jsonObj.put("searchType", 7030);
                 }
                 else
                 {
                     jsonObj.put("searchType", 7070);
                 }
                jsonObj.put("searchOnce", false);
                String wParam = jsonObj.toString();
                String wSign = null;
                try {
                    wSign = md5_urlEncode.EncoderByMd5(wAction + Define.token + jsonObj.toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                customerRankingParam.setwAction("AddSearchTask");
                customerRankingParam.setwParam(wParam);
                customerRankingParam.setwSign(wSign);
                try {
                    //查询taskID
                    CustomerRankingResult customerRankingResult= remoteService.getCustomerRanking(customerRankingParam);
                    if(customerRankingResult!=null)
                    {
                        if(customerRankingResult.getMessage().equals("success."))
                        {
                           JSONArray value=customerRankingResult.getValue();
                           JSONArray valueJSONArray= value.getJSONArray(0);
                           ApiId=Integer.parseInt(valueJSONArray.get(0).toString());
                           //录入价格
                            BillPrice billPrice=new BillPrice();
                            billPrice.setBillId(billId);
                            BigDecimal ret = null;
                            ret= new BigDecimal((String)price[0]);
                            billPrice.setPrice(ret);
                            billPrice.setBillRankingStandard(Long.parseLong(rankend[0]));
                            billPrice.setInMemberId(user.getId()); ;
                            String[] updateUserId=params.get("selectContent["+i+"][updateUserId]");
                            billPrice.setOutMemberId(Long.parseLong(updateUserId[0]));
                            billPrice.setCreateTime(new Date());
                            billPriceMapper.insert(billPrice);
                            Bill bill1=new Bill();
                            bill1.setId(billId);
                            bill1.setUpdateUserId(user.getId());
                            bill1.setWebAppId(ApiId);
                            bill1.setBillAscription(caozuoyuanId);
                            bill1.setState(2);
                            billMapper.updateByPrimaryKeySelective(bill1);
                            if (!"NaN".equals(price1[0])) {
                                String[] id1=params.get("selectContent["+i+"][id]");
                                String[] rankend1=params.get("rankend1");
                                Long billId1=Long.parseLong(id1[0]);
                                BillPrice billPrice1=new BillPrice();
                                billPrice1.setBillId(billId1);
                                BigDecimal ret1 = null;
                                ret1= new BigDecimal((String)price1[0]);
                                billPrice1.setPrice(ret1);
                                billPrice1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                                billPrice1.setInMemberId(user.getId());
                                String[] updateUserId1=params.get("selectContent["+i+"][updateUserId]");
                                billPrice1.setOutMemberId(Long.parseLong(updateUserId1[0]));
                                billPrice1.setCreateTime(new Date());
                                billPriceMapper.insert(billPrice1);
                                if (!"NaN".equals(price2[0])) {
                                    String[] id2=params.get("selectContent["+i+"][id]");
                                    String[] rankend2=params.get("rankend2");
                                    Long billId2=Long.parseLong(id2[0]);
                                    BillPrice billPrice2=new BillPrice();
                                    billPrice2.setBillId(billId2);
                                    BigDecimal ret2 = null;
                                    ret2= new BigDecimal((String)price2[0]);
                                    billPrice2.setPrice(ret2);
                                    billPrice2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                                    billPrice2.setInMemberId(user.getId());
                                    String[] updateUserId2=params.get("selectContent["+i+"][updateUserId]");
                                    billPrice2.setOutMemberId(Long.parseLong(updateUserId2[0]));
                                    billPrice2.setCreateTime(new Date());
                                    billPriceMapper.insert(billPrice2);
                                    if (!"NaN".equals(price3[0])) {
                                        String[] id3=params.get("selectContent["+i+"][id]");
                                        String[] rankend3=params.get("rankend3");
                                        Long billId3=Long.parseLong(id3[0]);
                                        BillPrice billPrice3=new BillPrice();
                                        billPrice3.setBillId(billId3);
                                        BigDecimal ret3 = null;
                                        ret3= new BigDecimal((String)price3[0]);
                                        billPrice3.setPrice(ret3);
                                        billPrice3.setBillRankingStandard(Long.parseLong(rankend3[0]));
                                        billPrice3.setInMemberId(user.getId());
                                        String[] updateUserId3=params.get("selectContent["+i+"][updateUserId]");
                                        billPrice3.setOutMemberId(Long.parseLong(updateUserId3[0]));
                                        billPrice3.setCreateTime(new Date());
                                        billPriceMapper.insert(billPrice3);
                                    }
                                }
                            }
                        }
                        else
                        {
                            continue;
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


            }


        }
        return 0;
    }


    /**
     * 订单列表
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getBillDetails(Map<String, Object> params, LoginUser user) {
        //判断查询条件
        //视图模型
        List<BillDetails> billDetailsList = new ArrayList<BillDetails>();

        int way=Integer.parseInt(params.get("way").toString()) ;
        int limit=Integer.parseInt(params.get("limit").toString()) ;
        int offset=Integer.parseInt(params.get("offset").toString()) ;
        int i=offset;
           //上游
           if(way==1)
           {
                //先获取对应的订单
               if(params.get("searchUserNameId")!=null)
               {
                   User user1=userMapper.selectByPrimaryKey(Long.parseLong(params.get("searchUserNameId").toString()));
                   params.put("userId",user1.getId());
               }
               else
               {
                   params.put("userId",user.getId());
               }
               List<Bill> billList=billMapper.selectByOutMemberId(params);

               Long total=billMapper.getBillListCount(params);
               for (Bill bill: billList
                       ) {
                   i++;
                   //调用方法  加入集合
                   BillDetails billDetails=this.insertBillDetails(bill, user,way);
                   billDetails.setdisplayId(i);
                   billDetailsList.add(billDetails);
               }
               Map<String,Object> map=new HashMap<>();
               map.put("total",total);
               map.put("rows",billDetailsList);
               return  map;
           }
           //下游
           else if(way==2)
           {
                 if(user.hasRole("SUPER_ADMIN")||user.hasRole("AGENT"))
                 {
                     params.put("userId",user.getId());
                     //先获取对应的订单
                     List<Bill> billList=billMapper.selectByInMemberId(params);
                     Long total=billMapper.getBillListCount(params);
                     for (Bill bill: billList
                             ) {
                         i++;
                         //调用方法  加入集合
                         BillDetails billDetails=this.insertBillDetails(bill, user,way);
                         billDetails.setdisplayId(i);
                         billDetailsList.add(billDetails);
                     }
                     Map<String,Object> map=new HashMap<>();
                     map.put("total",total);
                     map.put("rows",billDetailsList);
                     return  map;
                 }
                 else if(user.hasRole("DISTRIBUTOR"))
                 {
                     //先获取对应的订单
                     params.put("userId",user.getId());
                     List<Bill> billList=billMapper.selectByInMemberId(params);
                     Long total=billMapper.getBillListCount(params);
                     for (Bill bill: billList
                             ) {

                         //调用方法  加入集合
                         BillPrice billPrice=new BillPrice();
                         billPrice.setBillId(bill.getId());
                         billPrice.setInMemberId(user.getId());
                         Long OutMemberId=  billPriceMapper.selectByBillPriceOutMemberId(billPrice);
                         UserRole userRole=userRoleMapper.selectByUserId(OutMemberId);
                         //查询渠道商的付款方是不是代理商
                         if(userRole.getRoleId()==5)
                         {
                             i++;
                             BillDetails billDetails=this.insertBillDetails(bill, user,way);
                             billDetails.setdisplayId(i);
                             billDetailsList.add(billDetails);
                         }


                     }
                     Map<String,Object> map=new HashMap<>();
                     map.put("total",total);
                     map.put("rows",billDetailsList);
                     return  map;
                 }
                 else//操作员
                 {
                     //先获取对应的订单
                     params.put("userId",user.getCreateUserId());
                     params.put("billAscription",user.getId());
                     List<Bill> billList=billMapper.selectByInMemberId(params);
                     Long total=billMapper.getBillListCount(params);
                     for (Bill bill: billList
                             ) {
                         i++;
                         //调用方法  加入集合
                         BillDetails billDetails=this.insertBillDetails(bill, user,way);
                         billDetails.setdisplayId(i);
                         billDetailsList.add(billDetails);
                     }
                     Map<String,Object> map=new HashMap<>();
                     map.put("total",total);
                     map.put("rows",billDetailsList);
                     return  map;
                 }

           }
           else
           {

               //渠道商直属客户，先获取对应的订单
               Role role=roleMapper.selectByRoleCode("CUSTOMER");
               params.put("roleId",role.getId());
               params.put("userId",user.getId());
               List<Bill> billList=billMapper.selectByCustomerId(params);Long total=billMapper.selectByCustomerIdCount(params);
               for (Bill bill: billList
                       ) {
                       i++;
                       BillDetails billDetails=this.insertBillDetails(bill, user,way);
                       billDetails.setdisplayId(i);
                       billDetailsList.add(billDetails);



               }
               Map<String,Object> map=new HashMap<>();
               map.put("total",total);
               map.put("rows",billDetailsList);
               return  map;
           }


    }

    /**
     * 插入视图模型数据 封装成方法
     * @param bill
     * @return
     */
    public  BillDetails insertBillDetails(Bill bill,LoginUser user,int way)
    {
        BillPrice billPrice=new BillPrice();
        List<BillPrice> billPriceList=new ArrayList<>();
        //上游
        if(way==1)
        {
            billPrice.setBillId(bill.getId());
            billPrice.setOutMemberId(user.getId());
            //查出对应单价
            billPriceList=billPriceMapper.selectByBillPrice(billPrice);
        }
        //下游
        else
        {
            if(user.hasRole("COMMISSIONER"))
            {
                billPrice.setBillId(bill.getId());
                billPrice.setInMemberId(user.getCreateUserId());
                //查出对应单价
                billPriceList=billPriceMapper.selectByBillPrice(billPrice);
            }
            else
            {
                billPrice.setBillId(bill.getId());
                billPrice.setInMemberId(user.getId());
                //查出对应单价
                billPriceList=billPriceMapper.selectByBillPrice(billPrice);
            }

        }




        //查出搜索引擎
        BillSearchSupport billSearchSupport=  billSearchSupportMapper.selectByBillId(bill.getId());
        BillDetails billDetails=new BillDetails();
        billDetails.setId(bill.getId());
        if(way==1)
        {
            if(user.hasRole("CUSTOMER"))
            {

                BillPrice billPrice1=new BillPrice();
                billPrice1.setBillId(bill.getId());
                billPrice1.setOutMemberId(user.getId());
                List<BillPrice> billPriceList1= billPriceMapper.selectByBillPrice(billPrice1);
                User user2= userMapper.selectByPrimaryKey(billPriceList1.get(0).getOutMemberId());
                billDetails.setUserName(user2.getUserName());
            }
            else
            {

                BillPrice billPrice1=new BillPrice();
                billPrice1.setBillId(bill.getId());
                billPrice1.setInMemberId(user.getId());
                List<BillPrice> billPriceList1= billPriceMapper.selectByBillPrice(billPrice1);
                User user2= userMapper.selectByPrimaryKey(billPriceList1.get(0).getOutMemberId());
                billDetails.setUserName(user2.getUserName());
            }

        }
        else
        {
            User user1= userMapper.selectByPrimaryKey(billPriceList.get(0).getOutMemberId());
            billDetails.setUserName(user1.getUserName());
        }


        billDetails.setKeywords(bill.getKeywords());
        billDetails.setWebsite(bill.getWebsite());
        billDetails.setSearchName(billSearchSupport.getSearchSupport());
        billDetails.setCreateTime(DateUtils.formatDate(bill.getCreateTime()));
        billDetails.setFirstRanking(bill.getFirstRanking());
        billDetails.setNewRanking(bill.getNewRanking());
        billDetails.setBillPriceList(billPriceList);
        billDetails.setDayOptimization(bill.getDayOptimization());
        billDetails.setAllOptimization(bill.getAllOptimization());

        //判断当前是否达标  当天消费
        BillPrice billPrice1=new BillPrice();
        billPrice1.setBillId(bill.getId());

        if(way==1)
        {
            billPrice1.setOutMemberId(user.getId());
        }
        else
        {
            if(user.hasRole("COMMISSIONER"))
            {
                billPrice1.setInMemberId(user.getCreateUserId());
            }
            else
            {
                billPrice1.setInMemberId(user.getId());
            }

        }

       List<BillPrice>  billPrice2= billPriceMapper.selectByBillPrice(billPrice1);
       if(billPrice2.size()>0)
       {
           for (BillPrice item:billPrice2
                ) {
               if(item.getBillRankingStandard()>=bill.getNewRanking())
               {
                   billDetails.setDayConsumption(item.getPrice());

                   break;
               }
           }
       }
        Calendar now =Calendar.getInstance();
        Map<String,Object> map=new HashMap<>();
        map.put("year",now.get(Calendar.YEAR));
        map.put("month",now.get(Calendar.MONTH)+1);
        map.put("billId",bill.getId());
        if(way==1)
        {
            map.put("userId",user.getCreateUserId());
        }
        else
        {
            if(user.hasRole("COMMISSIONER"))
            {
                map.put("userId",user.getCreateUserId());
            }
            else
            {
                map.put("userId",user.getId());
            }

        }

        Double sum=billCostMapper.selectByPriceSum(map);
        if(sum!=null)
        {
            billDetails.setMonthConsumption(sum);
        }

       //有问题 不需要查询 订单表里面有达标天数 最后一次查询排名 增加达标天数
       Map<String,Object> map1=new HashMap<>();
       map1.put("billId",bill.getId());
       int count=billCostMapper.selectByPriceCount(map1);

        billDetails.setStandardDays(count);
        billDetails.setState(bill.getState());
        return billDetails;
    }
    /**
     * 优化调整
     * @param params
     * @param user
     * @return
     */
    @Override
    public int OptimizationUpdate(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength=params.get("length");
        int  length=Integer.parseInt(checkboxLength[0]);
        String[] num=params.get("num");
        int  nums=Integer.parseInt(num[0]);
        for(int i=0;i<length;i++)
        {
            String[] id=params.get("selectContent["+i+"][id]");
            Long  billId=Long.parseLong(id[0]);
            Bill bill=billMapper.selectByPrimaryKey(billId);
            int AllOptimization=bill.getAllOptimization();
            int sum=nums+AllOptimization;
            Bill  bill1 =new Bill();
            bill1.setId(billId);
            bill1.setDayOptimization(nums);
            bill1.setAllOptimization(sum);
            billMapper.updateByPrimaryKeySelective(bill1);

        }
        return 0;
    }

    /**
     * 优化停止
     * @param params
     * @param user
     * @return
     */
    @Override
    public int optimizationStop(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength=params.get("length");
        int  length=Integer.parseInt(checkboxLength[0]);
        for(int i=0;i<length;i++)
        {
            String[] id=params.get("selectContent["+i+"][id]");
            Long  billId=Long.parseLong(id[0]);
            Bill  bill =new Bill();
            bill.setId(billId);
            bill.setState(3);
            billMapper.updateByPrimaryKeySelective(bill);

        }
        return 0;
    }

    @Override
    public int optimizationStart(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength=params.get("length");
        int  length=Integer.parseInt(checkboxLength[0]);
        for(int i=0;i<length;i++)
        {
            String[] id=params.get("selectContent["+i+"][id]");
            Long  billId=Long.parseLong(id[0]);
            Bill  bill =new Bill();
            bill.setId(billId);
            bill.setState(2);
            billMapper.updateByPrimaryKeySelective(bill);

        }
        return 0;
    }

    @Override
    public Map<String, Object> getPriceDetails(int limit,int offset,String billId, LoginUser user,String way) {

       Map<String,Object> params=new HashMap<>();
        List<BillCostDetails> billCostDetailsList=new ArrayList<>();

        if(way.equals("1"))//上游
        {
            Map<String,Object> map=new HashMap<>();
            map.put("limit",limit);
            map.put("offset",offset);
            map.put("billId",billId);
            map.put("userId",user.getCreateUserId());
            map.put("offset",offset);
            List<BillCost> billCostList=billCostMapper.getPriceByMap(map);
            Long total=billCostMapper.getPriceByMapCount(map);
            for (BillCost item :billCostList
                    ) {
                BillCostDetails billCostDetails=new BillCostDetails();
                billCostDetails.setId(item.getId());
                billCostDetails.setCostAmount(item.getCostAmount());
                billCostDetails.setRanking(item.getRanking());
                billCostDetails.setCostDate(DateUtils.formatDate(item.getCostDate()));
                billCostDetailsList.add(billCostDetails);
            }
            params.put("rows", billCostDetailsList);
            params.put("total", total);
            return params;
        }

        else
        {
            Map<String,Object> map=new HashMap<>();
            map.put("billId",billId);
            map.put("limit",limit);
            map.put("offset",offset);
            if(user.hasRole("SUPER_ADMIN")||user.hasRole("DISTRIBUTOR")||user.hasRole("AGENT"))
            {
                map.put("userId",user.getId());
                List<BillCost> billCostList=billCostMapper.getPriceByMap(map);
                Long total=billCostMapper.getPriceByMapCount(map);
                for (BillCost item :billCostList
                        ) {
                    BillCostDetails billCostDetails=new BillCostDetails();
                    billCostDetails.setId(item.getId());
                    billCostDetails.setCostAmount(item.getCostAmount());
                    billCostDetails.setRanking(item.getRanking());
                    billCostDetails.setCostDate(DateUtils.formatDate(item.getCostDate()));
                    billCostDetailsList.add(billCostDetails);
                }
                params.put("rows", billCostDetailsList);
                params.put("total", total);
                return params;
            }

            else
            {
                map.put("userId",user.getCreateUserId());
                List<BillCost> billCostList=billCostMapper.getPriceByMap(map);
                Long total=billCostMapper.getPriceByMapCount(map);
                for (BillCost item :billCostList
                        ) {
                    BillCostDetails billCostDetails=new BillCostDetails();
                    billCostDetails.setId(item.getId());
                    billCostDetails.setCostAmount(item.getCostAmount());
                    billCostDetails.setRanking(item.getRanking());
                    billCostDetails.setCostDate(DateUtils.formatDate(item.getCostDate()));
                    billCostDetailsList.add(billCostDetails);
                }
                params.put("rows", billCostDetailsList);
                params.put("total", total);
                return params;
            }
        }



    }

    /**
     * 订单录入
     * @param params
     */
    public  void  insertPirce(Map<String,Object> params)
    {
        Long customerId =Long.valueOf( params.get("customerId").toString());
        Long CreateUserId =Long.valueOf( params.get("CreateUserId").toString());
        Bill bill = new Bill();
        bill.setWebsite(params.get("url").toString());
        bill.setKeywords(params.get("keywords").toString());
        bill.setCreateUserId((Long)params.get("CreateUserId"));
        bill.setUpdateUserId((Long)params.get("CreateUserId"));
        bill.setCreateTime(new Date());
        //默认排名为200（无排名）
        bill.setFirstRanking(200);
        bill.setNewRanking(200);
        bill.setStandardDays(0);
        bill.setDayOptimization(0);
        bill.setAllOptimization(0);
        bill.setState(Integer.parseInt(params.get("state").toString()));
        Long billId = billMapper.insert(bill);
        //订单引擎表
        BillSearchSupport billSearchSupport = new BillSearchSupport();
        billSearchSupport.setBillId(bill.getId());
        billSearchSupport.setSearchSupport(params.get("search").toString());
        billSearchSupportMapper.insert(billSearchSupport);
        //订单单价表
        BillPrice billPrice = new BillPrice();
        billPrice.setBillId(bill.getId());
        BigDecimal ret = null;
        ret= new BigDecimal((String)params.get("price") );
        billPrice.setPrice(ret);
        Long rankend =Long.valueOf( params.get("rankend").toString());
        billPrice.setBillRankingStandard((rankend));

        billPrice.setInMemberId(CreateUserId);

        billPrice.setOutMemberId(customerId);
        billPrice.setCreateTime(new Date());
        billPriceMapper.insert(billPrice);

        if (!"".equals(params.get("price1").toString()) && null != params.get("price1").toString()) {
            BillPrice billPrice1 = new BillPrice();
            billPrice1.setBillId(bill.getId());
            BigDecimal ret1 = null;
            ret1= new BigDecimal( (String)params.get("price1") );
            billPrice1.setPrice(ret1);
            Long rankend1 =Long.valueOf( params.get("rankend1").toString());
            billPrice1.setBillRankingStandard(rankend1);
            billPrice1.setInMemberId(CreateUserId);
            billPrice1.setOutMemberId(customerId);
            billPrice1.setCreateTime(new Date());
            billPriceMapper.insert(billPrice1);
            if (!"".equals(params.get("price2").toString()) && null != params.get("price2").toString()) {
                BillPrice billPrice2 = new BillPrice();
                billPrice2.setBillId(bill.getId());
                BigDecimal ret2 = null;
                ret2= new BigDecimal((String)params.get("price2") );
                billPrice2.setPrice(ret2);
                Long rankend2 =Long.valueOf( params.get("rankend").toString());
                billPrice2.setBillRankingStandard(rankend2);
                billPrice2.setInMemberId(CreateUserId);
                billPrice2.setOutMemberId(customerId);
                billPrice2.setCreateTime(new Date());
                billPriceMapper.insert(billPrice2);
                if (!"".equals(params.get("price3").toString()) && null != params.get("price3").toString()) {
                    BillPrice billPrice3 = new BillPrice();
                    billPrice3.setBillId(bill.getId());
                    BigDecimal ret3 = null;
                    ret3= new BigDecimal( (String)params.get("price3") );
                    billPrice3.setPrice(ret3);
                    Long rankend3 =Long.valueOf( params.get("rankend").toString());
                    billPrice3.setBillRankingStandard(rankend3);
                    billPrice3.setInMemberId(CreateUserId);
                    billPrice3.setOutMemberId(customerId);
                    billPrice3.setCreateTime(new Date());
                    billPriceMapper.insert(billPrice3);

                }
            }
        }
    }


    /**
     * 订单反馈
     * @param params
     * @param user
     * @return
     */
    @Override
    public Map<String,Object> billFeedback(String website) {
        Map<String,Object> param=new HashMap<>();
        param.put("website",website);
        List<Bill> billList=billMapper.selectAllSelective(param);
        if(billList.size()>0)
        {
            List<String> searchList=new ArrayList<>();
            List<String> keywordList=new ArrayList<>();
            for (Bill item:billList
                 ) {
                BillSearchSupport billSearchSupport=billSearchSupportMapper.selectByBillId(item.getId());
                if(searchList.size()>0)
                {
                    for (String str: searchList
                         ) {
                        if(!billSearchSupport.getSearchSupport().equals(str))
                        {
                            searchList.add(billSearchSupport.getSearchSupport());
                        }
                    }
                }
                else
                {
                    searchList.add(billSearchSupport.getSearchSupport());
                }
                keywordList.add(item.getKeywords());
            }

        ;

        }
        return null;
    }
}
