package com.yipeng.bill.bms.web;

import java.util.HashMap;
import java.util.Map;

import com.yipeng.bill.bms.service.HomeService;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.CustomerRankingParam;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends BaseController {
	@Autowired
	private HomeService homeService;
	//@Autowired
	//private AuthorityService authorityService;

	@RequestMapping(value = "/index")
	public String index(ModelMap model) throws Exception {
		Map<String, Object> bms = new HashMap<>();
		LoginUser user = this.getCurrentAccount();
		bms.put("user", user);
		//bms.put("bmsNavigationList", authorityService.queryBmsNavByUserType(NumberUtils.toInt(account.getLoginUserType())));
		model.addAttribute("bmsModel", bms);
		return "index";
	}

	/*@RequestMapping("/demo.do")
	public String demo() {
		return "demo";
	}*/

	@RequestMapping(value = "/home")
	public String home(ModelMap model) throws Exception {
        LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.homeDetails(loginUser);
		bms.put("user", loginUser);
		model.addAttribute("bmsModel", bms);
		CustomerRankingParam customerRankingParam=new CustomerRankingParam();

		return "/home/home";
	}
}
