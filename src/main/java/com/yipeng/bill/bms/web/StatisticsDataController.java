package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.dao.UserPowerMapper;
import com.yipeng.bill.bms.domain.UserPower;
import com.yipeng.bill.bms.service.StatisticsDataService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/StatisticsData")
public class StatisticsDataController extends BaseController {
    @Autowired
    private StatisticsDataService statisticsDataService;
    @Autowired
    private UserPowerMapper userPowerMapper;

    @RequestMapping(value = "WebsiteMonitor", method = RequestMethod.GET)
    public String WebsiteMonitor(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser.hasRole("SUPER_ADMIN")) {
            modelMap.put("iscan", 1);
        } else if (loginUser.hasRole("COMMISSIONER")) {
            UserPower userPower = userPowerMapper.selectByUserId(loginUser.getId());
            if (userPower != null && userPower.getPowerid() == 0) {
                modelMap.put("iscan", 1);
            } else {
                modelMap.put("iscan", 0);
            }
        } else {
            modelMap.put("iscan", 0);
        }
        return "/StatisticsData/WebsiteMonitor";
    }

    @RequestMapping(value = "GetWebsiteMonitor", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetWebsiteMonitor(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN") && !loginUser.hasRole("COMMISSIONER")) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        Map<String, Object> stringObjectMap = statisticsDataService.GetWebsiteMonitor(map, loginUser);

        return stringObjectMap;
    }

    @RequestMapping(value = "LoadFa", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> LoadFa() {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN") && !loginUser.hasRole("COMMISSIONER")) {
            return null;
        }
        Map<String, Object> stringObjectMap = statisticsDataService.LoadFa(loginUser);

        return stringObjectMap;
    }
}
