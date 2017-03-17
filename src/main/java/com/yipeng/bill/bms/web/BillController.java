package com.yipeng.bill.bms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.BillCostMapper;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.BillPriceMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.BillCost;
import com.yipeng.bill.bms.domain.BillPrice;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.model.BillPriceDetails;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.BillDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/3/10.
 */
@Controller
@RequestMapping(value = "/bill")
public class BillController extends BaseController {
    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/list")
    public  String listDetails(ModelMap modelMap,@RequestParam(defaultValue = "1") String way)
    {
        //type 1代表 查询下游的订单   2代表 查询提交到上游的订单
        modelMap.put("way",way);
        return "/bill/billList";
    }
    @RequestMapping(value = "/pendingAudit")
    public  String pendingAudit(HttpServletRequest request)
    {

        return "/bill/billPendingAudit";
    }

    /**
     * table表格获取数据
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getBillDetails")
    @ResponseBody
    public Map<String,Object> getBillDetails( int limit, int offset)
    {

       offset=(offset-1)*limit;
       // Page<Bill> page = this.getPageRequest();    //分页对象
       Map<String, Object> params = this.getSearchRequest(); //查询参数
        User user=this.getCurrentAccount();
        params.put("limit",limit);
        params.put("offset",offset);
        params.put("user",user);


        //调的是USER表的数据

        Map<String, Object> modelMap=billService.findBillList(params);
        return  modelMap;
    }

    /**
     *  相同价提交(测试)
     * @param search
     * @param keyword
     * @param url
     * @param rankend
     * @param price
     * @param rankend1
     * @param price1
     * @param rankend2
     * @param price2
     * @param rankend3
     * @param price3
     * @return
     */
    @RequestMapping(value = "/list/sameprice",method = RequestMethod.GET)
    @ResponseBody
    public ResultMessage samePrice(@RequestParam(value="search",required = true) String search, @RequestParam(value="keyword",required = true) String keyword,
                                   @RequestParam(value="url",required = true) String url, @RequestParam(value="rankend",required = true) Long rankend,
                                   @RequestParam(value="price",required = true) BigDecimal price, @RequestParam(value="rankend1",required = false) Long rankend1,
                                   @RequestParam(value="price1",required = false) BigDecimal price1, @RequestParam(value="rankend2",required = false) Long rankend2,
                                   @RequestParam(value="price2",required = false) BigDecimal price2, @RequestParam(value="rankend3",required = false) Long rankend3,
                                   @RequestParam(value="price3",required = false) BigDecimal price3
    ) throws UnsupportedEncodingException {

         if(keyword!=null)
         {
             try {
                 //get参数乱码问题
                 keyword=new String(keyword.getBytes("ISO-8859-1"), "UTF-8");

             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }
         }

        User user = this.getCurrentAccount();
         String errorDetails=billService.saveBill( user,search,url, keyword, rankend, price, rankend1, price1, rankend2, price2, rankend3, price3);

        return this.ajaxDoneSuccess(errorDetails);
    }

