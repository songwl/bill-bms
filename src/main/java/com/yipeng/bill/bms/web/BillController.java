package com.yipeng.bill.bms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
@Controller
@RequestMapping(value = "/billList")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/listDetails")
    public  String listDetails(HttpServletRequest request)
    {

        return "/bill/billList";
    }
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public Map<String, Object> getUser( int limit, int offset)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);


        long total=userService.getUserListCount();
        //测试数据
        List<User> users =userService.findList(limit, offset);
        modelMap.put("total",total);
        modelMap.put("rows",users);

       return  modelMap;


    }
}
