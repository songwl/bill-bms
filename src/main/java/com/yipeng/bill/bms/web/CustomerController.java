package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.CustomerService;
import com.yipeng.bill.bms.vo.LoginUser;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/3/17.
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends  BaseController{

    @Autowired
    private CustomerService customerService;
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
     * 获取用户列表
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value="getCustomerList")
    @ResponseBody
    public Map<String,Object> getCustomerList(int limit,int offset)
    {
        LoginUser user=this.getCurrentAccount();

        if(user!=null)
        {
            offset=(offset-1)*limit;
            Map<String,Object> params=new HashMap<>();
            params.put("limit",limit);
            params.put("offset",offset);
            Map<String, Object> modelMap=  customerService.getCustomerList(params,user);
            return  modelMap;

        }
        return null;
    }

    /**
     * 添加客户
     * @param request
     * @param user
     * @param addMemberId
     * @param balance
     * @return
     */
    @RequestMapping(value = "/customerList",method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage createUser(HttpServletRequest request, User user, int addMemberId, BigDecimal balance)
    {
        User user1=this.getCurrentAccount();
        int a=customerService.savaUser(user,addMemberId,user1.getId(),balance);
        if (a==0)
        {
            return this.ajaxDoneError("系统错误,请稍后再试！");
        }
        else
        {
            return this.ajaxDoneSuccess("添加成功!");
        }

    }

    /**
     * 客户审核页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/customerReviewList")
    public  String customerReviewList(HttpServletRequest request)
    {
        return "/customer/customerReviewList";
    }
    /**
     * 获取待审核用户列表
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value="/getCustomerReviewList")
    @ResponseBody
    public Map<String,Object> getCustomerReviewList(int limit,int offset)
    {
        LoginUser user=this.getCurrentAccount();

        if(user!=null)
        {
            if(user.hasRole("SUPER_ADMIN"))
            {
                offset=(offset-1)*limit;
                Map<String,Object> params=new HashMap<>();
                params.put("limit",limit);
                params.put("offset",offset);
                Map<String, Object> modelMap=  customerService.getCustomerReviewList(params);
                return  modelMap;
            }

        }
        return null;
    }
    @RequestMapping(value ="/customerAudit",method = RequestMethod.POST)
    @ResponseBody
    public  ResultMessage customerAudit(HttpServletRequest request, @RequestParam Long customerId)
    {

        LoginUser user=this.getCurrentAccount();

        if(user!=null)
        {
            if(user.hasRole("SUPER_ADMIN"))
            {
                 int a=customerService.customerAudit(customerId);
                 if(a==1)
                 {
                     return  this.ajaxDoneSuccess("审核成功！");
                 }
                 else
                 {
                     return  this.ajaxDoneError("审核失败，请稍后再试！");
                 }
            }

        }
        return null;
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
