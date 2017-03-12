package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
     * 注册用户
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public  String createUser(HttpServletRequest request,User user)
    {
        if(user.getUserName()!=""&&user.getPassword()!="")
        {
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
    @RequestMapping(value = "/register/validUserName",method = RequestMethod.GET)
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
