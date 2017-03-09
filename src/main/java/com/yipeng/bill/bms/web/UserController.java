package com.yipeng.bill.bms.web;

import com.alibaba.fastjson.JSON;
import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.core.model.JSONResult;
import com.yipeng.bill.bms.core.model.LoginAccount;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.UserRole;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @RequestMapping(value="/register",method = RequestMethod.GET)
    public  String register(HttpServletRequest request)
    {
        return "/user/register";
    }

    /**
     * 用户登录
     * @param request
     * @return
     */
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public  String login(HttpServletRequest request)
    {
        return "/user/login";
    }
    @RequestMapping(value="/dologin",method = RequestMethod.POST)
    public  String dologin(HttpServletRequest request,@RequestParam String userName, @RequestParam String password,ModelMap modelMap)
    {
        boolean loginType=  userService.login(userName,password);
        if(loginType)
        {

          return "redirect:/user/home";
        }
        else
        {
            return "redirect:/user/home";
        }

    }
    @RequestMapping(value="/home",method = RequestMethod.GET)
    public  String home(HttpServletRequest request)
    {
        return "/home/index";
    }

    /**
     * 注册用户
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public  String createUser(HttpServletRequest request,User user)
    {
        if(user.getUserName()!=""&&user.getPassword()!="")
        {
            user.setStatus(false);
              userService.saveUser(user);


            Long id=user.getId();




        }
        return  "/user/login";
    }

    /**
     *  根据注册用户名查询数据库是否已经存在
     * @param userName
     * @return
     */
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
