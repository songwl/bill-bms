package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.BillCostMapper;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.BillPriceMapper;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.BillCost;
import com.yipeng.bill.bms.domain.BillPrice;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.model.BillManageList;
import com.yipeng.bill.bms.service.BillManageService;
import com.yipeng.bill.bms.vo.BillDetails;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/5/2.
 */
@Service("BillManageService")
public class BillManageServiceImpl implements BillManageService {

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BillCostMapper billCostMapper;
    /**
     * 管理员订单管理
     * @param params
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> manageListByAdminTable(Map<String, Object> params, LoginUser loginUser) {

        //转换时间
        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
        String daynow=formatter.format(new Date());
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow=null;
        try {
            dateNow = format1.parse(daynow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //参数对象
        int limit=Integer.parseInt(params.get("limit").toString()) ;
        int offset=Integer.parseInt(params.get("offset").toString()) ;
        int i=offset;
        params.put("userId",loginUser.getId());
        params.put("state",2);//订单状态
        params.put("billType",1);//订单属性
        List<Bill> billList=billMapper.selectBillGroupByWebsite(params);
        //视图对象
        if (!CollectionUtils.isEmpty(billList))
        {

            List<BillManageList> billManageListList=new ArrayList<BillManageList>();
            for (Bill item:billList
                    ) {
                //填充对象
                BillManageList billManageList=new BillManageList();
                billManageList.setId(i+=1);
                billManageList.setSqlId(item.getId());
                //客户
                BillPrice billPrice=new BillPrice();
                billPrice.setBillId(item.getId());
                billPrice.setInMemberId(loginUser.getId());
                List<BillPrice> billPriceList=billPriceMapper.selectByBillPrice(billPrice);//通过单价表的outmemberId获取用户
                if(!CollectionUtils.isEmpty(billPriceList))
                {
                    Long userId=billPriceList.get(0).getOutMemberId();
                    User user=userMapper.selectByPrimaryKey(userId);
                    billManageList.setUserName(user.getUserName());
                }
                User user1=userMapper.selectByPrimaryKey(item.getBillAscription());//操作员
                billManageList.setCommissioner(user1.getUserName());
                billManageList.setWebSite(item.getWebsite());
                //一个网址对应的关键词数
                Map<String,Object> websiteParams=new HashMap<>();
                websiteParams.put("website",item.getWebsite());
                List<Bill> bills=billMapper.selectAllSelective(websiteParams);

                if (!CollectionUtils.isEmpty(bills))
                {
                    billManageList.setBillCount(bills.size());//任务数
                    int count=0;//达标数
                    double dayConsumption=0;
                    for (Bill billitem:bills
                            ) {
                        BillPrice billPrices=new BillPrice();
                        billPrice.setBillId(billitem.getId());
                        billPrice.setInMemberId(loginUser.getId());
                        List<BillPrice> billPriceLists=billPriceMapper.selectByBillPrice(billPrices);
                        //循环判断当前关键词是否达标
                        if(!CollectionUtils.isEmpty(billPriceLists))
                        {


                            for (BillPrice billPriceItem:billPriceLists
                                    ) {
                                if (billitem.getNewRanking()<=billPriceItem.getBillRankingStandard())
                                {
                                    count+=1;
                                    //消费表
                                    Map<String,Object> priceMap=new HashMap<>();
                                    priceMap.put("BillId",billitem.getId());
                                    priceMap.put("date",dateNow);
                                    priceMap.put("priceId",billPriceItem.getId());
                                    BillCost billCost1=billCostMapper.selectByBillIdAndDate(priceMap);
                                    if(billCost1!=null)
                                    {
                                        dayConsumption+=(billCost1.getCostAmount().doubleValue());
                                    }
                                    break;
                                }
                            }
                        }



                    }
                    double keywordsCompletionRate=((double)count/bills.size())*100;
                    //关键词达标率
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                    billManageList.setKeywordsCompletionRate(df.format(keywordsCompletionRate).toString());
                    //优化天数
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式时间
                    Date date=item.getCreateTime();
                    Date now=new Date();
                    int days = (int) ((now.getTime() - date.getTime()) / (1000*3600*24));//两个日期相差的天数
                    billManageList.setOptimizationDays(days);
                    billManageList.setDayConsumption(dayConsumption);

                }

                billManageListList.add(billManageList);
            }
            Map<String,Object> map=new HashMap<>();

            map.put("rows",billManageListList);
            map.put("total",billList.size());
            return  map;
        }
        else
        {
            return null;
        }

    }

    /**
     * 订单管理（渠道商和操作员）
     * @param params
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> manageListByOtherTable(Map<String, Object> params, LoginUser loginUser) {
        //转换时间
        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
        String daynow=formatter.format(new Date());
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow=null;
        try {
            dateNow = format1.parse(daynow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //参数对象
        int limit=Integer.parseInt(params.get("limit").toString()) ;
        int offset=Integer.parseInt(params.get("offset").toString()) ;
        int i=offset;
        if(loginUser.hasRole("COMMISSIONER"))
        {
            params.put("userId",loginUser.getCreateUserId());
            params.put("state",2);//订单状态
            params.put("billType",1);//订单属性
            params.put("billAscription",loginUser.getId());
        }
        else
        {
            params.put("userId",loginUser.getId());
            params.put("state",2);//订单状态
            params.put("billType",1);//订单属性
        }
        List<Bill> billList=billMapper.selectBillGroupByWebsite(params);
        //视图对象
        if (!CollectionUtils.isEmpty(billList))
        {
            List<BillManageList> billManageListList=new ArrayList<BillManageList>();
            for (Bill item:billList
                    ) {
                //填充对象
                BillManageList billManageList=new BillManageList();
                billManageList.setId(i+=1);
                billManageList.setSqlId(item.getId());
                //客户
                BillPrice billPrice=new BillPrice();
                billPrice.setBillId(item.getId());
                billPrice.setInMemberId(loginUser.getId());
                List<BillPrice> billPriceList=billPriceMapper.selectByBillPrice(billPrice);//通过单价表的outmemberId获取用户
                if(!CollectionUtils.isEmpty(billPriceList))
                {
                    Long userId=billPriceList.get(0).getOutMemberId();
                    User user=userMapper.selectByPrimaryKey(userId);
                    billManageList.setUserName(user.getUserName());
                }
                billManageList.setWebSite(item.getWebsite());
                //一个网址对应的关键词数
                Map<String,Object> websiteParams=new HashMap<>();
                websiteParams.put("website",item.getWebsite());
                List<Bill> bills=billMapper.selectAllSelective(websiteParams);

                if (!CollectionUtils.isEmpty(bills))
                {
                    billManageList.setBillCount(bills.size());//任务数
                    int count=0;//达标数
                    double dayConsumption=0;
                    double monthConsumption=0;
                    for (Bill billitem:bills
                            ) {
                        BillPrice billPrices=new BillPrice();
                        billPrices.setBillId(billitem.getId());
                        if (loginUser.hasRole("COMMISSIONER"))
                        {
                            billPrices.setInMemberId(loginUser.getCreateUserId());
                        }
                        else
                        {
                            billPrices.setInMemberId(loginUser.getId());
                        }
                        List<BillPrice> billPriceLists=billPriceMapper.selectByBillPrice(billPrices);
                        //循环判断当前关键词是否达标
                        if(!CollectionUtils.isEmpty(billPriceLists))
                        {
                            for (BillPrice billPriceItem:billPriceLists
                                    ) {
                                if (billitem.getNewRanking()<=billPriceItem.getBillRankingStandard())
                                {
                                    count+=1;
                                    //消费表
                                    Map<String,Object> priceMap=new HashMap<>();
                                    priceMap.put("BillId",billitem.getId());
                                    priceMap.put("date",dateNow);
                                    priceMap.put("priceId",billPriceItem.getId());
                                    BillCost billCost1=billCostMapper.selectByBillIdAndDate(priceMap);
                                    if(billCost1!=null)
                                    {
                                        dayConsumption+=(billCost1.getCostAmount().doubleValue());
                                    }
                                    break;
                                }
                            }
                        }
                        //计算每个关键词本月的消费
                        //monthConsumption+=billCostMapper.selectByBillCostOfMonth()


                    }
                    double keywordsCompletionRate=((double)count/bills.size())*100;
                    //关键词达标率
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                    billManageList.setKeywordsCompletionRate(df.format(keywordsCompletionRate).toString());
                    //优化天数
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式时间
                    Date date=item.getCreateTime();
                    Date now=new Date();
                    int days = (int) ((now.getTime() - date.getTime()) / (1000*3600*24));//两个日期相差的天数
                    billManageList.setOptimizationDays(days);
                    billManageList.setDayConsumption(Double.parseDouble( df.format(dayConsumption)));


                }
                billManageListList.add(billManageList);
            }
            Map<String,Object> map=new HashMap<>();
            map.put("rows",billManageListList);
            map.put("total",billList.size());
            return  map;
        }
        else
        {
            return null;
        }

    }

    @Override
    public Map<String, Object> getNewRankingTable(Map<String, Object> params, LoginUser loginUser) {
        int offset=Integer.parseInt(params.get("offset").toString()) ;
         int i=offset;
        List<BillDetails> billDetailsList=new ArrayList<>();
        //根据权限来判断
        //管理员
        if(loginUser.hasRole("SUPER_ADMIN"))
        {

        }
        //渠道商
        else if(loginUser.hasRole("DISTRIBUTOR"))
        {
            Calendar now =Calendar.getInstance();
            params.put("year",now.get(Calendar.YEAR));
            params.put("month",now.get(Calendar.MONTH)+1);
            params.put("day",now.get(Calendar.DATE));

            //查询最新排名的订单
            params.put("userId",loginUser.getId());
            params.put("state",2);


            List<Bill> billList=billMapper.selectByNewRanking(params);
            Long total=billMapper.selectByNewRankingCount(params);
            if (!CollectionUtils.isEmpty(billList))
            {
                for (Bill item: billList
                     ) {
                    i++;
                     BillDetails billDetails=new BillDetails();
                     billDetails.setdisplayId(i);
                     billDetails.setWebsite(item.getWebsite());
                     billDetails.setKeywords(item.getKeywords());
                     billDetails.setNewRanking(item.getNewRanking());
                     billDetails.setChangeRanking(item.getChangeRanking());
                     billDetailsList.add(billDetails);
                }

                Map<String,Object> map=new HashMap<>();
                map.put("total",total);
                map.put("rows",billDetailsList);
                return  map;
            }

        }
        return null;
    }
}
