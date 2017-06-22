package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.service.BillManageService;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class BillManageController extends BaseController {

    @Autowired
    private BillManageService billManageService;
    @Autowired
    private UserService userService;
    @Autowired
    private BillService billService;
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
     * 新增排名TABLE  (渠道商和操作员)
     * @param request
     * @return
     */
    @RequestMapping(value="/getNewRankingTable",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getNewRankingTable(HttpServletRequest request,String sortOrder, String sortName, String website,String keywords)
    {
        LoginUser user=this.getCurrentAccount();

        Map<String, Object> params=new HashMap<>();

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
    /**
     * 订单管理(管理员)
     * @param request
     * @return
     */
    @RequestMapping(value="/performanceStatistics",method = RequestMethod.GET)
    public  String performanceStatistics(HttpServletRequest request)
    {
        return "/billManage/performanceStatistics";
    }
    /**
     * 今日消费
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/performanceStatisticsTable")
    @ResponseBody
    public Map<String, Object> performanceStatisticsTable( String sortOrder, String sortName,String searchTime) {
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> modelMap = billManageService.performanceStatisticsTable(loginUser,searchTime);
        return modelMap;

    }

    /**
     * 新增排名(操作员和渠道商)
     * @param request
     * @return
     */
    @RequestMapping(value="/getStandardBill",method = RequestMethod.GET)
    public  String getStandardBill(HttpServletRequest request)
    {

        return "/billManage/getStandardBill";
    }

    /**
     * 新增排名table表格获取数据
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getStandardBillTable")
    @ResponseBody
    public Map<String, Object> getStandardBillTable(int limit, int offset, String sortOrder, String sortName,
                                              String website, String keywords, String searchName, String searchUserName,
                                              String state, String state2, String searchStandard,String standardDays,String createTime) throws UnsupportedEncodingException {
        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser loginUser = this.getCurrentAccount();
        if (!keywords.isEmpty()) {
            try {
                keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("keywords", keywords);
        }
        if (!website.isEmpty()) {
            params.put("website", website);
        }
        if (!searchName.isEmpty()) {
            try {
                searchName = new String(searchName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("searchName", searchName);
        }
        if (!loginUser.hasRole("CUSTOMER")) {
            if (!searchUserName.isEmpty()) {
                params.put("searchUserNameId", searchUserName);
            }
        }
        if (!state.isEmpty()) {
            params.put("state", state);
        }
        if (!state2.isEmpty()) {
            params.put("state2", state2);
        }
        if (!searchStandard.isEmpty()) {
            params.put("searchStandard", searchStandard);
        }
        if (!standardDays.isEmpty()) {
            params.put("standardDays", standardDays);
        }
        if (!createTime.isEmpty()) {
            params.put("createTime", createTime);
        }
        if (sortName != null) {
            params.put("sortName", sortName);
            params.put("sortOrder", sortOrder);
        }
        int way;
        if (loginUser.hasRole("CUSTOMER")||loginUser.hasRole("DISTRIBUTOR")||loginUser.hasRole("AGENT"))
        {
            way=1;
        }
        else
        {
            way=2;
        }


        LoginUser user = this.getCurrentAccount();
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("way", way);

        Map<String, Object> modelMap = billService.getBillDetails(params, user);


        return modelMap;
    }

    /**
     * 下滑排名(操作员和管理员)
     * @param request
     * @return
     */
    @RequestMapping(value="/getDeclineBill",method = RequestMethod.GET)
    public  String getDeclineBill(HttpServletRequest request)
    {
        return "/billManage/getDeclineBill";
    }

    /**
     * 新增排名TABLE  (渠道商和操作员)
     * @param request
     * @return
     */
    @RequestMapping(value="/getDeclineBillTable",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDeclineBillTable(HttpServletRequest request,String sortOrder, String sortName, String website,String keywords)
    {
        LoginUser loginUser=this.getCurrentAccount();
        Map<String, Object> params=new HashMap<>();

        Map<String,Object> map= billManageService.getDeclineBillTable(loginUser);
        return  map;
    }

}
