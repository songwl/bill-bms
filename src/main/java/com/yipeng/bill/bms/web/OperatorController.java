package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.OperatorService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/3/16.
 */
@Controller
@RequestMapping(value ="/operator")
public class OperatorController extends  BaseController {

    @Autowired
    private OperatorService operatorService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public  String list(HttpServletRequest request)
    {
        return "/operator/list";
    }

    /**
     * 添加操作员
     * @param request
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage createCommissioner(HttpServletRequest request, User user)
    {
        LoginUser users=this.getCurrentAccount();
        if(users.hasRole("SUPER_ADMIN"))
        {
            int a=operatorService.saveOperator(user,users);
            if(a==0)
            {
                return this.ajaxDoneError("用户名已经存在！");

            }
            else
            {
                return  this.ajaxDoneSuccess("注册成功");
            }

        }
        return  this.ajaxDoneError("系统错误，请稍后再试！");
    }
    /**
     * 操作员列表
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getOperator")
    @ResponseBody
    public Map<String,Object> getCustomerBill(int limit, int offset,String searchUserName,String searchState)
    {


        Long roleId=new Long(3);
        offset=(offset-1)*limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        User user=this.getCurrentAccount();
        params.put("limit",limit);
        params.put("offset",offset);
        params.put("roleId",roleId);
        params.put("user",user);
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
        Map<String, Object> modelMap= operatorService.getOperator(params);

        return  modelMap;
    }

    /**
     * 修改信息
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateOperator",method =RequestMethod.POST)
    @ResponseBody
    public ResultMessage updateOperator(HttpServletRequest request,User user)
    {
        LoginUser user1=this.getCurrentAccount();
        if(user1!=null)
        {

            int a=operatorService.updateOperator(user,user1);
            return this.ajaxDoneSuccess("操作成功!");
        }
        else
        {
            return  this.ajaxDoneError("操作失败！");
        }

    }
    /**
     * 重置密码
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/updatePwd",method =RequestMethod.POST)
    @ResponseBody
    public ResultMessage updatePwd(HttpServletRequest request,User user)
    {
        LoginUser user1=this.getCurrentAccount();
        if(user1!=null)
        {

            int a=operatorService.updatePwd(user,user1);
            return this.ajaxDoneSuccess("重置成功!");
        }
        else
        {
            return  this.ajaxDoneError("重置失败！");
        }

    }




}

