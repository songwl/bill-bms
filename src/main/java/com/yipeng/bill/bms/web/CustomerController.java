package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.CustomerService;
import com.yipeng.bill.bms.vo.LoginUser;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
        LoginUser user = this.getCurrentAccount();
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
    public Map<String,Object> getCustomerList(int limit,int offset,String searchUserName,String searchState)
    {
        LoginUser user=this.getCurrentAccount();

        if(user!=null)
        {
            offset=(offset-1)*limit;
            Map<String,Object> params=new HashMap<>();
            params.put("limit",limit);
            params.put("offset",offset);
            if(!searchUserName.isEmpty())
            {
                try{
                    searchUserName = new String(searchUserName.getBytes("ISO-8859-1"),"utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                params.put("searchUserName",searchUserName);
            }
            if(!searchState.isEmpty())
            {
                params.put("searchState",searchState);
            }
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
    public ResultMessage createUser(HttpServletRequest request, User user, int addMemberId,
                                    String realName,String contact,String phone,String qq, BigDecimal balance)
    {
        User user1=this.getCurrentAccount();
        int a=customerService.savaUser(user,addMemberId,user1.getId(),realName,contact,phone,qq,balance);
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

    /**
     * 审核客户
     * @param request
     * @param customerId
     * @return
     */
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

    /**
     * 资金明细table
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value="/fundAccountList")
    @ResponseBody
    public Map<String,Object> fundAccountList(int limit,int offset) {
         LoginUser user=this.getCurrentAccount();
        offset = (offset - 1) * limit;
         if(user!=null)
         {

             Map<String,Object> params=new HashMap<>();
             params.put("limit",limit);
             params.put("offset",offset);
             Map<String, Object> modelMap=  customerService.fundAccountList(params,user);
             return  modelMap;
         }
         return  null;

    }
    /**
     * 客户充值
     * @param request
     * @return
     */
     @RequestMapping(value = "/Recharge",method = RequestMethod.POST)
     @ResponseBody
    public  ResultMessage Recharge(HttpServletRequest request)
    {
        LoginUser user=this.getCurrentAccount();
        if(user!=null)
        {
            String customerId=request.getParameter("customerId");
            String RechargeSum=request.getParameter("RechargeSum");
           int a= customerService.Recharge(customerId,RechargeSum,user);
           return  this.ajaxDoneSuccess("充值成功！");
        }

        return  this.ajaxDoneError("充值失败，请稍后再试!");

    }
    /**
     * 客户退款
     * @param request
     * @return
     */
    @RequestMapping(value = "/Refound",method = RequestMethod.POST)
    @ResponseBody
    public  ResultMessage Refound(HttpServletRequest request)
    {
        LoginUser user=this.getCurrentAccount();
        if(user!=null)
        {
            String customerId=request.getParameter("customerId");
            String RechargeSum=request.getParameter("RechargeSum");
            int a= customerService.Refound(customerId,RechargeSum,user);
            if(a==1)
            {
                return  this.ajaxDoneSuccess("退款成功！");
            }
            else if(a==2)
            {
                return  this.ajaxDoneError("退款金额不能大于余额!");
            }
            else
            {
                return  this.ajaxDoneError("退款失败，请稍后再试!");
            }

        }

        return  this.ajaxDoneError("退款失败，请稍后再试!");

    }

    /**
     * 客户代理权限
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateDailiRole",method = RequestMethod.POST)
    @ResponseBody
    public  ResultMessage updateDailiRole(HttpServletRequest request)
    {
        LoginUser user=this.getCurrentAccount();
        if(user!=null)
        {
            Map<String, String[]> params= request.getParameterMap();
            int a=customerService.updateDailiRole(params,user);
            if(a==1)
            {
                return this.ajaxDoneSuccess("开通成功!");
            }
            else
            {
                return this.ajaxDoneError("开通失败，请稍后再试！");
            }
        }
        return this.ajaxDoneError("您还未登录，请先登录!");
    }
}
