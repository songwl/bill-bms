package com.yipeng.bill.bms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/5/4.
 */
@Controller
@RequestMapping(value = "/ybyNotify")
public class ybyNotifyController extends  BaseController{

    /**
     * 获取优帮云通知
     * @param request
     * @return
     */
    @RequestMapping(value="/getYbyNotify",method = RequestMethod.POST)
    @ResponseBody
    public  String getYbyNotify(String xAction,String xParam,String xSign)
    {


        return "1";
    }

}
