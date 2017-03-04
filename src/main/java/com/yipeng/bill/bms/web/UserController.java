package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 17/3/4.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(HttpServletRequest request) {
        return "/user/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(HttpServletRequest request,User user) {
        userService.saveUser(user);
        return "redirect:/user/detail";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, ModelMap modelMap, @PathVariable Long id) {
        User user = userService.getUserById(id);
        modelMap.put("user",user);
        return "/user/detail";
    }

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, ModelMap modelMap, @RequestParam(required = false) String userName,@RequestParam(required = false) String qq) {
        Map<String,Object> params = new HashMap<>();
        params.put("userName",userName);
        params.put("qq",qq);
        List<User> userList = userService.findList(params);
        modelMap.put("userList",userList);
        return "/user/list";
    }
}
