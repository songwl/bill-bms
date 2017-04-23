package com.yipeng.bill.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.Define;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.model.OpDefine;
import com.yipeng.bill.bms.model.Yby;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.beans.Encoder;
import java.io.*;
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

    Md5_UrlEncode md5_urlEncode=new Md5_UrlEncode();
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
                      if (billSearchSupport.getSearchSupport().equals(dfsearch[0]))
                      {
                          bool=false;
                          errorDetails+=+(i+1)+"网址："+dfurls[i]+"  关键词："+dfkeywords[i]+" ";
                           break;
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
                      bill.setDayOptimization(0);
                      bill.setAllOptimization(0);
                      bill.setState(state);
                      //正常单
                      bill.setBillType(1);
                      //优化状态（未优化）（调点击）
                      bill.setOpstate(0);
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
                  bill.setDayOptimization(0);
                  bill.setAllOptimization(0);
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
       //获取参数
        String[] bill = params.get("rankend1");
        String[] selectContent = params.get("selectContent[0][userName]");
        String[] checkboxLength = params.get("checkboxLength");
        String[] price1 = params.get("price1");
        String[] price2 = params.get("price2");
        String[] price3 = params.get("price3");
        String[] caozuoyuan = params.get("caozuoyuan");
        Long caozuoyuanId = Long.parseLong(caozuoyuan[0]);

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
                //组合参数
                int[] timeSet = { 9,12,15,19, };
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("keyword", qkeyword);
                jsonObj.put("url", qurl);
                jsonObj.put("time", System.currentTimeMillis());
                jsonObj.put("timeSet", timeSet);
                jsonObj.put("userId", Define.userId);
                jsonObj.put("businessType", 2006);
                //判断搜索引擎
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



                //调整点击录入订单
                int agid= OpDefine.agid;
                String action="importkw";
                String md5Key=OpDefine.md5Key;

                //取现在时间
                String dateString =new  SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                //填充实体类
                List<Yby> ybyList=new ArrayList<Yby>();
                //加密sign
                Yby yby=new Yby();
                yby.setKw(billA.getKeywords());
                yby.setUrl(billA.getWebsite());
                yby.setSe(1);
                yby.setMcpd(1);

                ybyList.add(yby);
                //组包
                String str="";
                str=str.concat(JSON.toJSONString(dateString));
                str=str.concat(JSON.toJSONString(action));
                str=str.concat(JSON.toJSONString(ybyList));
                str=str.concat(JSON.toJSONString(md5Key));
                str=str.concat(JSON.toJSONString(agid));
                //加密sign
               String sign= null;
                try {
                    sign = md5_urlEncode.EncoderByMd51(str);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //创建JSON
                JSONObject jaction=new JSONObject();
                jaction.put("action",action);
                JSONObject jagid=new JSONObject();
                jagid.put("agid",agid);
                JSONObject jstamp=new JSONObject();
                jstamp.put("stamp",dateString);
                JSONObject jargs=new JSONObject();
                jargs.put("args",JSON.toJSONString(ybyList));
                JSONObject jsign=new JSONObject();
                jsign.put("sign",sign);
                //拼接字符串
               String str11= "{"+jagid.toString().replace("{","").replace("}","")+","+jstamp.toString().replace("{","").replace("}","")+","+
                       jaction.toString().replace("{","").replace("}","")+","+jsign.toString().replace("{","").replace("}","")+","+
                       jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\","").replace("{\"args\"","\"args\"");

                //Base64  编码
                BASE64Encoder base64Encoder=new BASE64Encoder();
                String data= null;
                 try {
                    data = base64Encoder.encode(str11.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String,String> map=new HashMap<String,String>();
                map.put("data",data);
                try {
                    //查排名返回对象（导入订单）
                    CustomerRankingResult customerRankingResult= remoteService.getCustomerRanking(customerRankingParam);
                    //调点击返回对象（导入订单）
                    CustomerOptimizationResult customerOptimizationResult=remoteService.getOptimizationApi(map);
                    //判断两个返回结果是否为空
                    if(customerRankingResult!=null&&customerOptimizationResult!=null)
                    {
                        //判断两个返回结果是否成功
                        JSONObject jsonObject=customerOptimizationResult.getArgs();
                        String  code=jsonObject.get("code").toString();
                        JSONArray OptimizationValue=jsonObject.getJSONArray("value");
                        String message= OptimizationValue.getJSONArray(0).get(1).toString();

                        if(customerRankingResult.getMessage().equals("success.")&&("success").equals(code)&&("").equals(message))
                        {
                           JSONArray value=customerRankingResult.getValue();
                           JSONArray valueJSONArray= value.getJSONArray(0);
                           ApiId=Integer.parseInt(valueJSONArray.get(0).toString());
                           int ApiId1=Integer.parseInt(OptimizationValue.getJSONArray(0).get(0).toString());
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
                            //修改订单
                            billPriceMapper.insert(billPrice);
                            Bill bill1=new Bill();
                            bill1.setId(billId);
                            bill1.setUpdateUserId(user.getId());
                            bill1.setWebAppId(ApiId);
                            bill1.setWebAppId1(ApiId1);
                            bill1.setDayOptimization(1);
                            bill1.setBillAscription(caozuoyuanId);
                            bill1.setState(2);
                            //优化状态（优化中）（调点击）
                            bill1.setOpstate(1);
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
        billDetails.setStandardDays(bill.getStandardDays());
        billDetails.setState(bill.getState());
        billDetails.setOpstate(bill.getOpstate());
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
            //获取订单ID
            String[] id=params.get("selectContent["+i+"][id]");
            Long  billId=Long.parseLong(id[0]);
            //获取订单
            Bill bill=billMapper.selectByPrimaryKey(billId);
            //组包

            //调整点击录入订单
            int agid=OpDefine.agid;
            String action="setmcpd";
            String md5Key=OpDefine.md5Key;

            //取现在时间
            String dateString =new  SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            //订单优化量
            //加密sign
            int webApiId=bill.getWebAppId1();
            int[] aa=new int[]{webApiId,nums};
            JSONArray jsonArray=new JSONArray(aa);


            String arr="[\"kw\","+jsonArray.toString()+"]";
            //组包
            String str="";
            str=str.concat(JSON.toJSONString(dateString));
            str=str.concat(JSON.toJSONString(action));
            str=str.concat(arr);
            str=str.concat(JSON.toJSONString(md5Key));
            str=str.concat(JSON.toJSONString(agid));
            //加密sign
            String sign= null;
            try {
                sign = md5_urlEncode.EncoderByMd51(str);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //创建JSON
            JSONObject jaction=new JSONObject();
            jaction.put("action",action);
            JSONObject jagid=new JSONObject();
            jagid.put("agid",agid);
            JSONObject jstamp=new JSONObject();
            jstamp.put("stamp",dateString);
            JSONObject jargs=new JSONObject();
            jargs.put("args",arr);
            JSONObject jsign=new JSONObject();
            jsign.put("sign",sign);
            //拼接字符串
            String str11= "{"+jagid.toString().replace("{","").replace("}","")+","+jstamp.toString().replace("{","").replace("}","")+","+
                    jaction.toString().replace("{","").replace("}","")+","+jsign.toString().replace("{","").replace("}","")+","+
                    jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\","").replace("{\"args\"","\"args\"");
            //Base64  编码
            BASE64Encoder base64Encoder=new BASE64Encoder();
            String data= null;
            try {
                data = base64Encoder.encode(str11.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Map<String,String> map=new HashMap<String,String>();
            map.put("data",data);
            //调点击返回对象（修改点击）
            CustomerOptimizationResult customerOptimizationResult=remoteService.getOptimizationApi(map);
            if(customerOptimizationResult!=null)
            {
                //判断返回结果是否成功
                JSONObject jsonObject=customerOptimizationResult.getArgs();
                String  code=jsonObject.get("code").toString();
                if("success".equals(code))
                {

                    bill.setDayOptimization(nums);
                    billMapper.updateByPrimaryKeySelective(bill);
                }
            }
        }
        return 0;
    }
    /**
     * 优化状态调整
     * @param params
     * @param user
     * @return
     */
    @Override
    public int updateYBYstate(Map<String, String[]> params, LoginUser user) {
        //任务个数
        String[] checkboxLength=params.get("length");
        //任务状态（1：在线  100：离线  999：删除）
        String[] stateLength=params.get("state");
        if(checkboxLength.length>0&&stateLength.length>0)
        {
            int  state=Integer.parseInt(stateLength[0]);
            int  length=Integer.parseInt(checkboxLength[0]);
            if(state!=0&&length!=0)
            {
                for(int i=0;i<length;i++)
                {
                    //获取订单ID
                    String[] id=params.get("selectContent["+i+"][id]");
                    Long  billId=Long.parseLong(id[0]);
                    //获取订单
                    Bill bill=billMapper.selectByPrimaryKey(billId);
                    //组包
                    //调整点击录入订单
                    int agid=OpDefine.agid;
                    String action="setstatus";
                    String md5Key=OpDefine.md5Key;

                    //取现在时间
                    String dateString =new  SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    //订单优化量
                    //加密sign
                    int webApiId=bill.getWebAppId1();
                    int[] intarr=new int[]{webApiId,state};
                    JSONArray jsonArray=new JSONArray(intarr);


                    String arr="[\"kw\","+jsonArray.toString()+"]";
                    //组包
                    String str="";
                    str=str.concat(JSON.toJSONString(dateString));
                    str=str.concat(JSON.toJSONString(action));
                    str=str.concat(arr);
                    str=str.concat(JSON.toJSONString(md5Key));
                    str=str.concat(JSON.toJSONString(agid));
                    //加密sign
                    String sign= null;
                    try {
                        sign = md5_urlEncode.EncoderByMd51(str);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //创建JSON
                    JSONObject jaction=new JSONObject();
                    jaction.put("action",action);
                    JSONObject jagid=new JSONObject();
                    jagid.put("agid",agid);
                    JSONObject jstamp=new JSONObject();
                    jstamp.put("stamp",dateString);
                    JSONObject jargs=new JSONObject();
                    jargs.put("args",arr);
                    JSONObject jsign=new JSONObject();
                    jsign.put("sign",sign);
                    //拼接字符串
                    String str11= "{"+jagid.toString().replace("{","").replace("}","")+","+jstamp.toString().replace("{","").replace("}","")+","+
                            jaction.toString().replace("{","").replace("}","")+","+jsign.toString().replace("{","").replace("}","")+","+
                            jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\","").replace("{\"args\"","\"args\"");
                    //Base64  编码
                    BASE64Encoder base64Encoder=new BASE64Encoder();
                    String data= null;
                    try {
                        data = base64Encoder.encode(str11.getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("data",data);
                    //调点击返回对象（停止优化）
                    CustomerOptimizationResult customerOptimizationResult=remoteService.getOptimizationApi(map);
                    if(customerOptimizationResult!=null)
                    {
                        //判断返回结果是否成功
                        JSONObject jsonObject=customerOptimizationResult.getArgs();
                        String  code=jsonObject.get("code").toString();
                        if("success".equals(code))
                        {
                            //优化离线
                            if(state==1)
                            {
                                bill.setOpstate(1);
                            }
                            else
                            {
                                bill.setOpstate(2);
                            }

                            billMapper.updateByPrimaryKeySelective(bill);
                        }
                    }
                }
                return 1;

            }
            else
            {
                return  0;
            }
        }

        return 0;
    }

    /**
     * 优化停止(订单主状态)
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

    /**
     * 优化启动(订单主状态)
     * @param params
     * @param user
     * @return
     */
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

    /**
     * 获取价格
     * @param limit
     * @param offset
     * @param billId
     * @param user
     * @param way
     * @return
     */
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
        //正常单
        bill.setBillType(1);
        //优化状态（未优化）（调点击）
        bill.setOpstate(0);
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
        //判断价格
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
