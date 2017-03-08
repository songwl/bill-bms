package com.yipeng.bill.bms.web;

import com.alibaba.fastjson.JSON;
import com.yipeng.bill.bms.core.model.JSONResult;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
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

    @RequestMapping(value = "/validUserName",method = RequestMethod.GET)
    @ResponseBody
    public ResultMessage validUserName(@RequestParam String userName) {
       User user=  userService.getUserByName(userName);
        if(user!=null)
        {
            return this.ajaxDoneError("用户名已注册!");
        }
        else
        {
            return this.ajaxDoneSuccess("用户名可以使用!");
        }

    }

}
