package com.yipeng.bill.bms.web;

import java.util.HashMap;
import java.util.Map;

import com.yipeng.bill.bms.domain.User;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yipeng.bill.bms.core.model.LoginAccount;

@Controller
public class HomeController extends BaseController {

	//@Autowired
	//private AuthorityService authorityService;

	@RequestMapping(value = "/index")
	public String index(ModelMap model) throws Exception {
		Map<String, Object> bms = new HashMap<>();
		User user = this.getCurrentAccount();
		bms.put("user", user);
		//bms.put("bmsNavigationList", authorityService.queryBmsNavByUserType(NumberUtils.toInt(account.getLoginUserType())));
		model.addAttribute("bmsModel", bms);
		return "index";
	}

	/*@RequestMapping("/demo.do")
	public String demo() {
		return "demo";
	}*/

}
