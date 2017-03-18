package com.yipeng.bill.bms.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/3/17.
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends  BaseController{

    /**
     * 客户列表
     * @return
     */
    @RequestMapping(value = "/customerList")
    public  String customerList(ModelMap model) throws Exception
    {
        Map<String, Object> bms = new HashMap<>();
        Object user = this.getCurrentAccount();

        bms.put("user", user);
        model.addAttribute("bmsModel", bms);
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
