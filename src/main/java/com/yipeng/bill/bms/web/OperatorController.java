package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.RoleMapper;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.OperatorService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/3/16.
 */
@Controller
@RequestMapping(value ="/operator")
public class OperatorController extends BaseController {

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;
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
    public ResultMessage list(HttpServletRequest request, User user)
    {
        LoginUser users=this.getCurrentAccount();
        String role="COMMISSIONER";
        if(users.hasRole("SUPER_ADMIN"))
        {
            int a=operatorService.saveOperator(user,users,role);
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
     * 获取操作员列表
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getOperator")
    @ResponseBody
    public Map<String,Object> getOperator(int limit, int offset,String searchUserName,String searchState)
    {
        Map<String, Object> modelMap=new HashMap<>();
        Role role=roleMapper.selectByRoleCode("COMMISSIONER");
        if(role!=null)
        {
            offset=(offset-1)*limit;
            Map<String, Object> params = this.getSearchRequest(); //查询参数
            User user=this.getCurrentAccount();
            params.put("limit",limit);
            params.put("offset",offset);
            params.put("roleId",role.getId());
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

           modelMap= operatorService.getOperator(params);
        }

        return  modelMap;
    }


    @RequestMapping(value = "/adminList",method = RequestMethod.GET)
    public  String adminList(HttpServletRequest request)
    {
        return "/operator/adminList";
    }
    /**
     * 添加操作员
     * @param request
     * @return
     */
    @RequestMapping(value = "/adminList",method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage adminList(HttpServletRequest request, User user)
    {
        LoginUser users=this.getCurrentAccount();
        String role="ADMIN";
        if(users.hasRole("SUPER_ADMIN"))
        {
            int a=operatorService.saveOperator(user,users,role);
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
     * 获取运维列表
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getAdminList")
    @ResponseBody
    public Map<String,Object> getAdminList(int limit, int offset,String searchUserName,String searchState)
    {
        Map<String, Object> modelMap=new HashMap<>();
        Role role=roleMapper.selectByRoleCode("ADMIN");
        if(role!=null)
        {
            offset=(offset-1)*limit;
            Map<String, Object> params = this.getSearchRequest(); //查询参数
            User user=this.getCurrentAccount();
            params.put("limit",limit);
            params.put("offset",offset);
            params.put("roleId",role.getId());
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

            modelMap= operatorService.getOperator(params);
        }

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
    public ResultMessage updateOperator(HttpServletRequest request, User user)
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
    public ResultMessage updatePwd(HttpServletRequest request, User user)
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
    /**
     * 重置密码
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/deleteUser",method =RequestMethod.POST)
    @ResponseBody
    public ResultMessage deleteUser(HttpServletRequest request, User user)
    {
        LoginUser user1=this.getCurrentAccount();
        if(user1!=null)
        {

            int a=operatorService.deleteUser(user,user1);
            if(a==1)
            {
                return this.ajaxDoneSuccess("删除成功!");
            }
            else
            {
                return  this.ajaxDoneError("删除失败！");
            }

        }
        else
        {
            return  this.ajaxDoneError("未知错误！");
        }

    }

}