    /**
     * 不同价导入
     * @param dfsearch
     * @param dfkeyword
     * @param dfurl
     * @param dfrankend
     * @param dfprice
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/list/diffrentprice",method = RequestMethod.GET)
    @ResponseBody
    public ResultMessage diffrentprice( @RequestParam(value="dfsearch",required = true) String dfsearch,@RequestParam(value="dfkeyword",required = true) String dfkeyword,
                                        @RequestParam(value="dfurl",required = true) String dfurl,@RequestParam(value="dfrankend",required = true) Long dfrankend,
                                        @RequestParam(value="dfprice",required = true) String dfprice
    ) throws UnsupportedEncodingException {

        if(dfkeyword!=null)
        {
            try {
                //get参数乱码问题
                dfkeyword=new String(dfkeyword.getBytes("ISO-8859-1"), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        User user = this.getCurrentAccount();
        String  errorDetails= billService.savaDiffrentBill( user,dfsearch,dfurl,dfkeyword, dfrankend, dfprice);

        return this.ajaxDoneSuccess(errorDetails);
    }

    /**
     * 调整价格
     * @param rankend
     * @param price
     * @param rankend1
     * @param price1
     * @param rankend2
     * @param price2
     * @param rankend3
     * @param price3
     * @param selectContent
     * @return
     */
    @RequestMapping(value = "/billList/updatePrice",method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage updatePrice( HttpServletRequest request, HttpServletResponse response)
    {
        Map<String, String[]> params= request.getParameterMap();

        User user = this.getCurrentAccount();
        int a=billService.updateBillPrice(params,user);
      return  this.ajaxDoneSuccess("");
    }

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private BillCostMapper  billCostMapper;
    //测试查排名 产生消费记录
    @RequestMapping(value = "/testpm")
    @ResponseBody
    public ResultMessage testpm()
    {
        List<Bill> billList=billMapper.selectAll();
        for (Bill bill: billList
                ) {
            int x=1+(int)(Math.random()*30);
            bill.setNewRanking(x);
            billMapper.updateByPrimaryKeySelective(bill);
            List<BillPrice>  billPrice=billPriceMapper.selectByBillId(bill.getId());
            Map<String, Object> modelMap = new HashMap();
            modelMap.put("BillId",bill.getId());
            modelMap.put("date",new Date());
            BigDecimal a=new BigDecimal("0");
            //如果排名小于排名扣费标准，则产生扣费记录
            if(billPrice.size()<=1)
            {
                if(x<=billPrice.get(0).getBillRankingStandard())
                {

                    BillCost billCost=billCostMapper.selectByBillIdAndDate(modelMap);
                    //如果今日已有记录，则更新
                    if(billCost!=null)
                    {
                        billCost.setCostAmount(billPrice.get(0).getPrice());
                        billCost.setRanking(x);
                        billCostMapper.updateByPrimaryKey(billCost);
                    }
                    //没有记录，记录数据
                    else
                    {
                        BillCost billCost1=new BillCost();
                        billCost1.settBillId(bill.getId());
                        billCost1.settBillPriceId(billPrice.get(0).getId());
                        billCost1.setCostAmount(billPrice.get(0).getPrice());
                        billCost1.setCostDate(new Date());
                        billCost1.setRanking(x);
                        billCostMapper.insert(billCost1);
                    }

                }
                //没有达到标准
                else
                {

                    BillCost billCost2=billCostMapper.selectByBillIdAndDate(modelMap);
                    //已有，更新记录
                    if(billCost2!=null)
                    {
                        billCost2.setCostAmount(a);
                        billCost2.setRanking(x);
                        billCostMapper.updateByPrimaryKey(billCost2);
                    }
                    //没有，插入数据
                    else
                    {
                        BillCost billCost3=new BillCost();
                        billCost3.settBillId(bill.getId());
                        billCost3.settBillPriceId(billPrice.get(0).getId());
                        billCost3.setCostAmount(a);
                        billCost3.setCostDate(new Date());
                        billCost3.setRanking(x);
                        billCostMapper.insert(billCost3);
                    }
                }

            }
            else
            {
                if(x<=billPrice.get(0).getBillRankingStandard())
                {

                    BillCost billCost4=billCostMapper.selectByBillIdAndDate(modelMap);
                    //如果今日已有记录，则更新
                    if(billCost4!=null)
                    {
                        billCost4.setCostAmount(billPrice.get(0).getPrice());
                        billCost4.setRanking(x);
                        billCostMapper.updateByPrimaryKey(billCost4);
                    }
                    //没有记录，记录数据
                    else
                    {
                        BillCost billCost5=new BillCost();
                        billCost5.settBillId(bill.getId());
                        billCost5.settBillPriceId(billPrice.get(0).getId());
                        billCost5.setCostAmount(billPrice.get(0).getPrice());
                        billCost5.setCostDate(new Date());
                        billCost5.setRanking(x);
                        billCostMapper.insert(billCost5);
                    }

                }
                else  if(x>billPrice.get(0).getBillRankingStandard()&&x<=billPrice.get(1).getBillRankingStandard())
                {

                    BillCost billCost6=billCostMapper.selectByBillIdAndDate(modelMap);
                    //如果今日已有记录，则更新
                    if(billCost6!=null)
                    {
                        billCost6.setCostAmount(billPrice.get(1).getPrice());
                        billCost6.setRanking(x);
                        billCostMapper.updateByPrimaryKey(billCost6);
                    }
                    //没有记录，记录数据
                    else
                    {
                        BillCost billCost7=new BillCost();
                        billCost7.settBillId(bill.getId());
                        billCost7.settBillPriceId(billPrice.get(1).getId());
                        billCost7.setCostAmount(billPrice.get(1).getPrice());
                        billCost7.setCostDate(new Date());
                        billCost7.setRanking(x);
                        billCostMapper.insert(billCost7);
                    }
                }
                //没有达到标准
                else
                {

                    BillCost billCost8=billCostMapper.selectByBillIdAndDate(modelMap);
                    //已有，更新记录
                    if(billCost8!=null)
                    {
                        billCost8.setCostAmount(a);
                        billCost8.setRanking(x);
                        billCostMapper.updateByPrimaryKey(billCost8);
                    }
                    //没有，插入数据
                    else
                    {
                        BillCost billCost9=new BillCost();
                        billCost9.settBillId(bill.getId());
                        billCost9.settBillPriceId(billPrice.get(0).getId());
                        billCost9.setCostAmount(a);
                        billCost9.setCostDate(new Date());
                        billCost9.setRanking(x);
                        billCostMapper.insert(billCost9);
                    }
                }
            }

        }

        return  this.ajaxDoneSuccess("");
    }
}
