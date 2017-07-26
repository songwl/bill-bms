package com.yipeng.bill.bms.web;


import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.dao.orderLeaseMapper;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.service.SiteLeaseService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "/SiteLease")
public class SiteLeaseController extends BaseController {
    @Autowired
    private SiteLeaseService siteLeaseService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private orderLeaseMapper orderLeaseMapper;


    /**
     * 专员任务大厅
     *
     * @return
     */
    @RequestMapping(value = "/MissionHall", method = RequestMethod.GET)
    public String MissionHall() {
        return "/SiteLease/MissionHall";
    }

    /**
     * 管理员划分订单页面
     *
     * @return
     */
    @RequestMapping(value = "/DivideOrder", method = RequestMethod.GET)
    public String DivideOrder() {
        return "/SiteLease/DivideOrder";
    }

    /**
     * 渠道商任务大厅
     *
     * @return
     */
    @RequestMapping(value = "/DistributorOrder", method = RequestMethod.GET)
    public String DistributorOrder() {
        return "/SiteLease/DistributorOrder";
    }

    /**
     * 代理商任务大厅
     *
     * @return
     */
    @RequestMapping(value = "/AgentOrder", method = RequestMethod.GET)
    public String AgentOrder(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("userId", loginUser.getId());
        return "/SiteLease/AgentOrder";
    }

    /**
     * 客户任务大厅
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/CustomerOrder", method = RequestMethod.GET)
    public String CustomerOrder(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("userId", loginUser.getId());
        return "/SiteLease/CustomerOrder";
    }

    /**
     * 客户任务大厅订单详情页面
     * @param httpServletRequest
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/OrderDetails", method = RequestMethod.GET)
    public String OrderDetails(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        Map<String, String[]> param = httpServletRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("userId", loginUser.getId());
        modelMap.put("website", param.get("website")[0].toString());
        return "/SiteLease/OrderDetails";
    }

    @RequestMapping(value = "/LeaseOverdueList", method = RequestMethod.GET)
    public String LeaseOverdueList( ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("userId", loginUser.getId());
        return "/SiteLease/LeaseOverdueList";
    }
    /**
     * 专员分配订单
     *
     * @return
     */
    @RequestMapping(value = "/AllotOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage AllotOrder(HttpServletRequest httpServletRequest) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("COMMISSIONER")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        //boolean flag=siteLeaseService.GetMission()
        return this.ajaxDone(1, "", null);
    }

    /**
     * 管理员获取任务列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/AdminGetMission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> AdminGetMission(int limit, int offset,String website,String trade) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("website", website);
        params.put("trade", trade);
        Map<String, Object> modelMap = siteLeaseService.AdminGetMission(params, loginUser);
        return modelMap;
    }

    /**
     * 专员获取任务列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/GetMission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetMission(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("COMMISSIONER")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = siteLeaseService.GetMission(params, loginUser);
        return modelMap;
    }

    /**
     * 渠道商获取任务列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/GetReceiveIdMission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetReceiveIdMission(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("DISTRIBUTOR")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = siteLeaseService.GetReceiveIdMission(params, loginUser);
        return modelMap;
    }

    /**
     * 代理商获取任务列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/GetAgentIdMission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetAgentIdMission(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("AGENT")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = siteLeaseService.GetAgentIdMission(params, loginUser);
        return modelMap;
    }

    /**
     * 专员获取任务列表详情
     *
     * @param website
     * @return
     */
    @RequestMapping(value = "/GetDetails", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> GetDetails(String website) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("website", website);
        List<Map<String, Object>> modelMap = siteLeaseService.GetDetails(website, loginUser);
        return modelMap;
    }

    /**
     * 管理员划分订单给渠道商
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/DivideOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage DivideOrder(HttpServletRequest httpServletRequest) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        Map<String, String[]> param = httpServletRequest.getParameterMap();
        int len = Integer.parseInt(param.get("len")[0].toString());
        String[] arr = new String[len];
        for (int i = 0; i < len; i++) {
            arr[i] = param.get("selectContent[" + i + "][website]")[0].toString();
        }
        boolean flag = true;
        for (String item : arr
                ) {
            orderLease orderLeaseList = orderLeaseMapper.selectReserveByWebsite(item);
            if (orderLeaseList.getOrderstate() > 3) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            return this.ajaxDone(-3, "所选订单某些订单已不能划分，请重新选择", null);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("orderstate", 2);
        map.put("reserveid", "");
        map.put("allottime", new Date());
        map.put("receiveid", param.get("distributor")[0].toString());
        ResultMessage resultMessage = siteLeaseService.DivideOrder(map);
        return resultMessage;
    }

    /**
     * 获取所有渠道商
     *
     * @return
     */
    @RequestMapping(value = "/GetDistributor", method = RequestMethod.POST)
    @ResponseBody
    public List<User> GetDistributor() {
        List<User> userList = userMapper.selectDistributor();
        return userList;
    }

