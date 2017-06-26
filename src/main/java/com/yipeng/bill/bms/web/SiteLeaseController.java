package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.UserMapper;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/SiteLease")
public class SiteLeaseController extends BaseController {
    @Autowired
    private SiteLeaseService siteLeaseService;
    @Autowired
    private UserMapper userMapper;


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
     * 管理员获取任务列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/AdminGetMission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> AdminGetMission(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN")) {
            return null;
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
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
}
