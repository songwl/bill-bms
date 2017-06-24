package com.yipeng.bill.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.*;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

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
    @Autowired
    private ForbiddenWordsMapper forbiddenWordsMapper;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private BillOptimizationMapper billOptimizationMapper;
    Md5_UrlEncode md5_urlEncode = new Md5_UrlEncode();

    /**
     * 相同价导入
     *
     * @param bill
     * @return
     */
    @Override
    public String saveSameBill(Map<String, String[]> params, LoginUser user) {

        int state = 0;
        if (user.hasRole("DISTRIBUTOR") || user.hasRole("ASSISTANT")) {
            state = 1;
        }
        String[] urlsArr = params.get("url");
        String[] keywordsArr = params.get("keyword");
        String[] rankend = params.get("rankend");
        String[] price = params.get("price");
        String[] rankend1 = params.get("rankend1");
        String[] price1 = params.get("price1");
        String[] rankend2 = params.get("rankend2");
        String[] price2 = params.get("price2");
        String[] rankend3 = params.get("rankend3");
        String[] price3 = params.get("price3");
        String[] urls = urlsArr[0].split("\n");
        String[] keywords = keywordsArr[0].split("\n");
        String[] search = params.get("search");
        String[] customerId = params.get("customerId");
        String errorDetails = "";
        //判断是否为网址
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[wW]{3}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        Pattern pattern1 = Pattern.compile("^?([a-zA-Z0-9]([a-zA-Z0-9\\\\-]{0,61}[a-zA-Z0-9])?\\\\.)+[a-zA-Z]{2,6}");
        Pattern pattern2 = Pattern.compile("^([a-zA-Z\\d][a-zA-Z\\d-]+\\.)+[a-zA-Z\\d-][^ ]*$");
        Pattern pattern3 = Pattern
                .compile("^([mM]{1}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        if (urls.length == keywords.length) {
            //违禁词库
            List<ForbiddenWords> forbiddenWordsList = forbiddenWordsMapper.selectBySelective();
            for (int i = 0; i < urls.length; i++) {
                //判断当前关键词是否为违禁词
                Boolean ForbiddenWordsBool = true;//变量
                for (ForbiddenWords items : forbiddenWordsList
                        ) {
                    if (keywords[i].replace(" ", "").equals(items.getWords()))//是违禁词
                    {
                        ForbiddenWordsBool = false;
                    }
                }

                //不是违禁词  就添加
                if (ForbiddenWordsBool && (pattern.matcher(urls[i]).matches() || pattern1.matcher(urls[i]).matches() || pattern2.matcher(urls[i]).matches() || pattern3.matcher(urls[i]).matches())) {
                    Map<String, Object> map = new HashMap<>();
                    String urlss = urls[i];

                    if (search[0].equals("360") || search[0].equals("搜狗")) {
                        String urlStr = urls[i].substring(0, 7);
                        //String urlLast=urls[i].substring(urls[i].length()-1,urls[i].length());
                        if ("http://".equals(urlStr)) {
                            map.put("url", urls[i].replace(" ", "").replace(",", "."));
                        } else {

                            if (!"http://".equals(urlStr)) {
                                urlss = "http://" + urlss.replace(" ", "").replace(",", ".");
                            }
                           /* if(!"/".equals(urlLast))
                            {
                                urlss=urlss+"/";
                            }*/
                            map.put("url", urlss.replace(" ", "").replace(",", "."));
                        }

                    } else {
                        map.put("url", urls[i].replace(" ", "").replace(",", "."));
                    }
                    //先查询是否订单已经存在
                    Map<String, Object> params1 = new HashMap();
                    params1.put("website", urlss);
                    params1.put("keywords", keywords[i]);
                    List<Bill> billList = billMapper.selectAllSelective(params1);
                    map.put("keywords", keywords[i].trim());
                    if (user.hasRole("ASSISTANT")) {
                        map.put("CreateUserId", user.getCreateUserId());
                    } else {
                        map.put("CreateUserId", user.getId());
                    }
                    map.put("search", search[0]);
                    map.put("price", price[0]);
                    map.put("rankend", rankend[0]);
                    map.put("customerId", customerId[0]);
                    map.put("price1", price1[0]);
                    map.put("rankend1", rankend1[0]);
                    map.put("price2", price2[0]);
                    map.put("rankend2", rankend2[0]);
                    map.put("price3", price3[0]);
                    map.put("rankend3", rankend3[0]);
                    map.put("state", state);
                    //存在 判断搜索引擎是否一致
                    if (billList.size() > 0) {
                        Boolean bool = true;
                        for (Bill bill : billList
                                ) {

                            //查询每个订单对应的搜索引擎名
                            BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                            if (billSearchSupport.getSearchSupport().equals(search[0])) {
                                //判断当前登录对象单价表里是否有对应的记录
                                BillPrice billPrice = new BillPrice();
                                billPrice.setBillId(bill.getId());
                                billPrice.setOutMemberId(Long.parseLong(customerId[0]));
                                List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
                                //如果当前价格存在
                                if (!CollectionUtils.isEmpty(billPriceList)) {
                                    bool = false;
                                    errorDetails += +(i + 1) + "网址：" + urls[i] + "  关键词：" + keywords[i] + "已存在！  ";
                                    break;
                                }


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
                //返回错误信息
                else {
                    errorDetails += +(i + 1) + "网址：" + urls[i] + " 格式错误或关键词：" + keywords[i] + "是违禁词   ";
                }


            }

        }


        return errorDetails;
    }

    /**
     * 不同价导入
     *
     * @return
     */
    @Override
    public String savaDiffrentBill(Map<String, String[]> params, LoginUser user) {

        int state = 0;
        if (user.hasRole("DISTRIBUTOR") || user.hasRole("ASSISTANT")) {
            state = 1;
        }
        String[] dfurlsArr = params.get("dfurl");
        String[] dfkeywordsArr = params.get("dfkeyword");
        String[] dfpricesArr = params.get("dfprice");
        String[] dfurls = dfurlsArr[0].split("\n");
        String[] dfkeywords = dfkeywordsArr[0].split("\n");
        String[] dfprices = dfpricesArr[0].split("\n");
        String[] dfsearch = params.get("dfsearch");
        String[] dfrankend = params.get("dfrankend");
        String[] customerId = params.get("customerIds");
        String errorDetails = "";
        //判断是否为网址
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[wW]{3}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        Pattern pattern1 = Pattern.compile("^?([a-zA-Z0-9]([a-zA-Z0-9\\\\-]{0,61}[a-zA-Z0-9])?\\\\.)+[a-zA-Z]{2,6}");
        Pattern pattern2 = Pattern.compile("^([a-zA-Z\\d][a-zA-Z\\d-]+\\.)+[a-zA-Z\\d-][^ ]*$");
        Pattern pattern3 = Pattern
                .compile("^([mM]{1}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        if (dfurls.length == dfkeywords.length && dfkeywords.length == dfprices.length) {
            //违禁词库
            List<ForbiddenWords> forbiddenWordsList = forbiddenWordsMapper.selectBySelective();

            for (int i = 0; i < dfurls.length; i++) {
                //判断当前关键词是否为违禁词
                Boolean ForbiddenWordsBool = true;//变量
                for (ForbiddenWords items : forbiddenWordsList
                        ) {
                    if (dfkeywords[i].replace(" ", "").equals(items.getWords()))//是违禁词
                    {
                        ForbiddenWordsBool = false;
                    }
                }

                //先查询是否订单已经存在


                //判断搜索引擎
                String urlNew = dfurls[i];
                Map<String, Object> params1 = new HashMap();
                params1.put("website", urlNew.replace(" ", "").replace(",", "."));
                params1.put("keywords", dfkeywords[i].trim());
                List<Bill> billList = billMapper.selectAllSelective(params1);

                //不是违禁词  就添加
                if (ForbiddenWordsBool && (pattern.matcher(urlNew).matches() || pattern1.matcher(urlNew).matches() || pattern2.matcher(urlNew).matches() || pattern3.matcher(urlNew).matches())) {

                    if (dfsearch[0].equals("360") || dfsearch[0].equals("搜狗")) {

                        String urlStr = dfurls[i].substring(0, 7);
                        if ("http://".equals(urlStr)) {
                            urlNew = urlNew;
                        } else {

                            if (!"http://".equals(urlStr)) {
                                urlNew = "http://" + urlNew;
                            }

                        }
                    } else {
                        urlNew = dfurls[i];
                    }
                    if (billList != null && billList.size() > 0) {
                        Boolean bool = true;
                        for (Bill bill : billList
                                ) {
                            //查询每个订单对应的搜索引擎名
                            BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                            if (billSearchSupport.getSearchSupport().equals(dfsearch[0])) {
                                //判断当前登录对象单价表里是否有对应的记录
                                BillPrice billPrice = new BillPrice();
                                billPrice.setBillId(bill.getId());
                                billPrice.setOutMemberId(Long.parseLong(customerId[0]));
                                List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
                                //如果当前价格存在
                                if (!CollectionUtils.isEmpty(billPriceList)) {
                                    bool = false;
                                    errorDetails += +(i + 1) + "网址：" + dfurls[i] + "关键词：" + dfkeywords[i] + "已存在！ ";
                                    break;
                                }
                            }
                        }
                        if (bool) {

                            Bill bill = new Bill();
                            bill.setWebsite(urlNew.replace(",", "."));
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
                            //删除状态（0未删除，1已删除）
                            bill.setDeleteState(0);
                            //正常单
                            bill.setBillType(1);
                            //优化状态（未优化）（调点击）
                            bill.setOpstate(0);
                            Long billId = billMapper.insert(bill);
                            //订单引擎表
                            BillSearchSupport billSearchSupport = new BillSearchSupport();
                            billSearchSupport.setBillId(bill.getId());
                            billSearchSupport.setSearchSupport(dfsearch[0]);
                            billSearchSupportMapper.insert(billSearchSupport);
                            //订单单价表
                            BillPrice billPrice = new BillPrice();
                            billPrice.setBillId(bill.getId());
                            BigDecimal bd = new BigDecimal(dfprices[i]);
                            billPrice.setPrice(bd);
                            billPrice.setBillRankingStandard(Long.parseLong(dfrankend[0]));
                            if (user.hasRole("ASSISTANT")) {
                                billPrice.setInMemberId(user.getCreateUserId());
                            } else {
                                billPrice.setInMemberId(user.getId());
                            }

                            billPrice.setOutMemberId(new Long(customerId[0]));
                            billPrice.setCreateTime(new Date());
                            billPriceMapper.insert(billPrice);

                        }
                    } else {


                        Bill bill = new Bill();
                        //判断搜索引擎
                        bill.setWebsite(urlNew);
                        bill.setKeywords(dfkeywords[i]);
                        if (user.hasRole("ASSISTANT")) {
                            bill.setCreateUserId(user.getCreateUserId());
                            bill.setUpdateUserId(user.getCreateUserId());
                        } else {
                            bill.setCreateUserId(user.getId());
                            bill.setUpdateUserId(user.getId());
                        }

                        bill.setCreateTime(new Date());
                        //默认排名为200（无排名）
                        bill.setFirstRanking(200);
                        bill.setNewRanking(200);
                        bill.setStandardDays(0);
                        bill.setDayOptimization(0);
                        bill.setAllOptimization(0);
                        bill.setState(state);
                        //删除状态（0未删除，1已删除）
                        bill.setDeleteState(0);
                        //正常单
                        bill.setBillType(1);
                        Long billId = billMapper.insert(bill);
                        //订单引擎表
                        BillSearchSupport billSearchSupport = new BillSearchSupport();
                        billSearchSupport.setBillId(bill.getId());
                        billSearchSupport.setSearchSupport(dfsearch[0]);
                        billSearchSupportMapper.insert(billSearchSupport);
                        //订单单价表
                        BillPrice billPrice = new BillPrice();
                        billPrice.setBillId(bill.getId());
                        BigDecimal bd = new BigDecimal(dfprices[i]);
                        billPrice.setPrice(bd);
                        billPrice.setBillRankingStandard(Long.parseLong(dfrankend[0]));
                        if (user.hasRole("ASSISTANT")) {
                            billPrice.setInMemberId(user.getCreateUserId());
                        } else {
                            billPrice.setInMemberId(user.getId());
                        }

                        billPrice.setOutMemberId(new Long(customerId[0]));
                        billPrice.setCreateTime(new Date());
                        billPriceMapper.insert(billPrice);

                    }
                }
                //返货错误信息
                else {
                    errorDetails += +(i + 1) + "网址：" + dfurls[i] + " 格式错误或关键词：" + dfkeywords[i] + "是违禁词！   ";
                }
            }
        }

        return errorDetails;
    }

    /**
     * 客户方导入
     *
     * @return
     */
    @Override
    public String saveKeHuBill(Map<String, String[]> params, LoginUser user) {
        int state = -1;
        String[] urlsArr = params.get("url");
        String[] keywordsArr = params.get("keyword");
        String[] search = params.get("search");
        String errorDetails = "";
        //判断是否为网址
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[wW]{3}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        Pattern pattern1 = Pattern.compile("^?([a-zA-Z0-9]([a-zA-Z0-9\\\\-]{0,61}[a-zA-Z0-9])?\\\\.)+[a-zA-Z]{2,6}");
        Pattern pattern2 = Pattern.compile("^([a-zA-Z\\d][a-zA-Z\\d-]+\\.)+[a-zA-Z\\d-][^ ]*$");
        Pattern pattern3 = Pattern
                .compile("^([mM]{1}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        String[] urls = urlsArr[0].split("\n");
        String[] keywords = keywordsArr[0].split("\n");
        if (urls.length == keywords.length) {
            //违禁词库
            List<ForbiddenWords> forbiddenWordsList = forbiddenWordsMapper.selectBySelective();
            for (int i = 0; i < urls.length; i++) {
                //判断当前关键词是否为违禁词
                Boolean ForbiddenWordsBool = true;//变量
                for (ForbiddenWords items : forbiddenWordsList
                        ) {
                    if (keywords[i].replace(" ", "").equals(items.getWords()))//是违禁词
                    {
                        ForbiddenWordsBool = false;
                    }
                }

                //不是违禁词  就添加
                if (ForbiddenWordsBool && (pattern.matcher(urls[i]).matches() || pattern1.matcher(urls[i]).matches() || pattern2.matcher(urls[i]).matches() || pattern3.matcher(urls[i]).matches())) {
                    Map<String, Object> map = new HashMap<>();
                    String urlss = urls[i];

                    if (search[0].equals("360") || search[0].equals("搜狗")) {
                        String urlStr = urls[i].substring(0, 7);
                        //String urlLast=urls[i].substring(urls[i].length()-1,urls[i].length());
                        if ("http://".equals(urlStr)) {
                            map.put("url", urls[i].replace(" ", "").replace(",", "."));
                        } else {

                            if (!"http://".equals(urlStr)) {
                                urlss = "http://" + urlss.replace(" ", "").replace(",", ".");
                            }
                           /* if(!"/".equals(urlLast))
                            {
                                urlss=urlss+"/";
                            }*/
                            map.put("url", urlss.replace(" ", "").replace(",", "."));
                        }

                    } else {
                        map.put("url", urls[i].replace(" ", "").replace(",", "."));
                    }
                    //先查询是否订单已经存在
                    Map<String, Object> params1 = new HashMap();
                    params1.put("website", urlss);
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
                                errorDetails += +(i + 1) + "网址：" + urls[i] + "  关键词：" + keywords[i] + "已存在！  ";
                                break;
                            }


                        }

                        if (bool) {
                            //订单主表
                            Bill bill = new Bill();
                            bill.setWebsite(urlss);
                            bill.setKeywords(keywords[i]);
                            bill.setCreateTime(new Date());
                            bill.setCreateUserId(user.getId());
                            bill.setFirstRanking(200);
                            bill.setNewRanking(200);
                            bill.setStandardDays(0);
                            bill.setDayOptimization(0);
                            bill.setAllOptimization(0);
                            bill.setState(state);
                            bill.setBillType(1);
                            //优化状态（未优化）（调点击）
                            bill.setOpstate(0);
                            //删除状态（0未删除，1已删除）
                            bill.setDeleteState(0);
                            Long billId = billMapper.insert(bill);
                            //订单引擎表
                            BillSearchSupport billSearchSupport = new BillSearchSupport();
                            billSearchSupport.setBillId(bill.getId());
                            billSearchSupport.setSearchSupport(search[0]);
                            billSearchSupportMapper.insert(billSearchSupport);

                        }
                    }
                    //不存在 直接录入
                    else {
                        //订单主表
                        Bill bill = new Bill();
                        bill.setWebsite(urlss);
                        bill.setKeywords(keywords[i]);
                        bill.setCreateTime(new Date());
                        bill.setCreateUserId(user.getId());
                        bill.setFirstRanking(200);
                        bill.setNewRanking(200);
                        bill.setStandardDays(0);
                        bill.setDayOptimization(0);
                        bill.setAllOptimization(0);
                        bill.setState(state);
                        bill.setBillType(1);
                        //优化状态（未优化）（调点击）
                        bill.setOpstate(0);
                        //删除状态（0未删除，1已删除）
                        bill.setDeleteState(0);
                        Long billId = billMapper.insert(bill);
                        //订单引擎表
                        BillSearchSupport billSearchSupport = new BillSearchSupport();
                        billSearchSupport.setBillId(bill.getId());
                        billSearchSupport.setSearchSupport(search[0]);
                        billSearchSupportMapper.insert(billSearchSupport);
                    }
                } else {
                    errorDetails += +(i + 1) + "网址：" + urls[i] + " 格式错误或关键词：" + keywords[i] + "是违禁词   ";
                }
            }
        }

        return errorDetails;
    }

    /**
     * 订单录入
     *
     * @param params
     */
    public void insertPirce(Map<String, Object> params) {
        Long customerId = Long.valueOf(params.get("customerId").toString());
        Long CreateUserId = Long.valueOf(params.get("CreateUserId").toString());
        Bill bill = new Bill();
        bill.setWebsite(params.get("url").toString());
        bill.setKeywords(params.get("keywords").toString());
        bill.setCreateUserId((Long) params.get("CreateUserId"));
        bill.setUpdateUserId((Long) params.get("CreateUserId"));
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
        //删除状态（0未删除，1已删除）
        bill.setDeleteState(0);
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
        ret = new BigDecimal((String) params.get("price"));
        billPrice.setPrice(ret);
        Long rankend = Long.valueOf(params.get("rankend").toString());
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
            ret1 = new BigDecimal((String) params.get("price1"));
            billPrice1.setPrice(ret1);
            Long rankend1 = Long.valueOf(params.get("rankend1").toString());
            billPrice1.setBillRankingStandard(rankend1);
            billPrice1.setInMemberId(CreateUserId);
            billPrice1.setOutMemberId(customerId);
            billPrice1.setCreateTime(new Date());
            billPriceMapper.insert(billPrice1);
            if (!"".equals(params.get("price2").toString()) && null != params.get("price2").toString()) {
                BillPrice billPrice2 = new BillPrice();
                billPrice2.setBillId(bill.getId());
                BigDecimal ret2 = null;
                ret2 = new BigDecimal((String) params.get("price2"));
                billPrice2.setPrice(ret2);
                Long rankend2 = Long.valueOf(params.get("rankend").toString());
                billPrice2.setBillRankingStandard(rankend2);
                billPrice2.setInMemberId(CreateUserId);
                billPrice2.setOutMemberId(customerId);
                billPrice2.setCreateTime(new Date());
                billPriceMapper.insert(billPrice2);
                if (!"".equals(params.get("price3").toString()) && null != params.get("price3").toString()) {
                    BillPrice billPrice3 = new BillPrice();
                    billPrice3.setBillId(bill.getId());
                    BigDecimal ret3 = null;
                    ret3 = new BigDecimal((String) params.get("price3"));
                    billPrice3.setPrice(ret3);
                    Long rankend3 = Long.valueOf(params.get("rankend").toString());
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
     * 实现通过主键ID查订单
     *
     * @param id
     * @return
     */
    @Override
    public Bill getBillById(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    /**
     * 实现通过参数来查询集合(待审核页面)
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> pendingAuditList(Map<String, Object> params, LoginUser user) {

        //视图模型
        List<BillDetails> billDetailsList = new ArrayList<BillDetails>();
        int limit = Integer.parseInt(params.get("limit").toString());
        int offset = Integer.parseInt(params.get("offset").toString());
        int i = offset;
        if (user.hasRole("SUPER_ADMIN")) {
            Role role = roleMapper.selectByRoleCode("DISTRIBUTOR");
            if (role != null) {
                params.put("roleId", role.getId());
            }
            params.put("state", 1);

            //查询所有订单状态为1的
            List<Bill> billList = billMapper.selectByBillAudit(params);
            Long total = billMapper.selectByBillAuditCount(params);
            for (Bill bill : billList
                    ) {
                i++;
                BillDetails billDetails = new BillDetails();
                billDetails.setdisplayId(i);
                billDetails.setId(bill.getId());
                User user1 = userMapper.selectByPrimaryKey(bill.getUpdateUserId());
                billDetails.setUserName(user1.getUserName());
                //通过单价表里面的inmemberId获取outMembenId  从而获取客户名
                BillPrice billPrice = new BillPrice();
                billPrice.setBillId(bill.getId());
                billPrice.setInMemberId(user1.getId());
                List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
                //判断是否为空
                if (!CollectionUtils.isEmpty(billPriceList)) {
                    //查询对应客户
                    User kehuName = userMapper.selectByPrimaryKey(billPriceList.get(0).getOutMemberId());
                    billDetails.setUserNameByKehu(kehuName.getUserName());
                }


                billDetails.setKeywords(bill.getKeywords());
                billDetails.setWebsite(bill.getWebsite());
                //查出搜索引擎
                BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
                billDetails.setSearchName(billSearchSupport.getSearchSupport());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                billDetails.setCreateTime(sdf.format(bill.getCreateTime()));
                billDetails.setUpdateUserId(bill.getUpdateUserId());
                billDetails.setFirstRanking(bill.getFirstRanking());
                billDetails.setNewRanking(bill.getNewRanking());
                billDetails.setDayOptimization(bill.getDayOptimization());
                billDetails.setAllOptimization(billDetails.getAllOptimization());
                //当天消费(缺)
                //本月消费（缺）
                billDetails.setStandardDays(bill.getStandardDays());
                billDetails.setState(bill.getState());
                billDetails.setLength(total);
                billDetailsList.add(billDetails);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("total", total);
            map.put("rows", billDetailsList);
            return map;

        } else {

            params.put("userId", user.getId());
            params.put("state", 0);
            //查询所有订单状态为0的
            List<Bill> billList = billMapper.selectAgentBill(params);
            Long total = billMapper.selectAgentBillCount(params);
            for (Bill bill : billList
                    ) {
                i++;
                BillDetails billDetails = new BillDetails();
                billDetails.setdisplayId(i);
                billDetails.setId(bill.getId());
                User user1 = userMapper.selectByPrimaryKey(bill.getUpdateUserId());
                billDetails.setUserName(user1.getUserName());
                //通过单价表里面的inmemberId获取outMembenId  从而获取客户名
                BillPrice billPrice = new BillPrice();
                billPrice.setBillId(bill.getId());
                billPrice.setInMemberId(user1.getId());
                List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
                //判断是否为空
                if (!CollectionUtils.isEmpty(billPriceList)) {
                    //查询对应客户
                    User kehuName = userMapper.selectByPrimaryKey(billPriceList.get(0).getOutMemberId());
                    billDetails.setUserNameByKehu(kehuName.getUserName());
                }

                billDetails.setKeywords(bill.getKeywords());
                billDetails.setWebsite(bill.getWebsite());
                //查出搜索引擎
                BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
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
                billDetails.setLength(total);
                billDetailsList.add(billDetails);
            }
            ;
            Map<String, Object> map = new HashMap<>();
            map.put("total", total);
            map.put("rows", billDetailsList);
            return map;

        }


    }

    /**
     * 客户提交订单审核页面（上级审核）
     *
     * @param params
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> pendingAuditView1List(Map<String, Object> params, LoginUser loginUser) {
        //视图模型
        int limit = Integer.parseInt(params.get("limit").toString());
        int offset = Integer.parseInt(params.get("offset").toString());
        int i = offset;
        List<BillDetails> billDetailsList = new ArrayList<BillDetails>();
        Map<String, Object> sqlMap = new HashMap<>();
        if (loginUser.hasRole("ASSISTANT")) {
            sqlMap.put("userId", loginUser.getCreateUserId());
        } else {
            sqlMap.put("userId", loginUser.getId());
        }

        sqlMap.put("state", -1);
        sqlMap.put("limit", limit);
        sqlMap.put("offset", offset);

        List<Bill> billList = billMapper.selectByPendingAuditView1List(sqlMap);
        Long total = billMapper.selectByPendingAuditView1ListCount(sqlMap);
        for (Bill bill : billList
                ) {


            BillDetails billDetails = new BillDetails();
            User user1 = userMapper.selectByPrimaryKey(bill.getCreateUserId());
            if (user1 != null) {
                billDetails.setUserName(user1.getUserName());
            }
            BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
            if (billSearchSupport != null) {
                billDetails.setSearchName(billSearchSupport.getSearchSupport());
            }
            i++;
            //调用方法  加入集合
            billDetails.setdisplayId(i);
            billDetails.setId(bill.getId());
            //插入数据
            billDetails.setKeywords(bill.getKeywords());
            billDetails.setWebsite(bill.getWebsite());
            billDetails.setCreateTime(DateUtils.formatDate(bill.getCreateTime()));
            billDetailsList.add(billDetails);
        }

        Map<String, Object> tableMap = new HashMap<>();
        tableMap.put("total", total);
        tableMap.put("rows", billDetailsList);
        return tableMap;
    }

    /**
     * 客户提交订单（待审核，客户自己看的）
     *
     * @param params
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> pendingAuditViewKeHuList(Map<String, Object> params, LoginUser loginUser) {
        params.put("createUserId", loginUser.getId());
        List<BillDetails> billDetailsList = new ArrayList<>();
        List<Bill> billList = billMapper.selectByAuditViewKeHuList(params);
        Long total = billMapper.selectByAuditViewKeHuListCount(params);
        int limit = Integer.parseInt(params.get("limit").toString());
        int offset = Integer.parseInt(params.get("offset").toString());
        int i = offset;
        for (Bill item : billList
                ) {
            i++;
            BillDetails billDetails = new BillDetails();
            BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(item.getId());
            //插入数据
            billDetails.setdisplayId(i);
            billDetails.setId(item.getId());
            billDetails.setKeywords(item.getKeywords());
            billDetails.setWebsite(item.getWebsite());
            billDetails.setSearchName(billSearchSupport.getSearchSupport());
            billDetails.setCreateTime(DateUtils.formatDate(item.getCreateTime()));
            billDetails.setState(item.getState());
            billDetailsList.add(billDetails);
        }
        Map<String, Object> viewMap = new HashMap<>();
        viewMap.put("rows", billDetailsList);
        viewMap.put("total", total);
        return viewMap;
    }

    /**
     * 调整价格
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int updateBillPrice(Map<String, String[]> params, LoginUser loginUser) {
        //获取参数
        String[] selectContent = params.get("selectContent[0][userName]");
        String[] checkboxLength = params.get("checkboxLength");
        String[] price = params.get("price");
        String[] price1 = params.get("price1");
        String[] price2 = params.get("price2");
        String[] price3 = params.get("price3");
        String[] rankend = params.get("rankend");
        String[] rankend1 = params.get("rankend1");
        String[] rankend2 = params.get("rankend2");
        String[] rankend3 = params.get("rankend3");
        int length = Integer.parseInt(checkboxLength[0]);
        for (int i = 0; i < length; i++) {
            //数据库订单ID
            String[] id = params.get("selectContent[" + i + "][id]");
            ;
            Long billId = Long.parseLong(id[0]);
            //查询单价对象
            BillPrice billPrice = new BillPrice();
            billPrice.setBillId(billId);
            //判断当前登录对象
            if (loginUser.hasRole("SUPER_ADMIN") || loginUser.hasRole("DISTRIBUTOR") || loginUser.hasRole("AGENT") || loginUser.hasRole("ASSISTANT")) {
                if (loginUser.hasRole("ASSISTANT")) {
                    billPrice.setInMemberId(loginUser.getCreateUserId());
                } else {
                    billPrice.setInMemberId(loginUser.getId());
                }

            } else if (loginUser.hasRole("COMMISSIONER")) {
                billPrice.setInMemberId(loginUser.getCreateUserId());
            }

            List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
            //判断对象是否为空
            if (!CollectionUtils.isEmpty(billPriceList)) {
                if (!"NaN".equals(price[0])) {
                    //修改的对象
                    BillPrice billPriceInsert = new BillPrice();
                    billPriceInsert.setId(billPriceList.get(0).getId());
                    billPriceInsert.setBillRankingStandard(Long.parseLong(rankend[0]));
                    billPriceInsert.setPrice(new BigDecimal(price[0]));
                    //修改数据库
                    billPriceMapper.updateByPrimaryKeySelective(billPriceInsert);
                }
                //判断价格2是否为空  如果不为空才修改数据库
                if (!"NaN".equals(price1[0])) {
                    //价格已经存在
                    if (billPriceList.size() == 2) {
                        //修改的对象
                        BillPrice billPriceInsert = new BillPrice();
                        billPriceInsert.setId(billPriceList.get(1).getId());
                        billPriceInsert.setBillRankingStandard(Long.parseLong(rankend1[0]));
                        billPriceInsert.setPrice(new BigDecimal(price1[0]));
                        //修改数据库
                        billPriceMapper.updateByPrimaryKeySelective(billPriceInsert);
                    } else {
                        //插入的对象
                        BillPrice billPriceInsert1 = new BillPrice();
                        billPriceInsert1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                        billPriceInsert1.setPrice(new BigDecimal(price1[0]));
                        billPriceInsert1.setBillId(billId);
                        //判断当前登录对象
                        if (loginUser.hasRole("SUPER_ADMIN") || loginUser.hasRole("DISTRIBUTOR") || loginUser.hasRole("AGENT") || loginUser.hasRole("ASSISTANT")) {
                            if (loginUser.hasRole("ASSISTANT")) {
                                billPriceInsert1.setInMemberId(loginUser.getCreateUserId());
                            } else {
                                billPriceInsert1.setInMemberId(loginUser.getId());
                            }

                        } else if (loginUser.hasRole("COMMISSIONER")) {
                            billPriceInsert1.setInMemberId(loginUser.getCreateUserId());
                        }

                        billPriceInsert1.setOutMemberId(billPriceList.get(0).getOutMemberId());
                        billPriceInsert1.setCreateTime(new Date());
                        //修改数据库
                        billPriceMapper.insert(billPriceInsert1);
                    }
                }
                //判断价格3是否为空  如果不为空才修改数据库
                if (!"NaN".equals(price2[0])) {
                    //价格已经存在 修改数据库
                    if (billPriceList.size() == 3) {
                        //修改的对象
                        BillPrice billPriceInsert2 = new BillPrice();
                        billPriceInsert2.setId(billPriceList.get(2).getId());
                        billPriceInsert2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                        billPriceInsert2.setPrice(new BigDecimal(price2[0]));
                        //修改数据库
                        billPriceMapper.updateByPrimaryKeySelective(billPriceInsert2);
                    }
                    //价格不存在 新增数据库
                    else {
                        //插入的对象
                        BillPrice billPriceInsert3 = new BillPrice();
                        billPriceInsert3.setBillRankingStandard(Long.parseLong(rankend2[0]));
                        billPriceInsert3.setPrice(new BigDecimal(price2[0]));
                        billPriceInsert3.setBillId(billId);
                        if (loginUser.hasRole("SUPER_ADMIN") || loginUser.hasRole("DISTRIBUTOR") || loginUser.hasRole("AGENT") || loginUser.hasRole("ASSISTANT")) {
                            if (loginUser.hasRole("ASSISTANT")) {
                                billPriceInsert3.setInMemberId(loginUser.getCreateUserId());
                            } else {

                                billPriceInsert3.setInMemberId(loginUser.getId());
                            }

                        } else if (loginUser.hasRole("COMMISSIONER")) {
                            billPriceInsert3.setInMemberId(loginUser.getCreateUserId());
                        }

                        billPriceInsert3.setOutMemberId(billPriceList.get(0).getOutMemberId());
                        billPriceInsert3.setCreateTime(new Date());
                        //修改数据库
                        billPriceMapper.insert(billPriceInsert3);
                    }
                }
                //判断价格4是否为空  如果不为空才修改数据库
                if (!"NaN".equals(price3[0])) {
                    //价格已经存在 修改数据库
                    if (billPriceList.size() == 4) {
                        //修改的对象
                        BillPrice billPriceInsert4 = new BillPrice();
                        billPriceInsert4.setId(billPriceList.get(3).getId());
                        billPriceInsert4.setBillRankingStandard(Long.parseLong(rankend3[0]));
                        billPriceInsert4.setPrice(new BigDecimal(price3[0]));
                        //修改数据库
                        billPriceMapper.updateByPrimaryKeySelective(billPriceInsert4);
                    }
                    //价格不存在 新增数据库
                    else {
                        //插入的对象
                        BillPrice billPriceInsert5 = new BillPrice();
                        billPriceInsert5.setBillRankingStandard(Long.parseLong(rankend3[0]));
                        billPriceInsert5.setPrice(new BigDecimal(price3[0]));
                        billPriceInsert5.setBillId(billId);
                        if (loginUser.hasRole("SUPER_ADMIN") || loginUser.hasRole("DISTRIBUTOR") || loginUser.hasRole("AGENT") || loginUser.hasRole("ASSISTANT")) {
                            if (loginUser.hasRole("ASSISTANT")) {
                                billPriceInsert5.setInMemberId(loginUser.getCreateUserId());
                            } else {

                                billPriceInsert5.setInMemberId(loginUser.getId());
                            }

                        } else if (loginUser.hasRole("COMMISSIONER")) {
                            billPrice.setInMemberId(loginUser.getCreateUserId());
                        }

                        billPriceInsert5.setOutMemberId(billPriceList.get(0).getOutMemberId());
                        billPriceInsert5.setCreateTime(new Date());
                        //修改数据库
                        billPriceMapper.insert(billPriceInsert5);
                    }
                }

            } else {
                return 1;
            }

        }
        return 0;
    }

    /**
     * 修改订单信息（待审核的订单）
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int updateBillDetails(Long billId, String website, String keyword, LoginUser loginUser) {
        //查询对应的订单信息
        Bill bill = billMapper.selectByPrimaryKey(billId);
        //判断是否为网址
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[wW]{3}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        Pattern pattern1 = Pattern.compile("^?([a-zA-Z0-9]([a-zA-Z0-9\\\\-]{0,61}[a-zA-Z0-9])?\\\\.)+[a-zA-Z]{2,6}");
        Pattern pattern2 = Pattern.compile("^([a-zA-Z\\d][a-zA-Z\\d-]+\\.)+[a-zA-Z\\d-][^ ]*$");
        Pattern pattern3 = Pattern.compile("^([mM]{1}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        //违禁词判断
        ForbiddenWords forbiddenWords = forbiddenWordsMapper.selectByKeyWord(keyword);
        if (!pattern.matcher(website).matches() && !pattern1.matcher(website).matches() && !pattern2.matcher(website).matches() && !pattern3.matcher(website).matches()) {
            return 1;
        }
        if (forbiddenWords != null) {
            return 2;
        }
        if (bill == null) {
            return 3;
        }
        if ((pattern.matcher(website).matches() || pattern1.matcher(website).matches() || pattern2.matcher(website).matches() || pattern3.matcher(website).matches()) && forbiddenWords == null && bill != null) {
            bill.setWebsite(website);
            bill.setKeywords(keyword);
            int a = billMapper.updateByPrimaryKeySelective(bill);
            if (a == 1) {
                return 0;
            } else {
                return 4;
            }
        } else {
            return 5;
        }

    }

    /**
     * 修改订单信息（优化中的订单）
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int updateBillDetailsYouHua(Long billId, String website, String keyword, LoginUser loginUser) {
        String[] state = {"999"};
        String[] length = {"1"};
        String[] billArr = {billId.toString()};
        Map<String, String[]> map = new HashMap<>();
        map.put("state", state);
        map.put("length", length);
        map.put("billId", billArr);
        //查询对应的订单信息
        Bill bill = billMapper.selectByPrimaryKey(billId);
        //判断是否为网址
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[wW]{3}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        Pattern pattern1 = Pattern.compile("^?([a-zA-Z0-9]([a-zA-Z0-9\\\\-]{0,61}[a-zA-Z0-9])?\\\\.)+[a-zA-Z]{2,6}");
        Pattern pattern2 = Pattern.compile("^([a-zA-Z\\d][a-zA-Z\\d-]+\\.)+[a-zA-Z\\d-][^ ]*$");
        Pattern pattern3 = Pattern.compile("^([mM]{1}[.])(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/]*$)");
        //违禁词判断
        ForbiddenWords forbiddenWords = forbiddenWordsMapper.selectByKeyWord(keyword);
        if (!pattern.matcher(website).matches() && !pattern1.matcher(website).matches() && !pattern2.matcher(website).matches() && !pattern3.matcher(website).matches()) {
            return 1;
        }
        if (forbiddenWords != null) {
            return 2;
        }
        if (bill == null) {
            return 3;
        }
        //通过判断
        if ((pattern.matcher(website).matches() || pattern1.matcher(website).matches() || pattern2.matcher(website).matches() || pattern3.matcher(website).matches()) && forbiddenWords == null && bill != null) {
            if (bill != null) {
                //判断修改的订单是否存在
                Map<String, Object> sqlMap = new HashMap<>();
                sqlMap.put("website", website);
                sqlMap.put("keywords", keyword);
                List<Bill> bills = billMapper.selectAllSelective(sqlMap);
                BillSearchSupport billSearchSupportNow = billSearchSupportMapper.selectByBillId(bill.getId());
                List<BillPrice> billPrice = billPriceMapper.selectByBillId(bill.getId());
                if (!CollectionUtils.isEmpty(bills)) {
                    Boolean boolTf = true;
                    for (Bill item : bills
                            ) {
                        //查询每个订单对应的搜索引擎名
                        BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(item.getId());
                        if (billSearchSupport.getSearchSupport().equals(billSearchSupportNow.getSearchSupport())) {
                            //判断当前登录对象单价表里是否有对应的记录
                            List<BillPrice> billPriceList = billPriceMapper.selectByBillId(item.getId());
                            //如果当前价格存在
                            if (!CollectionUtils.isEmpty(billPriceList)) {
                                for (BillPrice item1 : billPriceList
                                        ) {
                                    for (BillPrice item2 : billPrice
                                            ) {
                                        if (item1.getOutMemberId() == item2.getOutMemberId()) {
                                            boolTf = false;
                                        }
                                    }
                                }

                            }
                        }
                    }
                    //不冲突
                    if (boolTf) {
                        //根据taskId 获取所有的订单
                        List<Bill> billList = billMapper.selectByBillIdToGetTaskId(bill.getWebAppId());
                        if (!CollectionUtils.isEmpty(billList)) {
                            //获取的订单只有一个(删除接口，重新获取 TASKID)
                            if (billList.size() == 1) {
                                //删除扣费通道

                                int a = this.deleteYBYbill(map, loginUser);
                                String taskIdArr[] = {billList.get(0).getWebAppId().toString()};
                                Boolean bool = delSearchTask(taskIdArr);
                                if (a == 1 && bool) {
                                    int result = addSearchTask(billId, keyword, website);
                                    if (result == 1) {
                                        return 0;
                                    }
                                }
                            } else {
                                int result1 = addSearchTask(billId, keyword, website);
                                if (result1 == 1) {
                                    return 0;
                                }
                            }
                        }
                    }
                } else {
                    //删除扣费通道
                    int a = this.deleteYBYbill(map, loginUser);
                    String taskIdArr[] = {bill.getWebAppId().toString()};
                    Boolean bool = delSearchTask(taskIdArr);
                    if (a == 1 && bool) {
                        int result2 = addSearchTask(billId, keyword, website);
                        if (result2 == 1) {
                            return 0;
                        }
                    }
                }
            }
        }
        return 3;
    }

    /**
     * 通过接口添加订单（公用方法）
     *
     * @param billId
     * @param keyword
     * @param website
     * @return
     */
    public int addSearchTask(Long billId, String keyword, String website) {
        //查询对应的订单信息
        Bill bill = billMapper.selectByPrimaryKey(billId);
        CustomerRankingParam customerRankingParam = new CustomerRankingParam();
        BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(billId);

        String wAction = "AddSearchTask";
        keyword = keyword.replaceAll("[\\u00A0]+", "");
        String[] qkeyword = {keyword};

        String[] qurl = {website};
        //组合参数
        int[] timeSet = {8, 15};
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("keyword", qkeyword);
        jsonObj.put("url", qurl);
        jsonObj.put("time", System.currentTimeMillis());
        jsonObj.put("timeSet", timeSet);
        jsonObj.put("userId", Define.userId);
        jsonObj.put("businessType", 2006);
        //判断搜索引擎
        switch (billSearchSupport.getSearchSupport()) {
            case "百度":
                jsonObj.put("searchType", 1010);
                break;
            case "360":
                jsonObj.put("searchType", 1015);
                break;
            case "搜狗":
                jsonObj.put("searchType", 1030);
                break;
            case "手机百度":
                jsonObj.put("searchType", 7010);
                break;
            case "手机360":
                jsonObj.put("searchType", 7015);
                break;
            case "手机搜狗":
                jsonObj.put("searchType", 7030);
                break;
            case "神马":
                jsonObj.put("searchType", 7070);
                break;
        }

        jsonObj.put("searchOnce", true);
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
        int agid = OpDefine.agid;
        String action = "importkw";
        String md5Key = OpDefine.md5Key;

        //取现在时间
        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //填充实体类
        List<Yby> ybyList = new ArrayList<Yby>();
        //加密sign
        Yby yby = new Yby();
        yby.setKw(keyword.replaceAll("[\\u00A0]+", ""));
        yby.setUrl(website);
        //判断搜索引擎
        switch (billSearchSupport.getSearchSupport()) {
            case "百度":
                yby.setSe(1);
                break;
            case "360":
                yby.setSe(2);
                break;
            case "搜狗":
                yby.setSe(3);
                break;
            case "手机百度":
                yby.setSe(4);
                break;
            case "手机360":
                yby.setSe(5);
                break;
            case "手机搜狗":
                yby.setSe(6);
                break;
            case "神马":
                yby.setSe(7);
                break;
        }
        yby.setMcpd(1);
        ybyList.add(yby);
        //组包
        String str = "";
        str = str.concat(JSON.toJSONString(dateString));
        str = str.concat(JSON.toJSONString(action));
        str = str.concat(JSON.toJSONString(ybyList));
        str = str.concat(JSON.toJSONString(md5Key));
        str = str.concat(JSON.toJSONString(agid));
        //加密sign
        String sign = null;
        try {
            sign = md5_urlEncode.EncoderByMd51(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建JSON
        JSONObject jaction = new JSONObject();
        jaction.put("action", action);
        JSONObject jagid = new JSONObject();
        jagid.put("agid", agid);
        JSONObject jstamp = new JSONObject();
        jstamp.put("stamp", dateString);
        JSONObject jargs = new JSONObject();
        jargs.put("args", JSON.toJSONString(ybyList));
        JSONObject jsign = new JSONObject();
        jsign.put("sign", sign);
        //拼接字符串
        String str11 = "{" + jagid.toString().replace("{", "").replace("}", "") + "," + jstamp.toString().replace("{", "").replace("}", "") + "," +
                jaction.toString().replace("{", "").replace("}", "") + "," + jsign.toString().replace("{", "").replace("}", "") + "," +
                jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\", "").replace("{\"args\"", "\"args\"");

        //Base64  编码
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String data = null;
        try {
            data = base64Encoder.encode(str11.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("data", data);
        //查排名返回对象（导入订单）
        CustomerRankingResult customerRankingResult = null;
        try {
            customerRankingResult = remoteService.getCustomerRanking(customerRankingParam);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //调点击返回对象（导入订单）
        CustomerOptimizationResult customerOptimizationResult = remoteService.getOptimizationApi(maps);
        //判断两个返回结果是否为空
        if (customerRankingResult != null && customerOptimizationResult != null) {
            //判断两个返回结果是否成功
            JSONObject jsonObject = customerOptimizationResult.getArgs();
            String code = jsonObject.get("code").toString();
            if (customerRankingResult.getMessage().equals("success.") && ("success").equals(code)) {
                JSONArray OptimizationValue = jsonObject.getJSONArray("value");
                String message = OptimizationValue.getJSONArray(0).get(1).toString();
                JSONArray value = customerRankingResult.getValue();
                JSONArray valueJSONArray = value.getJSONArray(0);
                int ApiId = Integer.parseInt(valueJSONArray.get(0).toString());
                int ApiId1 = Integer.parseInt(OptimizationValue.getJSONArray(0).get(0).toString());
                bill.setKeywords(keyword);
                bill.setWebsite(website);
                bill.setWebAppId(ApiId);
                bill.setWebAppId1(ApiId1);
                bill.setUpdateTime(new Date());
                billMapper.updateByPrimaryKeySelective(bill);
                return 1;
            }
        }
        return 0;
    }

    /**
     * 渠道商审核订单，录入单价表数据
     *
     * @param params 价格
     * @param user   当前登录对象
     * @return
     */
    @Override
    public int distributorPrice(Map<String, String[]> params, LoginUser user) {

        String[] selectContent = params.get("selectContent[0][userName]");
        String[] checkboxLength = params.get("checkboxLength");
        String[] price1 = params.get("price1");
        String[] price2 = params.get("price2");
        String[] price3 = params.get("price3");

        int length = Integer.parseInt(checkboxLength[0]);
        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            String[] price = params.get("price");
            String[] rankend = params.get("rankend");
            Long billId = Long.parseLong(id[0]);
            //判断渠道商订单价格是否已经存在(有BUG 如果管理员录入价格 bool会变成true)
            List<BillPrice> billPriceList = billPriceMapper.selectByBillId(billId);
            Boolean bool = true;
            //如果有了 就修改
            if (billPriceList.size() > 0) {
                for (BillPrice billPrice1 : billPriceList
                        ) {
                    if (billPrice1.getInMemberId().longValue() == user.getId().longValue()) {
                        bool = false;
                    }
                }
            }
            if (bool) {
                BillPrice billPrice = new BillPrice();
                billPrice.setBillId(billId);
                BigDecimal ret = null;
                ret = new BigDecimal((String) price[0]);
                billPrice.setPrice(ret);
                billPrice.setBillRankingStandard(Long.parseLong(rankend[0]));
                if (user.hasRole("ASSISTANT")) {
                    billPrice.setInMemberId(user.getCreateUserId());
                } else {
                    billPrice.setInMemberId(user.getId());
                }
                ;
                String[] updateUserId = params.get("selectContent[" + i + "][updateUserId]");
                billPrice.setOutMemberId(Long.parseLong(updateUserId[0]));
                billPrice.setCreateTime(new Date());
                billPriceMapper.insert(billPrice);
                Bill bill1 = new Bill();
                bill1.setId(billId);
                bill1.setUpdateUserId(user.getId());
                bill1.setState(1);
                billMapper.updateByPrimaryKeySelective(bill1);
                if (!"NaN".equals(price1[0])) {
                    String[] id1 = params.get("selectContent[" + i + "][id]");
                    String[] rankend1 = params.get("rankend1");
                    Long billId1 = Long.parseLong(id1[0]);
                    BillPrice billPrice1 = new BillPrice();
                    billPrice1.setBillId(billId1);
                    BigDecimal ret1 = null;
                    ret1 = new BigDecimal((String) price1[0]);
                    billPrice1.setPrice(ret1);
                    billPrice1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                    if (user.hasRole("ASSISTANT")) {
                        billPrice1.setInMemberId(user.getCreateUserId());
                    } else {
                        billPrice1.setInMemberId(user.getId());
                    }

                    String[] updateUserId1 = params.get("selectContent[" + i + "][updateUserId]");
                    billPrice1.setOutMemberId(Long.parseLong(updateUserId1[0]));
                    billPrice1.setCreateTime(new Date());
                    billPriceMapper.insert(billPrice1);
                    if (!"NaN".equals(price2[0])) {
                        String[] id2 = params.get("selectContent[" + i + "][id]");
                        String[] rankend2 = params.get("rankend2");
                        Long billId2 = Long.parseLong(id2[0]);
                        BillPrice billPrice2 = new BillPrice();
                        billPrice2.setBillId(billId2);
                        BigDecimal ret2 = null;
                        ret2 = new BigDecimal((String) price2[0]);
                        billPrice2.setPrice(ret2);
                        billPrice2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                        if (user.hasRole("ASSISTANT")) {
                            billPrice2.setInMemberId(user.getCreateUserId());
                        } else {
                            billPrice2.setInMemberId(user.getId());
                        }

                        String[] updateUserId2 = params.get("selectContent[" + i + "][updateUserId]");
                        billPrice2.setOutMemberId(Long.parseLong(updateUserId2[0]));
                        billPrice2.setCreateTime(new Date());
                        billPriceMapper.insert(billPrice2);
                        if (!"NaN".equals(price3[0])) {
                            String[] id3 = params.get("selectContent[" + i + "][id]");
                            String[] rankend3 = params.get("rankend3");
                            Long billId3 = Long.parseLong(id3[0]);
                            BillPrice billPrice3 = new BillPrice();
                            billPrice3.setBillId(billId3);
                            BigDecimal ret3 = null;
                            ret3 = new BigDecimal((String) price3[0]);
                            billPrice3.setPrice(ret3);
                            billPrice3.setBillRankingStandard(Long.parseLong(rankend3[0]));
                            if (user.hasRole("ASSISTANT")) {
                                billPrice3.setInMemberId(user.getCreateUserId());
                            } else {
                                billPrice3.setInMemberId(user.getId());
                            }

                            String[] updateUserId3 = params.get("selectContent[" + i + "][updateUserId]");
                            billPrice3.setOutMemberId(Long.parseLong(updateUserId3[0]));
                            billPrice3.setCreateTime(new Date());
                            billPriceMapper.insert(billPrice3);
                        }
                    }
                }
            } else {
                return 1;
            }


        }
        return 0;
    }

    /**
     * 优化方录入价格
     *
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

        int length = Integer.parseInt(checkboxLength[0]);
        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            String[] price = params.get("price");
            String[] rankend = params.get("rankend");
            Long billId = Long.parseLong(id[0]);
            //判断渠道商订单价格是否已经存在(有BUG 如果管理员录入价格 bool会变成true)
            List<BillPrice> billPriceList = billPriceMapper.selectByBillId(billId);
            Boolean bool = true;
            //如果有了 就修改
            if (billPriceList.size() > 0) {
                for (BillPrice billPrice1 : billPriceList
                        ) {
                    if (billPrice1.getInMemberId().longValue() == user.getId().longValue()) {
                        bool = false;
                    }
                }
            }
            if (bool) {
                CustomerRankingParam customerRankingParam = new CustomerRankingParam();
                Bill billA = billMapper.selectByPrimaryKey(billId);
                BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(billId);
                int ApiId;
                String wAction = "AddSearchTask";
                String keyword = billA.getKeywords();
                keyword = keyword.replaceAll("[\\u00A0]+", "");
                String[] qkeyword = {keyword};

                String[] qurl = {billA.getWebsite()};
                //组合参数
                int[] timeSet = {8, 15};
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("keyword", qkeyword);
                jsonObj.put("url", qurl);
                jsonObj.put("time", System.currentTimeMillis());
                jsonObj.put("timeSet", timeSet);
                jsonObj.put("userId", Define.userId);
                jsonObj.put("businessType", 2006);
                //判断搜索引擎
                switch (billSearchSupport.getSearchSupport()) {
                    case "百度":
                        jsonObj.put("searchType", 1010);
                        break;
                    case "360":
                        jsonObj.put("searchType", 1015);
                        break;
                    case "搜狗":
                        jsonObj.put("searchType", 1030);
                        break;
                    case "手机百度":
                        jsonObj.put("searchType", 7010);
                        break;
                    case "手机360":
                        jsonObj.put("searchType", 7015);
                        break;
                    case "手机搜狗":
                        jsonObj.put("searchType", 7030);
                        break;
                    case "神马":
                        jsonObj.put("searchType", 7070);
                        break;
                }

                jsonObj.put("searchOnce", true);
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
                int agid = OpDefine.agid;
                String action = "importkw";
                String md5Key = OpDefine.md5Key;

                //取现在时间
                String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                //填充实体类
                List<Yby> ybyList = new ArrayList<Yby>();
                //加密sign
                Yby yby = new Yby();
                yby.setKw(billA.getKeywords().replaceAll("[\\u00A0]+", ""));
                yby.setUrl(billA.getWebsite());
                //判断搜索引擎
                switch (billSearchSupport.getSearchSupport()) {
                    case "百度":
                        yby.setSe(1);
                        break;
                    case "360":
                        yby.setSe(2);
                        break;
                    case "搜狗":
                        yby.setSe(3);
                        break;
                    case "手机百度":
                        yby.setSe(4);
                        break;
                    case "手机360":
                        yby.setSe(5);
                        break;
                    case "手机搜狗":
                        yby.setSe(6);
                        break;
                    case "神马":
                        yby.setSe(7);
                        break;
                }
                yby.setMcpd(1);
                ybyList.add(yby);
                //组包
                String str = "";
                str = str.concat(JSON.toJSONString(dateString));
                str = str.concat(JSON.toJSONString(action));
                str = str.concat(JSON.toJSONString(ybyList));
                str = str.concat(JSON.toJSONString(md5Key));
                str = str.concat(JSON.toJSONString(agid));
                //加密sign
                String sign = null;
                try {
                    sign = md5_urlEncode.EncoderByMd51(str);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //创建JSON
                JSONObject jaction = new JSONObject();
                jaction.put("action", action);
                JSONObject jagid = new JSONObject();
                jagid.put("agid", agid);
                JSONObject jstamp = new JSONObject();
                jstamp.put("stamp", dateString);
                JSONObject jargs = new JSONObject();
                jargs.put("args", JSON.toJSONString(ybyList));
                JSONObject jsign = new JSONObject();
                jsign.put("sign", sign);
                //拼接字符串
                String str11 = "{" + jagid.toString().replace("{", "").replace("}", "") + "," + jstamp.toString().replace("{", "").replace("}", "") + "," +
                        jaction.toString().replace("{", "").replace("}", "") + "," + jsign.toString().replace("{", "").replace("}", "") + "," +
                        jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\", "").replace("{\"args\"", "\"args\"");

                //Base64  编码
                BASE64Encoder base64Encoder = new BASE64Encoder();
                String data = null;
                try {
                    data = base64Encoder.encode(str11.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("data", data);
                try {
                    //查排名返回对象（导入订单）
                    CustomerRankingResult customerRankingResult = remoteService.getCustomerRanking(customerRankingParam);
                    //调点击返回对象（导入订单）
                    CustomerOptimizationResult customerOptimizationResult = remoteService.getOptimizationApi(map);
                    //判断两个返回结果是否为空
                    if (customerRankingResult != null && customerOptimizationResult != null) {
                        //判断两个返回结果是否成功
                        JSONObject jsonObject = customerOptimizationResult.getArgs();
                        String code = jsonObject.get("code").toString();
                        if (customerRankingResult.getMessage().equals("success.") && ("success").equals(code)) {

                            JSONArray OptimizationValue = jsonObject.getJSONArray("value");
                            String message = OptimizationValue.getJSONArray(0).get(1).toString();
                            JSONArray value = customerRankingResult.getValue();
                            JSONArray valueJSONArray = value.getJSONArray(0);
                            ApiId = Integer.parseInt(valueJSONArray.get(0).toString());
                            int ApiId1 = Integer.parseInt(OptimizationValue.getJSONArray(0).get(0).toString());
                            //录入价格
                            BillPrice billPrice = new BillPrice();
                            billPrice.setBillId(billId);
                            BigDecimal ret = null;
                            ret = new BigDecimal((String) price[0]);
                            billPrice.setPrice(ret);
                            billPrice.setBillRankingStandard(Long.parseLong(rankend[0]));
                            billPrice.setInMemberId(user.getId());
                            ;
                            String[] updateUserId = params.get("selectContent[" + i + "][updateUserId]");
                            billPrice.setOutMemberId(Long.parseLong(updateUserId[0]));
                            billPrice.setCreateTime(new Date());
                            //修改订单
                            billPriceMapper.insert(billPrice);
                            Bill bill1 = new Bill();
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
                                String[] id1 = params.get("selectContent[" + i + "][id]");
                                String[] rankend1 = params.get("rankend1");
                                Long billId1 = Long.parseLong(id1[0]);
                                BillPrice billPrice1 = new BillPrice();
                                billPrice1.setBillId(billId1);
                                BigDecimal ret1 = null;
                                ret1 = new BigDecimal((String) price1[0]);
                                billPrice1.setPrice(ret1);
                                billPrice1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                                billPrice1.setInMemberId(user.getId());
                                String[] updateUserId1 = params.get("selectContent[" + i + "][updateUserId]");
                                billPrice1.setOutMemberId(Long.parseLong(updateUserId1[0]));
                                billPrice1.setCreateTime(new Date());
                                billPriceMapper.insert(billPrice1);
                                if (!"NaN".equals(price2[0])) {
                                    String[] id2 = params.get("selectContent[" + i + "][id]");
                                    String[] rankend2 = params.get("rankend2");
                                    Long billId2 = Long.parseLong(id2[0]);
                                    BillPrice billPrice2 = new BillPrice();
                                    billPrice2.setBillId(billId2);
                                    BigDecimal ret2 = null;
                                    ret2 = new BigDecimal((String) price2[0]);
                                    billPrice2.setPrice(ret2);
                                    billPrice2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                                    billPrice2.setInMemberId(user.getId());
                                    String[] updateUserId2 = params.get("selectContent[" + i + "][updateUserId]");
                                    billPrice2.setOutMemberId(Long.parseLong(updateUserId2[0]));
                                    billPrice2.setCreateTime(new Date());
                                    billPriceMapper.insert(billPrice2);
                                    if (!"NaN".equals(price3[0])) {
                                        String[] id3 = params.get("selectContent[" + i + "][id]");
                                        String[] rankend3 = params.get("rankend3");
                                        Long billId3 = Long.parseLong(id3[0]);
                                        BillPrice billPrice3 = new BillPrice();
                                        billPrice3.setBillId(billId3);
                                        BigDecimal ret3 = null;
                                        ret3 = new BigDecimal((String) price3[0]);
                                        billPrice3.setPrice(ret3);
                                        billPrice3.setBillRankingStandard(Long.parseLong(rankend3[0]));
                                        billPrice3.setInMemberId(user.getId());
                                        String[] updateUserId3 = params.get("selectContent[" + i + "][updateUserId]");
                                        billPrice3.setOutMemberId(Long.parseLong(updateUserId3[0]));
                                        billPrice3.setCreateTime(new Date());
                                        billPriceMapper.insert(billPrice3);
                                    }
                                }
                            }
                        } else {

                            return 1;
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    return 1;
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return 1;
                }


            }


        }
        return 0;
    }

    /**
     * 审核不通过
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int billNotExamine(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);
        for (int i = 0; i < length; i++) {
            //获取订单ID
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            //获取订单
            Bill bill = billMapper.selectByPrimaryKey(billId);
            //修改订单的操作员
            Bill newBill = new Bill();
            newBill.setId(billId);
            if (user.hasRole("SUPER_ADMIN")) {
                newBill.setState(-4);
            } else if (user.hasRole("DISTRIBUTOR") || user.hasRole("ASSISTANT")) {
                newBill.setState(-3);
            } else if (user.hasRole("AGENT")) {
                newBill.setState(-2);
            }
            billMapper.updateByPrimaryKeySelective(newBill);
        }

        return 0;
    }

    /**
     * 待审核订单预览页面（提交订单页面）删除
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int deleteBillPendingAuditView(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);

        for (int i = 0; i < length; i++) {
            //获取订单ID
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            //删除数据
            billPriceMapper.deleteByBillId(billId);
            billSearchSupportMapper.deleteByBillId(billId);
            billMapper.deleteByPrimaryKey(billId);
        }

        return 0;
    }

    /**
     * 订单列表
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getBillDetails(Map<String, Object> params, LoginUser user) {
        //判断查询条件
        //视图模型
        List<BillDetails> billDetailsList = new ArrayList<BillDetails>();

        int way = Integer.parseInt(params.get("way").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int offset = Integer.parseInt(params.get("offset").toString());
        int i = offset;
        //上游
        if (way == 1) {
            //先获取对应的订单
            if (params.get("searchUserNameId") != null) {
                User user1 = userMapper.selectByPrimaryKey(Long.parseLong(params.get("searchUserNameId").toString()));
                params.put("userId", user1.getId());
            } else {
                params.put("userId", user.getId());
            }
            //订单数
            List<Bill> billList = billMapper.selectByOutMemberId(params);

            Long total = billMapper.selectByOutMemberIdCount(params);


            for (Bill bill : billList
                    ) {
                i++;
                //调用方法  加入集合
                BillDetails billDetails = this.insertBillDetails(bill, user, way);
                billDetails.setdisplayId(i);
                billDetails.setLength(total);
                billDetails.setRoleName(user.getRoles().get(0));
                billDetailsList.add(billDetails);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("total", total);
            map.put("rows", billDetailsList);
            return map;
        }
        //下游
        else if (way == 2) {
            if (user.hasRole("SUPER_ADMIN") || user.hasRole("AGENT") || user.hasRole("ADMIN")) {
                if (user.hasRole("SUPER_ADMIN") || user.hasRole("AGENT")) {
                    params.put("userId", user.getId());
                } else {
                    params.put("userId", user.getCreateUserId());
                }

                //先获取对应的订单
                List<Bill> billList = billMapper.selectByInMemberId(params);
                Long total = billMapper.selectByInMemberIdCount(params);
                for (Bill bill : billList
                        ) {
                    i++;
                    //调用方法  加入集合
                    BillDetails billDetails = this.insertBillDetails(bill, user, way);
                    billDetails.setdisplayId(i);
                    billDetails.setLength(total);
                    billDetails.setRoleName(user.getRoles().get(0));
                    User billAscriptionName = userMapper.selectByPrimaryKey(bill.getBillAscription());
                    if (billAscriptionName != null) {
                        billDetails.setBillAscriptionName(billAscriptionName.getUserName());
                    }
                    billDetailsList.add(billDetails);

                }
                Map<String, Object> map = new HashMap<>();
                map.put("total", total);
                map.put("rows", billDetailsList);
                return map;
            } else if (user.hasRole("DISTRIBUTOR")) {
                //先获取对应的订单
                Role role1 = roleMapper.selectByRoleCode("AGENT");
                params.put("roleId", role1.getId());
                params.put("userId", user.getId());
                List<Bill> billList = billMapper.selectByInMemberId(params);
                Long total = billMapper.selectByInMemberIdCount(params);
                for (Bill bill : billList
                        ) {

                    //调用方法  加入集合
                    BillPrice billPrice = new BillPrice();
                    billPrice.setBillId(bill.getId());
                    billPrice.setInMemberId(user.getId());
                    Long OutMemberId = billPriceMapper.selectByBillPriceOutMemberId(billPrice);
                    UserRole userRole = userRoleMapper.selectByUserId(OutMemberId);
                    if (userRole != null) {
                        Role role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
                        if (role != null) {
                            //查询渠道商的付款方是不是代理商
                            if (role.getRoleCode().equals("AGENT")) {

                                i++;
                                BillDetails billDetails = this.insertBillDetails(bill, user, way);
                                billDetails.setdisplayId(i);
                                billDetails.setLength(total);
                                billDetails.setRoleName(user.getRoles().get(0));
                                billDetailsList.add(billDetails);
                            } else {
                                total--;
                            }
                        }
                    }

                }


                Map<String, Object> map = new HashMap<>();
                map.put("total", total);
                map.put("rows", billDetailsList);
                return map;
            } else//操作员
            {
                //先获取对应的订单
                params.put("userId", user.getCreateUserId());
                params.put("billAscription", user.getId());
                List<Bill> billList = billMapper.selectByInMemberId(params);
                Long total = billMapper.getBillListCount(params);
                for (Bill bill : billList
                        ) {
                    i++;
                    //调用方法  加入集合
                    BillDetails billDetails = this.insertBillDetails(bill, user, way);
                    billDetails.setdisplayId(i);
                    billDetails.setLength(total);
                    billDetails.setRoleName(user.getRoles().get(0));
                    billDetailsList.add(billDetails);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("total", total);
                map.put("rows", billDetailsList);
                return map;
            }

        } else {

            //渠道商直属客户，先获取对应的订单
            Role role = roleMapper.selectByRoleCode("CUSTOMER");
            params.put("roleId", role.getId());
            if (user.hasRole("ASSISTANT")) {
                params.put("userId", user.getCreateUserId());
            } else {
                params.put("userId", user.getId());
            }

            List<Bill> billList = billMapper.selectByCustomerId(params);
            Long total = billMapper.selectByCustomerIdCount(params);
            for (Bill bill : billList
                    ) {
                i++;
                BillDetails billDetails = this.insertBillDetails(bill, user, way);
                billDetails.setdisplayId(i);
                billDetails.setLength(total);
                billDetails.setRoleName(user.getRoles().get(0));
                billDetailsList.add(billDetails);


            }
            Map<String, Object> map = new HashMap<>();
            map.put("total", total);
            map.put("rows", billDetailsList);
            return map;
        }


    }

    /**
     * 插入视图模型数据 封装成方法
     *
     * @param bill
     * @return
     */
    public BillDetails insertBillDetails(Bill bill, LoginUser user, int way) {
        BillPrice billPrice = new BillPrice();
        List<BillPrice> billPriceList = new ArrayList<>();
        //上游
        if (way == 1) {
            billPrice.setBillId(bill.getId());
            billPrice.setOutMemberId(user.getId());
            //查出对应单价
            billPriceList = billPriceMapper.selectByBillPrice(billPrice);
        }
        //下游
        else {
            if (user.hasRole("COMMISSIONER")) {
                billPrice.setBillId(bill.getId());
                billPrice.setInMemberId(user.getCreateUserId());
                //查出对应单价
                billPriceList = billPriceMapper.selectByBillPrice(billPrice);
            } else {
                billPrice.setBillId(bill.getId());
                if (user.hasRole("ASSISTANT") || user.hasRole("ADMIN")) {
                    billPrice.setInMemberId(user.getCreateUserId());
                } else {
                    billPrice.setInMemberId(user.getId());
                }
                //查出对应单价
                billPriceList = billPriceMapper.selectByBillPrice(billPrice);
            }

        }
        //查出搜索引擎
        BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(bill.getId());
        BillDetails billDetails = new BillDetails();
        billDetails.setId(bill.getId());
        if (way == 1) {
            if (user.hasRole("CUSTOMER")) {
                BillPrice billPrice1 = new BillPrice();
                billPrice1.setBillId(bill.getId());
                billPrice1.setOutMemberId(user.getId());
                List<BillPrice> billPriceList1 = billPriceMapper.selectByBillPrice(billPrice1);
                User user2 = userMapper.selectByPrimaryKey(billPriceList1.get(0).getOutMemberId());
                billDetails.setUserName(user2.getUserName());
            } else {

                BillPrice billPrice1 = new BillPrice();
                billPrice1.setBillId(bill.getId());
                if (user.hasRole("ASSISTANT") || user.hasRole("ADMIN")) {
                    billPrice1.setInMemberId(user.getCreateUserId());
                } else {
                    billPrice1.setInMemberId(user.getId());
                }

                List<BillPrice> billPriceList1 = billPriceMapper.selectByBillPrice(billPrice1);
                User user2 = userMapper.selectByPrimaryKey(billPriceList1.get(0).getOutMemberId());
                billDetails.setUserName(user2.getUserName());
            }
        } else {
            User user1 = userMapper.selectByPrimaryKey(billPriceList.get(0).getOutMemberId());
            billDetails.setUserName(user1.getUserName());
        }
        //插入数据
        billDetails.setKeywords(bill.getKeywords());
        billDetails.setWebsite(bill.getWebsite());
        billDetails.setSearchName(billSearchSupport.getSearchSupport());
        billDetails.setCreateTime(DateUtils.formatDate(bill.getCreateTime()));
        billDetails.setFirstRanking(bill.getFirstRanking());
        billDetails.setNewRanking(bill.getNewRanking());
        billDetails.setChangeRanking(bill.getChangeRanking());
        billDetails.setBillPriceList(billPriceList);
        billDetails.setDayOptimization(bill.getDayOptimization());
        billDetails.setAllOptimization(bill.getAllOptimization());
        billDetails.setApplyState(bill.getApplyState());

        //判断当前是否达标  当天消费
        BillPrice billPrice1 = new BillPrice();
        billPrice1.setBillId(bill.getId());

        if (way == 1) {
            billPrice1.setOutMemberId(user.getId());
        } else {

            if (user.hasRole("COMMISSIONER") || user.hasRole("ASSISTANT") || user.hasRole("ADMIN")) {
                billPrice1.setInMemberId(user.getCreateUserId());
            } else {
                billPrice1.setInMemberId(user.getId());
            }

        }
        //判断今日消费
        List<BillPrice> billPrice2 = billPriceMapper.selectByBillPrice(billPrice1);
        if (billPrice2.size() > 0) {
            for (BillPrice item : billPrice2
                    ) {
                if (item.getBillRankingStandard() >= bill.getNewRanking()) {
                    billDetails.setDayConsumption(item.getPrice());

                    break;
                }
            }
        }
        //统计本月消费
        Calendar now = Calendar.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("year", now.get(Calendar.YEAR));
        map.put("month", now.get(Calendar.MONTH) + 1);
        map.put("billId", bill.getId());
        if (way == 1) {
            map.put("userId", user.getCreateUserId());
        } else {
            if (user.hasRole("COMMISSIONER") || user.hasRole("ASSISTANT") || user.hasRole("ADMIN")) {
                map.put("userId", user.getCreateUserId());
            } else {
                map.put("userId", user.getId());
            }

        }
        //查询本月消费
        Double sum = billCostMapper.selectByPriceSum(map);
        if (sum != null) {
            billDetails.setMonthConsumption(sum);
        }
        billDetails.setStandardDays(bill.getStandardDays());
        billDetails.setState(bill.getState());
        billDetails.setOpstate(bill.getOpstate());
        return billDetails;
    }

    /**
     * 优化调整
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int OptimizationUpdate(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);
        String[] num = params.get("num");
        int nums = Integer.parseInt(num[0]);
        for (int i = 0; i < length; i++) {
            //获取订单ID
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            //获取订单
            Bill bill = billMapper.selectByPrimaryKey(billId);
            //组包

            //调整点击录入订单
            int agid = OpDefine.agid;
            String action = "setmcpd";
            String md5Key = OpDefine.md5Key;

            //取现在时间
            String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            //订单优化量
            //加密sign
            int webApiId = bill.getWebAppId1();
            int[] aa = new int[]{webApiId, nums};
            JSONArray jsonArray = new JSONArray(aa);


            String arr = "[\"kw\"," + jsonArray.toString() + "]";
            //组包
            String str = "";
            str = str.concat(JSON.toJSONString(dateString));
            str = str.concat(JSON.toJSONString(action));
            str = str.concat(arr);
            str = str.concat(JSON.toJSONString(md5Key));
            str = str.concat(JSON.toJSONString(agid));
            //加密sign
            String sign = null;
            try {
                sign = md5_urlEncode.EncoderByMd51(str);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //创建JSON
            JSONObject jaction = new JSONObject();
            jaction.put("action", action);
            JSONObject jagid = new JSONObject();
            jagid.put("agid", agid);
            JSONObject jstamp = new JSONObject();
            jstamp.put("stamp", dateString);
            JSONObject jargs = new JSONObject();
            jargs.put("args", arr);
            JSONObject jsign = new JSONObject();
            jsign.put("sign", sign);
            //拼接字符串
            String str11 = "{" + jagid.toString().replace("{", "").replace("}", "") + "," + jstamp.toString().replace("{", "").replace("}", "") + "," +
                    jaction.toString().replace("{", "").replace("}", "") + "," + jsign.toString().replace("{", "").replace("}", "") + "," +
                    jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\", "").replace("{\"args\"", "\"args\"");
            //Base64  编码
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String data = null;
            try {
                data = base64Encoder.encode(str11.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("data", data);
            //调点击返回对象（修改点击）
            CustomerOptimizationResult customerOptimizationResult = remoteService.getOptimizationApi(map);
            if (customerOptimizationResult != null) {
                //判断返回结果是否成功
                JSONObject jsonObject = customerOptimizationResult.getArgs();
                String code = jsonObject.get("code").toString();
                if ("success".equals(code)) {
                    bill.setDayOptimization(nums);
                    billMapper.updateByPrimaryKeySelective(bill);
                }
            }
        }
        return 0;
    }

    /**
     * 切换操作员
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int billChangeCmt(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        String[] caozuoyuan = params.get("caozuoyuan1");
        int length = Integer.parseInt(checkboxLength[0]);
        String userName = caozuoyuan[0];
        for (int i = 0; i < length; i++) {
            //获取订单ID
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            //获取订单
            Bill bill = billMapper.selectByPrimaryKey(billId);
            //获取操作员ID
            User user1 = userMapper.selectByUserName(userName);

            //修改订单的操作员
            Bill newBill = new Bill();
            newBill.setId(billId);
            newBill.setBillAscription(user1.getId());
            billMapper.updateByPrimaryKeySelective(newBill);
        }

        return 0;
    }

    /**
     * 切换客户
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int billChangeToKeHucmt(Map<String, String[]> params, LoginUser loginUser) {
        String[] checkboxLength = params.get("length");
        String[] kehu = params.get("kehu");
        int length = Integer.parseInt(checkboxLength[0]);
        for (int i = 0; i < length; i++) {
            //获取订单ID
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            BillPrice billPrice = new BillPrice();
            billPrice.setBillId(billId);
            billPrice.setInMemberId(loginUser.getId());
            List<BillPrice> billPriceList = billPriceMapper.selectByBillPrice(billPrice);
            if (!CollectionUtils.isEmpty(billPriceList)) {
                for (BillPrice item : billPriceList
                        ) {
                    item.setOutMemberId(new Long(kehu[0]));
                    billPriceMapper.updateByPrimaryKeySelective(item);
                }
            }

        }

        return 0;
    }

    /**
     * 优化状态调整
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int updateYBYstate(Map<String, String[]> params, LoginUser user) {
        //任务个数
        String[] checkboxLength = params.get("length");
        //任务状态（1：在线  100：离线  999：删除）
        String[] stateLength = params.get("state");
        if (checkboxLength.length > 0 && stateLength.length > 0) {
            int state = Integer.parseInt(stateLength[0]);
            int length = Integer.parseInt(checkboxLength[0]);
            if (state != 0 && length != 0) {
                JSONArray jsonArray = new JSONArray();
                String jsonStr = "";
                for (int i = 0; i < length; i++) {
                    //获取订单ID
                    String[] id = params.get("selectContent[" + i + "][id]");
                    Long billId = Long.parseLong(id[0]);
                    //获取订单
                    Bill bill = billMapper.selectByPrimaryKey(billId);
                    //订单优化量
                    //加密sign
                    int webApiId = bill.getWebAppId1();
                    int[] intarr = new int[]{webApiId, state};
                    JSONArray json = new JSONArray(intarr);
                    jsonStr += json.toString() + ",";

                }
                String arr = "[\"kw\"," + jsonStr.substring(0, jsonStr.length() - 1) + "]";
                //组包
                //调整点击录入订单
                int agid = OpDefine.agid;
                String action = "setstatus";
                String md5Key = OpDefine.md5Key;

                //取现在时间
                String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                //组包
                String str = "";
                str = str.concat(JSON.toJSONString(dateString));
                str = str.concat(JSON.toJSONString(action));
                str = str.concat(arr);
                str = str.concat(JSON.toJSONString(md5Key));
                str = str.concat(JSON.toJSONString(agid));
                //加密sign
                String sign = null;
                try {
                    sign = md5_urlEncode.EncoderByMd51(str);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //创建JSON
                JSONObject jaction = new JSONObject();
                jaction.put("action", action);
                JSONObject jagid = new JSONObject();
                jagid.put("agid", agid);
                JSONObject jstamp = new JSONObject();
                jstamp.put("stamp", dateString);
                JSONObject jargs = new JSONObject();
                jargs.put("args", arr);
                JSONObject jsign = new JSONObject();
                jsign.put("sign", sign);
                //拼接字符串
                String str11 = "{" + jagid.toString().replace("{", "").replace("}", "") + "," + jstamp.toString().replace("{", "").replace("}", "") + "," +
                        jaction.toString().replace("{", "").replace("}", "") + "," + jsign.toString().replace("{", "").replace("}", "") + "," +
                        jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\", "").replace("{\"args\"", "\"args\"");
                //Base64  编码
                BASE64Encoder base64Encoder = new BASE64Encoder();
                String data = null;
                try {
                    data = base64Encoder.encode(str11.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("data", data);
                //调点击返回对象（停止优化）
                CustomerOptimizationResult customerOptimizationResult = remoteService.getOptimizationApi(map);
                if (customerOptimizationResult != null) {
                    //判断返回结果是否成功
                    JSONObject jsonObject = customerOptimizationResult.getArgs();
                    String code = jsonObject.get("code").toString();
                    if ("success".equals(code)) {
                        for (int i = 0; i < length; i++) {
                            //获取订单ID
                            String[] id = params.get("selectContent[" + i + "][id]");
                            Long billId = Long.parseLong(id[0]);
                            //获取订单
                            Bill bill = billMapper.selectByPrimaryKey(billId);
                            //优化离线
                            if (state == 1) {
                                bill.setOpstate(1);
                            } else if (state == 100) {
                                bill.setOpstate(2);
                            }

                            billMapper.updateByPrimaryKeySelective(bill);


                        }

                    } else {
                        return 2;
                    }
                }

            } else {
                return 0;
            }
        }

        return 1;
    }

    /**
     * 合作停(订单主状态)
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int optimizationStop(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);
        Map<String, String[]> map = new HashMap<>(params);
        String[] taskIdArr = new String[length];
        String[] state = {"999"};
        map.put("state", state);
        Long[] billIdArr = new Long[length];
        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            Bill bill = billMapper.selectByPrimaryKey(billId);
            if (bill != null) {
                //通过 Taskid 查询订单
                List<Bill> billList = billMapper.selectByBillIdToGetTaskId(bill.getWebAppId());
                if (!CollectionUtils.isEmpty(billList)) {
                    //如果有多个重复订单 只删除当前数据库的数据
                    if (billList.size() > 1) {
                        //判断其他重复的订单是否已经停单
                        Boolean bool=false;
                        for (Bill b:billList
                             ) {
                            if(b.getState()==2&&!b.getId().equals(billId))
                            {
                                bool=true;
                                break;
                            }
                        }
                        if(bool)
                        {
                            bill.setState(3);
                            billMapper.updateByPrimaryKeySelective(bill);
                        }
                        else
                        {
                            //删除扣费通道
                            bill.setState(3);
                            billMapper.updateByPrimaryKeySelective(bill);
                            taskIdArr[i] = billList.get(0).getWebAppId().toString();
                            billIdArr[i] = billList.get(0).getId();
                        }

                    }
                    //调用接口  删除对应的数据
                    else {
                        //删除扣费通道
                        taskIdArr[i] = billList.get(0).getWebAppId().toString();
                        billIdArr[i] = billList.get(0).getId();
                        bill.setState(3);
                        billMapper.updateByPrimaryKeySelective(bill);
                    }
                }
            }
        }
        //调用接口删除接口数据
        if(billIdArr.length>0)
        {
            int a = this.deleteYBYState(billIdArr, user, 999);
            Boolean b = this.delSearchTask(taskIdArr);
        }

        return 1;

    }

    /**
     * 优化启动(订单主状态)
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int optimizationStart(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);
        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            Bill bill = billMapper.selectByPrimaryKey(billId);
            if (bill != null) {
                //通过 Taskid 查询订单
                List<Bill> billList = billMapper.selectByBillIdToGetTaskId(bill.getWebAppId());
                if (!CollectionUtils.isEmpty(billList)) {
                    //如果有多个重复订单
                    if (billList.size() > 1) {
                        //更改
                        Boolean bool=true;
                        for (Bill item : billList
                                ) {
                            if (item.getState()==2&&!item.getId().equals(billId)) {
                                bill.setWebAppId(item.getWebAppId());
                                bill.setWebAppId1(item.getWebAppId1());
                                bill.setState(2);
                                billMapper.updateByPrimaryKeySelective(bill);
                               bool=false;
                               break;
                            }
                        }
                        if(bool)
                        {
                            int result2 = addSearchTask(billId, bill.getKeywords(), bill.getWebsite());
                        }

                    } else {
                        int result2 = addSearchTask(billId, bill.getKeywords(), bill.getWebsite());
                        if(result2!=1)
                        {
                            bill.setState(2);
                            billMapper.updateByPrimaryKeySelective(bill);
                            return 1;
                        }

                    }
                }

            }


        }
        return 0;
    }

    /**
     * 删除订单（错误的)
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int deleteBill(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);
        Map<String, String[]> map = new HashMap<>(params);
        String[] taskIdArr = new String[length];
        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            //获取当前订单的TASKID
            Bill bill = billMapper.selectByPrimaryKey(Long.parseLong(id[0]));
            if (bill != null) {
                //通过 TASKid 查询订单
                List<Bill> billList = billMapper.selectByBillIdToGetTaskId(bill.getWebAppId());
                if (!CollectionUtils.isEmpty(billList)) {
                    //如果有多个重复订单 只删除当前数据库的数据
                    if (billList.size() > 1) {
                        bill.setDeleteState(1);
                        billMapper.updateByPrimaryKeySelective(bill);
                    }
                    //调用接口  删除对应的数据
                    else {
                        bill.setDeleteState(1);
                        bill.setWebAppId(0);
                        bill.setWebAppId1(0);
                        billMapper.updateByPrimaryKeySelective(bill);
                        //删除扣费通道
                        String[] state = {"999"};
                        map.put("state", state);
                        this.updateYBYstate(map, user);
                        taskIdArr[i] = billList.get(0).getWebAppId().toString();
                    }
                }

            }


        }
        delSearchTask(taskIdArr);
        //判断删除

        return 0;
    }

    /**
     * 获取价格
     *
     * @param limit
     * @param offset
     * @param billId
     * @param user
     * @param way
     * @return
     */
    @Override
    public Map<String, Object> getPriceDetails(int limit, int offset, String billId, LoginUser user, String way) {

        Map<String, Object> params = new HashMap<>();
        List<BillCostDetails> billCostDetailsList = new ArrayList<>();

        if (way.equals("1"))//上游
        {
            Map<String, Object> map = new HashMap<>();
            map.put("limit", limit);
            map.put("offset", offset);
            map.put("billId", billId);
            map.put("userId", user.getCreateUserId());
            map.put("offset", offset);
            List<BillCost> billCostList = billCostMapper.getPriceByMap(map);
            Long total = billCostMapper.getPriceByMapCount(map);
            for (BillCost item : billCostList
                    ) {
                BillCostDetails billCostDetails = new BillCostDetails();
                billCostDetails.setId(item.getId());
                billCostDetails.setCostAmount(item.getCostAmount());
                billCostDetails.setRanking(item.getRanking());
                billCostDetails.setCostDate(DateUtils.formatDate(item.getCostDate()));
                billCostDetailsList.add(billCostDetails);
            }
            params.put("rows", billCostDetailsList);
            params.put("total", total);
            return params;
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("billId", billId);
            map.put("limit", limit);
            map.put("offset", offset);
            if (user.hasRole("SUPER_ADMIN") || user.hasRole("DISTRIBUTOR") || user.hasRole("AGENT")) {
                map.put("userId", user.getId());
                List<BillCost> billCostList = billCostMapper.getPriceByMap(map);
                Long total = billCostMapper.getPriceByMapCount(map);
                for (BillCost item : billCostList
                        ) {
                    BillCostDetails billCostDetails = new BillCostDetails();
                    billCostDetails.setId(item.getId());
                    billCostDetails.setCostAmount(item.getCostAmount());
                    billCostDetails.setRanking(item.getRanking());
                    billCostDetails.setCostDate(DateUtils.formatDate(item.getCostDate()));
                    billCostDetailsList.add(billCostDetails);
                }
                params.put("rows", billCostDetailsList);
                params.put("total", total);
                return params;
            } else {
                map.put("userId", user.getCreateUserId());
                List<BillCost> billCostList = billCostMapper.getPriceByMap(map);
                Long total = billCostMapper.getPriceByMapCount(map);
                for (BillCost item : billCostList
                        ) {
                    BillCostDetails billCostDetails = new BillCostDetails();
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
     * 订单反馈
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> billFeedback(String website) {
        Map<String, Object> param = new HashMap<>();
        param.put("website", website);
        List<Bill> billList = billMapper.selectAllSelective(param);
        if (billList.size() > 0) {
            List<String> searchList = new ArrayList<>();
            List<String> keywordList = new ArrayList<>();
            for (Bill item : billList
                    ) {
                BillSearchSupport billSearchSupport = billSearchSupportMapper.selectByBillId(item.getId());
                if (searchList.size() > 0) {
                    for (String str : searchList
                            ) {
                        if (!billSearchSupport.getSearchSupport().equals(str)) {
                            searchList.add(billSearchSupport.getSearchSupport());
                        }
                    }
                } else {
                    searchList.add(billSearchSupport.getSearchSupport());
                }
                keywordList.add(item.getKeywords());
            }

            ;

        }
        return null;
    }

    /**
     * 申请停单
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int applyStopBillConfirm(Map<String, String[]> params, LoginUser loginUser) {

        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);

        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            Bill bill = new Bill();
            bill.setId(billId);

            if (loginUser.hasRole("AGENT")) {
                bill.setApplyState(1);
            }

            //如果是渠道商 停单状态直接为2
            if (loginUser.hasRole("DISTRIBUTOR")) {
                bill.setApplyState(2);
            }


            billMapper.updateByPrimaryKeySelective(bill);

        }

        return 0;
    }

    /**
     * 审核通过
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int applyStopBillPass(Map<String, String[]> params, LoginUser user) {
        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);

        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            Bill bill = new Bill();
            bill.setId(billId);
            if (user.hasRole("SUPER_ADMIN")) {
                bill.setState(3);
                bill.setApplyState(0);
            } else {
                bill.setApplyState(2);
            }


            billMapper.updateByPrimaryKeySelective(bill);

        }

        return 0;
    }

    /**
     * 挺单申请不通过
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int applyStopBillNotPass(Map<String, String[]> params, LoginUser user) {

        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);

        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            Bill bill = new Bill();
            bill.setId(billId);
            bill.setApplyState(0);
            billMapper.updateByPrimaryKeySelective(bill);

        }

        return 0;

    }

    /**
     * 申请优化
     *
     * @param params
     * @param loginUser
     * @return
     */
    @Override
    public int applyToOptimization(Map<String, String[]> params, LoginUser loginUser) {

        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);

        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            Bill bill = new Bill();
            bill.setId(billId);
            if (loginUser.hasRole("AGENT")) {
                bill.setApplyState(-1);
            }

            //如果是渠道商 停单状态直接为2
            if (loginUser.hasRole("DISTRIBUTOR")) {
                bill.setApplyState(-2);
            }
            billMapper.updateByPrimaryKeySelective(bill);

        }

        return 0;

    }

    /**
     * 申请优化的订单通过审核
     *
     * @param request
     * @return
     */
    @Override
    public int billApplyToOptimizationPass(Map<String, String[]> params, LoginUser loginUser) {


        String[] checkboxLength = params.get("length");
        int length = Integer.parseInt(checkboxLength[0]);

        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            Long billId = Long.parseLong(id[0]);
            Bill bill = new Bill();
            bill.setId(billId);
            if (loginUser.hasRole("SUPER_ADMIN")) {
                bill.setStandardDays(0);
                bill.setState(2);
                bill.setCreateTime(new Date());
                bill.setApplyState(0);
            } else {
                bill.setApplyState(-2);
            }


            billMapper.updateByPrimaryKeySelective(bill);

        }
        return 0;
    }

    /**
     * 用户结算页面
     *
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> billOptimizationSettlement(LoginUser loginUser) {
        //返回对象
        Map<String, Object> map = new HashMap<>();
        //查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("userId", loginUser.getCreateUserId());
        params.put("outUserId", loginUser.getId());
        //本月消费
        Double MonthConsumption = MonthConsumption(params);
        if (MonthConsumption != null) {
            map.put("MonthConsumption", MonthConsumption);
        } else {
            map.put("MonthConsumption", "0.00");
        }
        //本日消费
        Double DayConsumption = DayConsumption(params);
        if (DayConsumption != null) {
            map.put("DayConsumption", DayConsumption);
        } else {
            map.put("DayConsumption", "0.00");
        }
        //昨日消费
        Double yesterDayConsumption = yesterDayConsumption(params);
        if (yesterDayConsumption != null) {
            map.put("yesterDayConsumption", yesterDayConsumption);
        } else {
            map.put("yesterDayConsumption", "0.00");
        }
        //获取上一个月
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        //转换时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String daynow = formatter.format(new Date());
        //当前时间
        Date dateNow = null;
        try {
            dateNow = format1.parse(daynow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        //上一个月的日期
        Date preMonth = null;
        try {
            preMonth = format1.parse(formatter.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //上一个月的天数
        int monthPreCount = 0;
        monthPreCount = getDaysOfMonth(preMonth);
        //上个月消费
        String seriesLastMonthSum = "";
        //循环获取上个月每天的达标数
        Map<String, Object> dateMap = new HashMap<>();
        dateMap.put("userId", loginUser.getCreateUserId());
        dateMap.put("outMemberId", loginUser.getId());
        //判断Y轴任务数的显示
        int MaxYbylast = 0;
        //判断Y轴消费的显示
        double MaxYbylastCost = 0;
        //某个月的第一天和最后一天
        Map<String, String> maps = getFirstday_Lastday_Month(dateNow);
        String fistDay = maps.get("first");
        //将第一日转换为Date格式
        Date fistDate = null;
        try {
            fistDate = format1.parse(fistDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < monthPreCount; i++) {
            calendar.setTime(fistDate);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            Date tomorrow = calendar.getTime();
            String str = formatter.format(tomorrow);
            dateMap.put("date", str);
            Double keywordsSum = billCostMapper.selectByBillCostOfDaySum(dateMap);

            //比较消费最大数
            if (MaxYbylastCost <= keywordsSum) {
                MaxYbylastCost = keywordsSum;
            }
            seriesLastMonthSum += keywordsSum + ",";

        }
        //上个月的达标数
        //下一个月的日期

        map.put("seriesLastMonthSum", seriesLastMonthSum);

        //判断Y轴的显示
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateNow); // 设置为当前时间
        calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) + 1); // 设置为下一个月
        //下一个月的日期
        Date nextMonth = null;
        try {
            nextMonth = format1.parse(formatter.format(calendar1.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, String> mapss = getFirstday_Lastday_Month(nextMonth);
        String fistDaysNow = mapss.get("first");
        //将第一日转换为Date格式
        Date fistDateNow = null;
        try {
            fistDateNow = format1.parse(fistDaysNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int MaxYbyNew = 0;
        double MaxYbyNewCost = 0;
        String seriesNowMonthSum = "";
        int monthNowCount = calendar1.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < monthNowCount; i++) {
            calendar.setTime(fistDateNow);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            Date tomorrow = calendar.getTime();
            String str1 = formatter.format(tomorrow);
            dateMap.put("date", str1);
            Double keywordsSum = billCostMapper.selectByBillCostOfDaySum(dateMap);

            if (MaxYbyNewCost <= keywordsSum) {
                MaxYbyNewCost = keywordsSum;
            }
            seriesNowMonthSum += keywordsSum + ",";

        }


        String yAxisSum = "";
        if (MaxYbyNewCost != 0) {
            yAxisSum = getYAxisSum(MaxYbyNewCost);
        } else {
            yAxisSum = getYAxisSum(MaxYbylastCost);
        }

        map.put("yAxisSum", yAxisSum);

        map.put("seriesNowMonthSum", seriesNowMonthSum);
        return map;
    }

    /**
     * 用户结算页面（用户余额）
     *
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> userBalance(LoginUser loginUser) {
        Map<String, Object> map = new HashMap<>();
        FundAccount fundAccount = fundAccountMapper.selectByUserId(loginUser.getId());
        if (fundAccount != null) {
            map.put("userBalance", fundAccount.getBalance());
        } else {
            map.put("userBalance", 0);
        }
        return map;
    }

    /**
     * 用户结算页面（年度消费）
     *
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> yearConsumption(LoginUser loginUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("outUserId", loginUser.getId());
        Double yearSum = YearConsumption(params);
        params.put("yearSum", yearSum);
        return params;
    }

    /**
     * 上月消费
     *
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> lastMonthConsumption(LoginUser loginUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("outUserId", loginUser.getId());
        Double lastMonthSum = lastMConsumption(params);
        params.put("lastMonthSum", lastMonthSum);
        return params;
    }

    @Override
    public Map<String, Object> billClientSideSettlementTable(LoginUser loginUser) {
        return null;
    }

    /**
     * 优化方结算页面（今日消费）
     *
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> billDayCost(LoginUser loginUser, String searchTime) {
        //获取渠道商的客户和代理商
        List<User> userList = userMapper.getUserByCreateId(loginUser.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("InUserId", loginUser.getId());
        List<FundItemSum> fundItemSumList = new ArrayList<>();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat smm = new SimpleDateFormat("yyyy-MM");
        if (searchTime == null) {
            searchTime = sm.format(new Date());
        }
        String monthTime = null;
        try {
            monthTime = smm.format(smm.parse(searchTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //本日
        params.put("searchTime", searchTime);
        //本月
        params.put("monthTime", monthTime);

        Long i = new Long(0);
        for (User item : userList
                ) {
            //判断角色
            UserRole userRole = userRoleMapper.selectByUserId(item.getId());
            Role role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
            if (role.getRoleCode().equals("AGENT") || role.getRoleCode().equals("CUSTOMER")) {
                i++;
                params.put("OutUserId", userRole.getUserId());
                Double sumDay = billCostMapper.selectByBillDayCost(params);
                Double sumMonth = billCostMapper.selectByBillMonthCost(params);
                FundItemSum fundItemSum = new FundItemSum();
                fundItemSum.setId(i);
                fundItemSum.setUserName(item.getUserName());
                FundAccount fundAccount = fundAccountMapper.selectByUserId(item.getId());
                if (fundAccount != null) {
                    fundItemSum.setBalance(fundAccount.getBalance());
                } else {
                    fundItemSum.setBalance(new BigDecimal(0));
                }
                fundItemSum.setChangeTime(searchTime);
                fundItemSum.setChangeAmount(new BigDecimal(sumMonth));
                fundItemSum.setdayAccountSum(new BigDecimal(sumDay));
                fundItemSumList.add(fundItemSum);

            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rows", fundItemSumList);
        return map;
    }

    //客户消费
    @Override
    public Map<String, Object> billClientDayCost(LoginUser loginUser) {
        //获取渠道商的客户和代理商
        List<User> userList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (loginUser.hasRole("ASSISTANT")) {
            params.put("InUserId", loginUser.getCreateUserId());
            userList = userMapper.getUserByCreateId(loginUser.getCreateUserId());
        } else {
            params.put("InUserId", loginUser.getId());
            userList = userMapper.getUserByCreateId(loginUser.getId());
        }

        List<FundItemSum> fundItemSumList = new ArrayList<>();
        Long i = new Long(0);
        for (User item : userList
                ) {
            //判断角色
            UserRole userRole = userRoleMapper.selectByUserId(item.getId());
            Role role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
            if (role.getRoleCode().equals("CUSTOMER")) {
                i++;
                params.put("OutUserId", userRole.getUserId());
                Double sumDay = billCostMapper.selectByBillClientDayCost(params);
                Double sumMonth = billCostMapper.selectByBillClientMonthCost(params);
                FundItemSum fundItemSum = new FundItemSum();
                fundItemSum.setId(i);
                fundItemSum.setUserName(item.getUserName());
                FundAccount fundAccount = fundAccountMapper.selectByUserId(item.getId());
                if (fundAccount != null) {
                    fundItemSum.setBalance(fundAccount.getBalance());
                } else {
                    fundItemSum.setBalance(new BigDecimal(0));
                }
                SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                fundItemSum.setChangeTime(sm.format(new Date()));
                fundItemSum.setChangeAmount(new BigDecimal(sumMonth));
                fundItemSum.setdayAccountSum(new BigDecimal(sumDay));
                fundItemSumList.add(fundItemSum);

            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rows", fundItemSumList);
        return map;
    }

    /**
     * 客户提交的订单
     *
     * @param params
     * @param user
     * @return
     */
    @Override
    public int pendingAuditView1ListCmt(Map<String, String[]> params, LoginUser user) {
        String[] selectContent = params.get("selectContent[0][userName]");
        String[] checkboxLength = params.get("checkboxLength");
        String[] price1 = params.get("price1");
        String[] price2 = params.get("price2");
        String[] price3 = params.get("price3");

        int length = Integer.parseInt(checkboxLength[0]);
        for (int i = 0; i < length; i++) {
            String[] id = params.get("selectContent[" + i + "][id]");
            String[] price = params.get("price");
            String[] rankend = params.get("rankend");
            Long billId = Long.parseLong(id[0]);

            Bill billNow = billMapper.selectByPrimaryKey(billId);
            List<BillPrice> billPriceList = billPriceMapper.selectByBillId(billId);
            Boolean bool = true;
            //如果有了 就修改
            if (billPriceList.size() > 0) {
                for (BillPrice billPrice1 : billPriceList
                        ) {
                    if (billPrice1.getInMemberId().longValue() == user.getId().longValue()) {
                        bool = false;
                    }
                }
            }
            if (bool) {
                BillPrice billPrice = new BillPrice();
                billPrice.setBillId(billId);
                BigDecimal ret = null;
                ret = new BigDecimal((String) price[0]);
                billPrice.setPrice(ret);
                billPrice.setBillRankingStandard(Long.parseLong(rankend[0]));
                billPrice.setInMemberId(user.getId());
                ;
                billPrice.setOutMemberId(billNow.getCreateUserId());
                billPrice.setCreateTime(new Date());
                billPriceMapper.insert(billPrice);
                Bill bill1 = new Bill();
                bill1.setId(billId);
                bill1.setUpdateUserId(user.getId());
                if (user.hasRole("DISTRIBUTOR")) {
                    bill1.setState(1);
                } else {
                    bill1.setState(0);
                }

                billMapper.updateByPrimaryKeySelective(bill1);
                if (!"NaN".equals(price1[0])) {
                    String[] id1 = params.get("selectContent[" + i + "][id]");
                    String[] rankend1 = params.get("rankend1");
                    Long billId1 = Long.parseLong(id1[0]);
                    BillPrice billPrice1 = new BillPrice();
                    billPrice1.setBillId(billId1);
                    BigDecimal ret1 = null;
                    ret1 = new BigDecimal((String) price1[0]);
                    billPrice1.setPrice(ret1);
                    billPrice1.setBillRankingStandard(Long.parseLong(rankend1[0]));
                    billPrice1.setInMemberId(user.getId());
                    billPrice1.setOutMemberId(billNow.getCreateUserId());
                    billPrice1.setCreateTime(new Date());
                    billPriceMapper.insert(billPrice1);
                    if (!"NaN".equals(price2[0])) {
                        String[] id2 = params.get("selectContent[" + i + "][id]");
                        String[] rankend2 = params.get("rankend2");
                        Long billId2 = Long.parseLong(id2[0]);
                        BillPrice billPrice2 = new BillPrice();
                        billPrice2.setBillId(billId2);
                        BigDecimal ret2 = null;
                        ret2 = new BigDecimal((String) price2[0]);
                        billPrice2.setPrice(ret2);
                        billPrice2.setBillRankingStandard(Long.parseLong(rankend2[0]));
                        billPrice2.setInMemberId(user.getId());
                        billPrice2.setOutMemberId(billNow.getCreateUserId());
                        billPrice2.setCreateTime(new Date());
                        billPriceMapper.insert(billPrice2);
                        if (!"NaN".equals(price3[0])) {
                            String[] id3 = params.get("selectContent[" + i + "][id]");
                            String[] rankend3 = params.get("rankend3");
                            Long billId3 = Long.parseLong(id3[0]);
                            BillPrice billPrice3 = new BillPrice();
                            billPrice3.setBillId(billId3);
                            BigDecimal ret3 = null;
                            ret3 = new BigDecimal((String) price3[0]);
                            billPrice3.setPrice(ret3);
                            billPrice3.setBillRankingStandard(Long.parseLong(rankend3[0]));
                            billPrice3.setInMemberId(user.getId());
                            billPrice3.setOutMemberId(billNow.getCreateUserId());
                            billPrice3.setCreateTime(new Date());
                            billPriceMapper.insert(billPrice3);
                        }
                    }
                }
            } else {
                return 1;
            }


        }
        return 0;
    }

    /**
     * 批量修改价格
     *
     * @param bill
     * @param loginUser
     * @return
     */
    @Override
    public String uploadPrice(List<String[]> fileList, LoginUser loginUser) {
        if (!CollectionUtils.isEmpty(fileList)) {
            int updateCount = 0;
            //循环EXCEL表格
            String errorStr = "";
            for (int i = 0; i < fileList.size(); i++) {
                //获取参数
                String keyword = fileList.get(i)[1];
                String website = fileList.get(i)[2];
                String searchName = fileList.get(i)[3];
                BigDecimal price1 = new BigDecimal(fileList.get(i)[7]);
                BigDecimal price2 = new BigDecimal(fileList.get(i)[8]);
                int standardDays = Integer.parseInt(fileList.get(i)[10]);
                //组包
                Map<String, Object> params = new HashMap<>();
                params.put("userId", 1);
                params.put("state", 2);
                params.put("keywords", keyword);
                params.put("website", website);
                params.put("searchName", searchName);
                params.put("billAscription", loginUser.getId());

                //查询价格
                List<BillPrice> billPriceList = billPriceMapper.selectByBillPriceList(params);
                List<Bill> bill = billMapper.selectByBillByUpdateStandardDays(params);

                if (!CollectionUtils.isEmpty(billPriceList) && bill != null && !CollectionUtils.isEmpty(bill)) {
                    if (bill.size() == 1) {
                        //修改价格
                        BillPrice billPrice = new BillPrice();
                        billPrice.setId(billPriceList.get(0).getId());
                        billPrice.setPrice(price1);
                        billPriceMapper.updateByPrimaryKeySelective(billPrice);
                        //修改达标天数
                        bill.get(0).setStandardDays(standardDays);
                        billMapper.updateByPrimaryKeySelective(bill.get(0));

                        //判断第二个价格是否存在
                        if (!price2.equals(BigDecimal.ZERO)) {
                            if (billPriceList.size() == 1) {
                                BillPrice billPrice1 = new BillPrice();
                                billPrice1.setBillId(billPriceList.get(0).getBillId());
                                billPrice1.setInMemberId(billPriceList.get(0).getInMemberId());
                                billPrice1.setOutMemberId(billPriceList.get(0).getOutMemberId());
                                billPrice1.setPrice(price2);
                                billPrice1.setCreateTime(new Date());
                                billPrice1.setBillRankingStandard(new Long(20));
                                billPriceMapper.insert(billPrice1);
                            }
                        }
                        updateCount++;
                    } else {
                        errorStr += "关键词：" + keyword + "," + "网址：" + website + "，有重复订单。";
                        continue;
                    }

                } else {
                    errorStr += "关键词：" + keyword + "," + "网址：" + website + "，格式不正确（网址末尾或缺少/）";
                }

            }
            return errorStr + "成功更新：" + updateCount + "条记录。";
        }
        return null;


    }

    //年度总消费
    public Double YearConsumption(Map<String, Object> params) {
        Calendar now = Calendar.getInstance();
        params.put("year", now.get(Calendar.YEAR));
        Double sum = billCostMapper.MonthConsumption(params);
        return sum;
    }

    //本月总消费
    public Double MonthConsumption(Map<String, Object> params) {
        Calendar now = Calendar.getInstance();
        params.put("year", now.get(Calendar.YEAR));
        params.put("month", now.get(Calendar.MONTH) + 1);
        Double sum = billCostMapper.MonthConsumption(params);
        return sum;
    }

    //上月总消费
    public Double lastMConsumption(Map<String, Object> params) {
        Calendar now = Calendar.getInstance();
        params.put("year", now.get(Calendar.YEAR));
        params.put("month", now.get(Calendar.MONTH));
        Double sum = billCostMapper.MonthConsumption(params);
        return sum;
    }

    //今日消费
    public Double DayConsumption(Map<String, Object> params) {
        Calendar now = Calendar.getInstance();
        params.put("year", now.get(Calendar.YEAR));
        params.put("month", now.get(Calendar.MONTH) + 1);
        params.put("day", now.get(Calendar.DATE));
        Double sum = billCostMapper.MonthConsumption(params);
        return sum;
    }

    //昨日消费
    public Double yesterDayConsumption(Map<String, Object> params) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
        params.put("year", calendar.get(Calendar.YEAR));
        params.put("month", calendar.get(Calendar.MONTH) + 1);
        params.put("day", calendar.get(Calendar.DATE));
        Double sum = billCostMapper.MonthConsumption(params);
        return sum;
    }


    //获取一个月的天数
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 某一个月第一天和最后一天
     *
     * @param date
     * @return
     */
    private static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    public String getYAxisSum(double max) {
        String yAxis = "";
        if (max <= 50) {
            yAxis = "0,5,10,15,20,25,30,35,40,45";
        } else if (max <= 100 && max > 50) {

            yAxis = "50,55,60,65,70,75,80,85,90,95";
        } else if (max > 100 && max <= 200) {

            yAxis = "100,110,120,130,140,150,160,170,180,190";
        } else if (max <= 300 && max > 200) {
            yAxis = "200,210,220,230,240,250,260,270,280,290";
        } else if (max <= 400 && max > 300) {
            yAxis = "300,310,320,330,340,350,360,370,380,390";
        } else if (max <= 500 && max > 400) {
            yAxis = "400,410,420,430,440,450,460,470,480,490";
        } else if (max <= 600 && max > 500) {
            yAxis = "500,510,520,530,540,550,560,570,580,590";
        } else if (max <= 700 && max > 600) {
            yAxis = "600,610,620,630,640,650,660,670,680,690";
        } else if (max <= 800 && max > 700) {
            yAxis = "700,710,720,730,740,750,760,770,780,790";
        } else if (max <= 900 && max > 800) {
            yAxis = "800,810,820,830,840,850,860,870,880,890";
        } else if (max <= 1000 && max > 900) {
            yAxis = "900,910,920,930,940,950,960,970,980,990";
        } else if (max <= 2000 && max > 1000) {
            yAxis = "1100,1200,1300,1400,1500,1600,1700,1800,1900,2000";
        } else if (max <= 3000 && max > 2000) {
            yAxis = "2100,2200,2300,2400,2500,2600,2700,2800,2900,3000";
        } else if (max <= 4000 && max > 3000) {
            yAxis = "3100,3200,3300,3400,3500,3600,3700,3800,3900,4000";
        } else if (max <= 5000 && max > 4000) {
            yAxis = "4100,4200,4300,4400,4500,4600,4700,4800,4900,5000";
        } else if (max <= 5000 && max > 4000) {
            yAxis = "5100,5200,5300,5400,5500,5600,5700,5800,5900,6000";
        } else if (max <= 6000 && max > 5000) {
            yAxis = "5100,5200,5300,5400,5500,5600,5700,5800,5900,6000";
        } else if (max <= 7000 && max > 6000) {
            yAxis = "6100,6200,6300,6400,6500,6600,6700,6800,6900,7000";
        } else if (max <= 8000 && max > 7000) {
            yAxis = "7100,7200,7300,7400,7500,7600,7700,7800,7900,8000";
        } else if (max <= 9000 && max > 8000) {
            yAxis = "8100,8200,8300,8400,8500,8600,8700,8800,8900,9000";
        } else if (max <= 10000 && max > 9000) {
            yAxis = "9100,9200,9300,9400,9500,9600,9700,9800,9900,10000";
        } else if (max <= 11000 && max > 10000) {
            yAxis = "10100,10200,10300,10400,10500,10600,10700,10800,10900,11000";
        } else if (max <= 12000 && max > 11000) {
            yAxis = "11100,11200,11300,11400,11500,11600,11700,11800,11900,12000";
        } else if (max <= 13000 && max > 12000) {
            yAxis = "12100,12200,12300,12400,12500,12600,12700,12800,12900,13000";
        } else if (max <= 14000 && max > 13000) {
            yAxis = "13100,13200,13300,13400,13500,13600,13700,13800,13900,14000";
        } else if (max <= 15000 && max > 14000) {
            yAxis = "14100,14200,14300,14400,14500,14600,14700,14800,14900,15000";
        } else if (max <= 16000 && max > 15000) {
            yAxis = "15100,15200,15300,15400,15500,15600,15700,15800,15900,16000";
        } else if (max <= 17000 && max > 16000) {
            yAxis = "16100,16200,16300,16400,16500,16600,16700,16800,16900,17000";
        } else if (max <= 18000 && max > 17000) {
            yAxis = "17100,17200,17300,17400,17500,17600,17700,17800,17900,18000";
        } else if (max <= 19000 && max > 18000) {
            yAxis = "18100,18200,18300,18400,18500,18600,18700,18800,18900,19000";
        } else if (max <= 20000 && max > 19000) {
            yAxis = "19100,19200,19300,19400,19500,19600,19700,19800,19900,20000";
        }
        return yAxis;
    }

    public boolean delSearchTask(String[] taskId) {
        //设置传入参数
        String wAction = "DelSearchTask";
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("taskId", taskId);
        jsonobj.put("time", System.currentTimeMillis());
        jsonobj.put("userId", Define.userId);
        jsonobj.put("businessType", 1006);
        //加密
        String wParam = jsonobj.toString();
        String wSign = null;
        try {
            wSign = md5_urlEncode.EncoderByMd5(wAction + Define.token + jsonobj.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Map对象传入参数
        Map<String, String> params = new HashMap<>();
        params.put("wAction", wAction);
        params.put("wParam", wParam);
        params.put("wSign", wSign);
        String result = remoteService.delSearchTask(params);
        //返回对象
        //请求返回
        //返回对象
        CustomerRankingResult customerRankingResult = new CustomerRankingResult();
        //转换返回数据
        JSONObject myJsonObject = new JSONObject(result);
        JSONArray value = myJsonObject.getJSONArray("xValue");
        customerRankingResult.setCode(Integer.parseInt(myJsonObject.get("xCode").toString()));
        customerRankingResult.setMessage(myJsonObject.get("xMessage").toString());
        customerRankingResult.setValue(value);
        if (customerRankingResult.getMessage().equals("success.")) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * 删除优帮云调点击数据
     *
     * @param params
     * @param user
     * @return
     */
    public int deleteYBYbill(Map<String, String[]> params, LoginUser user) {
        //任务个数
        String[] checkboxLength = params.get("length");
        //任务状态（1：在线  100：离线  999：删除）
        String[] stateLength = params.get("state");
        if (checkboxLength.length > 0 && stateLength.length > 0) {
            int state = Integer.parseInt(stateLength[0]);
            int length = Integer.parseInt(checkboxLength[0]);
            if (state != 0 && length != 0) {
                for (int i = 0; i < length; i++) {
                    //获取订单ID
                    String[] id = params.get("billId");
                    Long billId = Long.parseLong(id[0]);
                    //获取订单
                    Bill bill = billMapper.selectByPrimaryKey(billId);
                    //组包
                    //调整点击录入订单
                    int agid = OpDefine.agid;
                    String action = "setstatus";
                    String md5Key = OpDefine.md5Key;

                    //取现在时间
                    String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    //订单优化量
                    //加密sign
                    int webApiId = bill.getWebAppId1();
                    int[] intarr = new int[]{webApiId, state};
                    JSONArray jsonArray = new JSONArray(intarr);


                    String arr = "[\"kw\"," + jsonArray.toString() + "]";
                    //组包
                    String str = "";
                    str = str.concat(JSON.toJSONString(dateString));
                    str = str.concat(JSON.toJSONString(action));
                    str = str.concat(arr);
                    str = str.concat(JSON.toJSONString(md5Key));
                    str = str.concat(JSON.toJSONString(agid));
                    //加密sign
                    String sign = null;
                    try {
                        sign = md5_urlEncode.EncoderByMd51(str);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //创建JSON
                    JSONObject jaction = new JSONObject();
                    jaction.put("action", action);
                    JSONObject jagid = new JSONObject();
                    jagid.put("agid", agid);
                    JSONObject jstamp = new JSONObject();
                    jstamp.put("stamp", dateString);
                    JSONObject jargs = new JSONObject();
                    jargs.put("args", arr);
                    JSONObject jsign = new JSONObject();
                    jsign.put("sign", sign);
                    //拼接字符串
                    String str11 = "{" + jagid.toString().replace("{", "").replace("}", "") + "," + jstamp.toString().replace("{", "").replace("}", "") + "," +
                            jaction.toString().replace("{", "").replace("}", "") + "," + jsign.toString().replace("{", "").replace("}", "") + "," +
                            jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\", "").replace("{\"args\"", "\"args\"");
                    //Base64  编码
                    BASE64Encoder base64Encoder = new BASE64Encoder();
                    String data = null;
                    try {
                        data = base64Encoder.encode(str11.getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("data", data);
                    //调点击返回对象（停止优化）
                    CustomerOptimizationResult customerOptimizationResult = remoteService.getOptimizationApi(map);
                    if (customerOptimizationResult != null) {
                        //判断返回结果是否成功
                        JSONObject jsonObject = customerOptimizationResult.getArgs();
                        String code = jsonObject.get("code").toString();
                        if ("success".equals(code)) {
                            //优化离线
                            if (state == 1) {
                                bill.setOpstate(1);
                            } else if (state == 100) {
                                bill.setOpstate(2);
                            }

                            billMapper.updateByPrimaryKeySelective(bill);
                            return 1;
                        } else {
                            return 2;
                        }
                    }
                }


            } else {
                return 0;
            }
        }

        return 0;
    }

    public int deleteYBYState(Long[] billIdArr, LoginUser user, int state) {
        JSONArray jsonArray = new JSONArray();
        String jsonStr = "";
        for (int i = 0; i < billIdArr.length; i++) {
            Long billId = billIdArr[i];
            Bill bill = billMapper.selectByPrimaryKey(billId);
            //加密sign
            int webApiId = bill.getWebAppId1();
            int[] intarr = new int[]{webApiId, state};
            JSONArray json = new JSONArray(intarr);
            jsonStr += json.toString() + ",";
        }
        String arr = "[\"kw\"," + jsonStr.substring(0, jsonStr.length() - 1) + "]";
        //组包
        //调整点击录入订单
        int agid = OpDefine.agid;
        String action = "setstatus";
        String md5Key = OpDefine.md5Key;
        //取现在时间
        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        //组包
        String str = "";
        str = str.concat(JSON.toJSONString(dateString));
        str = str.concat(JSON.toJSONString(action));
        str = str.concat(arr);
        str = str.concat(JSON.toJSONString(md5Key));
        str = str.concat(JSON.toJSONString(agid));
        //加密sign
        String sign = null;
        try {
            sign = md5_urlEncode.EncoderByMd51(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建JSON
        JSONObject jaction = new JSONObject();
        jaction.put("action", action);
        JSONObject jagid = new JSONObject();
        jagid.put("agid", agid);
        JSONObject jstamp = new JSONObject();
        jstamp.put("stamp", dateString);
        JSONObject jargs = new JSONObject();
        jargs.put("args", arr);
        JSONObject jsign = new JSONObject();
        jsign.put("sign", sign);
        //拼接字符串
        String str11 = "{" + jagid.toString().replace("{", "").replace("}", "") + "," + jstamp.toString().replace("{", "").replace("}", "") + "," +
                jaction.toString().replace("{", "").replace("}", "") + "," + jsign.toString().replace("{", "").replace("}", "") + "," +
                jargs.toString().replace("\"[", "[").replace("]\"", "]").replace("\\", "").replace("{\"args\"", "\"args\"");
        //Base64  编码
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String data = null;
        try {
            data = base64Encoder.encode(str11.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", data);
        //调点击返回对象（停止优化）
        CustomerOptimizationResult customerOptimizationResult = remoteService.getOptimizationApi(map);
        if (customerOptimizationResult != null) {
            //判断返回结果是否成功
            JSONObject jsonObject = customerOptimizationResult.getArgs();
            String code = jsonObject.get("code").toString();
            if ("success".equals(code)) {
                return 1;
            } else {
                return 2;
            }
        }
        return 0;

    }


}
