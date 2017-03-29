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
    public Map<String,Object> getCustomerBill(int limit, int offset)
    {


        Long roleId=new Long(3);
        offset=(offset-1)*limit;
        // Page<Bill> page = this.getPageRequest();    //分页对象
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        User user=this.getCurrentAccount();
        params.put("limit",limit);
        params.put("offset",offset);
        params.put("roleId",roleId);
        params.put("user",user);
        Map<String, Object> modelMap= operatorService.getOperator(params);

        return  modelMap;
    }
    @RequestMapping(value = "/updateUserState",method =RequestMethod.POST)
    @ResponseBody
    public ResultMessage updateUserState(HttpServletRequest request)
    {
        User user=this.getCurrentAccount();
        if(user!=null)
        {
            Map<String, String[]> params = request.getParameterMap();
            String[] users=params.get("userId");
            Long userId=Long.parseLong(users[0]);
              int a=operatorService.updateUserState(userId);
            return this.ajaxDoneSuccess("操作成功!");
        }
        else
        {
            return  this.ajaxDoneError("操作失败！");
        }

    }

}

