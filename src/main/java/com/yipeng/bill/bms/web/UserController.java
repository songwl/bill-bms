package com.yipeng.bill.bms.web;

import com.alibaba.fastjson.JSON;
import com.yipeng.bill.bms.core.model.JSONResult;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value="/register",method = RequestMethod.GET)
    public  String register(HttpServletRequest request)
    {
        return "/user/register";
    }
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public  String createUser(HttpServletRequest request,User user)
    {

        return  "";
    }
    @RequestMapping(value = "/ajaxName",method = RequestMethod.GET)
    public String AjaxName(HttpServletRequest request, @RequestParam String ReLogid, HttpServletResponse response) throws IOException {
       User user=  userService.getUserByName(ReLogid);
        if(user!=null)
        {
            response.getWriter().write("1");
             return   null;
        }
        else
        {
            response.getWriter().write("0");
            return  null;
        }

    }

}
