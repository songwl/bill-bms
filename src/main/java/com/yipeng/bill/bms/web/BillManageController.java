package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.service.BillManageService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2.
 */
@Controller
@RequestMapping(value = "/billManage")
public class BillManageController extends  BaseController {

    @Autowired
    private BillManageService billManageService;
    @Autowired
    private UserService userService;
    /**
     * 订单管理(管理员)
     * @param request
     * @return
     */
    @RequestMapping(value="/manageListByAdmin",method = RequestMethod.GET)
    public  String manageListByAdmin(HttpServletRequest request)
    {


        return "/billManage/manageListByAdmin";
    }

    /**
     * 订单管理TABLE  (管理员)
     * @param request
     * @return
     */
    @RequestMapping(value="/manageListByAdminTable",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> manageListByAdminTable(HttpServletRequest request)
    {
        LoginUser user=this.getCurrentAccount();
        int limit =Integer.parseInt(request.getParameter("limit"));
        int offset=Integer.parseInt(request.getParameter("offset"));

        offset=(offset-1)*limit;
        Map<String, Object> params=new HashMap<>();
        params.put("limit",limit);
        params.put("offset",offset);
        Map<String,Object> map=billManageService.manageListByAdminTable(params,user);
        return  map;
    }


    /**
     * 订单管理(操作员和渠道商)
     * @param request
     * @return
     */
    @RequestMapping(value="/manageListByOther",method = RequestMethod.GET)
    public  String manageListByOther(HttpServletRequest request)
    {
        return "/billManage/manageListByOther";
    }

    /**
     * 订单管理TABLE  (渠道商和操作员)
     * @param request
     * @return
     */
    @RequestMapping(value="/manageListByOtherTable",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> manageListByOtherTable(HttpServletRequest request,String sortOrder, String sortName)
    {
        LoginUser user=this.getCurrentAccount();
        int limit =Integer.parseInt(request.getParameter("limit"));
        int offset=Integer.parseInt(request.getParameter("offset"));

        offset=(offset-1)*limit;
        Map<String, Object> params=new HashMap<>();
        params.put("limit",limit);
        params.put("offset",offset);
        if(sortName!=null)
        {
            params.put("sortName",sortName);
            params.put("sortOrder",sortOrder);
        }
        Map<String,Object> map= billManageService.manageListByOtherTable(params,user);
        return  map;
    }

    /**
     * 新增排名(操作员和渠道商)
     * @param request
     * @return
     */
    @RequestMapping(value="/getNewRanking",method = RequestMethod.GET)
    public  String getNewRanking(HttpServletRequest request)
    {
        return "/billManage/getNewRanking";
    }

    /**
     * 订单管理TABLE  (渠道商和操作员)
     * @param request
     * @return
     */
    @RequestMapping(value="/getNewRankingTable",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getNewRankingTable(HttpServletRequest request,String sortOrder, String sortName, String website,String keywords)
    {
        LoginUser user=this.getCurrentAccount();
        int limit =Integer.parseInt(request.getParameter("limit"));
        int offset=Integer.parseInt(request.getParameter("offset"));

        offset=(offset-1)*limit;
        Map<String, Object> params=new HashMap<>();
        params.put("limit",limit);
        params.put("offset",offset);
        if(sortName!=null)
        {
            params.put("sortName",sortName);
            params.put("sortOrder",sortOrder);
        }
        if("displayId".equals(sortName))
        {
            params.put("sortName","id");
        }
        if(!keywords.isEmpty())
        {
            try{
                keywords = new String(keywords.getBytes("ISO-8859-1"),"utf-8");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            params.put("keywords",keywords);
        }
        if(!website.isEmpty())
        {
            params.put("website",website);
        }
        Map<String,Object> map= billManageService.getNewRankingTable(params,user);
        return  map;
    }


}
