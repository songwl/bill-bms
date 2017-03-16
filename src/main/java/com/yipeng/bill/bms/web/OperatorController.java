package com.yipeng.bill.bms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 鱼在我这里。 on 2017/3/16.
 */
@Controller
@RequestMapping(value ="/operator")
public class OperatorController extends  BaseController {

    @RequestMapping(value = "/list")
    public  String list(HttpServletRequest request)
    {
        return "/operator/list";
    }

}
