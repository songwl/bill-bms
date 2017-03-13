package com.yipeng.bill.bms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public  String listDetails(HttpServletRequest request)
    {

        return "/bill/billList";
    }

    /**
     * table表格获取数据
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public Object getUser( int limit, int offset)
    {

      offset=offset-1;
        Page<Bill> page = this.getPageRequest();    //分页对象
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit",limit);
        params.put("offset",offset);

        //调的是USER表的数据
        //Map<String, Object> modelMap=userService.findList(limit, offset);
        page.setItemList(billService.findBillList(params));

        return  page;
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
    public ResultMessage samePrice(@RequestParam(value="search",required = true) String search,@RequestParam(value="keyword",required = true) String keyword,
                                    @RequestParam(value="url",required = true) String url,@RequestParam(value="rankend",required = true) Long rankend,
                                    @RequestParam(value="price",required = true) Long price,@RequestParam(value="rankend1",required = false) Long rankend1,
                                    @RequestParam(value="price1",required = false) Long price1,@RequestParam(value="rankend2",required = false) Long rankend2,
                                    @RequestParam(value="price2",required = false) Long price2,@RequestParam(value="rankend3",required = false) Long rankend3,
                                    @RequestParam(value="price3",required = false) Long price3
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
        billService.saveBill( user,search,url, keyword, rankend, price, rankend1, price1, rankend2, price2, rankend3, price3);

        return this.ajaxDoneError("用户名已注册!");
    }
}
