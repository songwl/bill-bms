package com.yipeng.bill.bms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 鱼在我这里。 on 2017/3/17.
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends  BaseController{

    /**
     * 客户列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/customerList")
    public  String customerList(HttpServletRequest request)
    {
        return "/customer/customerList";
    }

    /**
     * 资金明细
     * @param request
     * @return
     */
    @RequestMapping(value = "/fundAccount")
    public  String fundAccount(HttpServletRequest request)
    {
        return "/customer/fundAccount";
    }
}