    /**
     * 预定订单
     *
     * @param website
     * @param type
     * @return
     */
    @RequestMapping(value = "/ReserveOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage ReserveOrder(String website, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("AGENT")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        ResultMessage resultMessage = siteLeaseService.ReserveOrder(website, type, loginUser);
        return resultMessage;
    }

    /**
     * 获取预定人数
     *
     * @param website
     * @return
     */
    @RequestMapping(value = "/GetReserve", method = RequestMethod.POST)
    @ResponseBody
    public List<User> GetReserve(String website) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("DISTRIBUTOR")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("website", website);
        List<User> modelMap = siteLeaseService.GetReserve(website, loginUser);
        return modelMap;
    }

    /**
     * 渠道商给代理商充值（保证金）
     *
     * @param userid
     * @param website
     * @param SumMoney
     * @return
     */
    @RequestMapping(value = "/Recharge", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage Recharge(String userid, String website, String SumMoney) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("DISTRIBUTOR")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        orderLease orderLease = orderLeaseMapper.selectReserveByWebsite(website);
        if (orderLease == null || orderLease.getOrderstate() != 3) {
            return this.ajaxDone(-3, "该订单状态不允许充值", null);
        }
        BigDecimal sumMoney = new BigDecimal("0");
        try {
            BigDecimal bd = new BigDecimal(SumMoney);
            sumMoney = bd.setScale(2);
        } catch (Exception ex) {
            return this.ajaxDone(-4, "金额输入错误", null);
        }
        BigDecimal bd = new BigDecimal(0);
        if (sumMoney.compareTo(bd) == -1) {
            return this.ajaxDone(-4, "金额输入错误", null);
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        String[] arr = {website};
        params.put("arr", arr);
        params.put("reserveid", userid);
        params.put("orderstate", 4);
        params.put("reservetime", new Date());
        int a = siteLeaseService.Recharge(params, sumMoney, loginUser);
        return this.ajaxDone(a, "", null);
    }

    /**
     * 客户任务大厅页面列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/CustomerGetMission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> CustomerGetMission(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("CUSTOMER")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = siteLeaseService.CustomerGetMission(params, loginUser);
        return modelMap;
    }

    /**
     * 客户任务大厅页面点击详情
     *
     * @param limit
     * @param offset
     * @param website
     * @return
     */
    @RequestMapping(value = "/GetOrderDetails", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetOrderDetails(int limit, int offset, String website) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("CUSTOMER")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("website", website);
        Map<String, Object> modelMap = siteLeaseService.OrderDetails(params, loginUser);
        return modelMap;
    }

    /**
     * 客户订购订单
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ResponseBody
    public int order(HttpServletRequest httpServletRequest) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("CUSTOMER")) {
            return -1;
        }
        Map<String, String[]> map = httpServletRequest.getParameterMap();
        int len = Integer.parseInt(map.get("len")[0].toString());
        Long[] arr = new Long[len];
        //List<orderLease> leaseList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            /*orderLease orderLease = new orderLease();
            orderLease.setId(Long.parseLong(map.get("data[" + i + "][id]")[0].toString()));
            orderLease.setKeywords(map.get("data[" + i + "][keywords]")[0].toString());
            orderLease.setWebsite(map.get("data[" + i + "][website]")[0].toString());
            orderLease.setOrderid(Long.parseLong(map.get("data[" + i + "][orderId]")[0].toString()));
            orderLease.setKeywordstate(Integer.parseInt(map.get("data[" + i + "][keywordState]")[0].toString()));
            leaseList.add(orderLease);*/
            arr[i] = Long.parseLong(map.get("data[" + i + "][id]")[0].toString());
        }
        String website = map.get("data[0][website]")[0].toString();
        int result = siteLeaseService.Ordering(arr, website, loginUser);
        return result;
    }

    /**
     * 获取当前代理商下面的所有客户
     *
     * @return
     */
    @RequestMapping(value = "/GetCustomer", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> GetCustomer() {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("AGENT")) {
            return null;
        }
        List<Map<String, Object>> result = siteLeaseService.GetCustomer(loginUser);
        return result;
    }

    /**
     * 代理商确定客户
     *
     * @param website
     * @param customer
     * @return
     */
    @RequestMapping(value = "/ConfirmCustomer", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage ConfirmCustomer(String website, String customer) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("AGENT")) {
            return this.ajaxDone(-1, "", null);
        }
        int result = siteLeaseService.ConfirmCustomer(website, loginUser, customer);
        return this.ajaxDone(result, "", null);
    }

    /**
     * 获取保证金已过期的数据
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/GetLeaseOverdue", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetLeaseOverdue(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("AGENT")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = siteLeaseService.GetLeaseOverdue(params, loginUser);
        return modelMap;
    }
}
