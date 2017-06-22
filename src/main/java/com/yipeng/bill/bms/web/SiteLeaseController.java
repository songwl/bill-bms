package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.service.SiteLeaseService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/SiteLease")
public class SiteLeaseController extends BaseController {
    @Autowired
    private SiteLeaseService siteLeaseService;

    @RequestMapping(value = "/MissionHall", method = RequestMethod.GET)
    public String MissionHall(HttpServletRequest request) {
        return "/SiteLease/MissionHall";
    }

    @RequestMapping(value = "/GetMission", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetMission(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = siteLeaseService.GetMission(params, loginUser);
        return modelMap;
    }
    @RequestMapping(value = "/GetDetails", method = RequestMethod.POST)
    @ResponseBody
    public List<HallDetails> GetDetails(String website) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("website", website);
        List<HallDetails> modelMap = siteLeaseService.GetDetails(website,loginUser);
        return modelMap;
    }
}
