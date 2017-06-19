package com.yipeng.bill.bms.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.yipeng.bill.bms.dao.sendBoxMapper;
import com.yipeng.bill.bms.domain.noticepublish;
import com.yipeng.bill.bms.service.HomeService;
import com.yipeng.bill.bms.service.MessageService;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {
	@Autowired
	private HomeService homeService;
	@Autowired
	private RemoteService remoteService;
	//@Autowired
	//private AuthorityService authorityService;
	@Autowired
	private sendBoxMapper sendBoxMapper;
	@Autowired
	private MessageService messageService;
	//@Autowired
	//private AuthorityService authorityService;

	@RequestMapping(value = "/index")
	public String index(ModelMap model) throws Exception {
		Map<String, Object> bms = new HashMap<>();
		LoginUser user = this.getCurrentAccount();
		Long UnReadNum=sendBoxMapper.selectUnreadCount(user.getId().toString());//反馈未读数量
		Long SendUnReadNum = messageService.getReMailNum(user, 1);//发件箱未读数量
		Long InUnReadNum = messageService.getInReMailNum(user, 1);
		bms.put("UnReadNum", UnReadNum);
		bms.put("SendUnReadNum", SendUnReadNum);
		bms.put("InUnReadNum", InUnReadNum);
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
		Map<String, Object> bms=new HashMap<>();
		/*Map<String, Object> bms=new HashMap<>();*/
		List<noticepublish> inbox=homeService.getInBox(loginUser);
		bms.put("user", loginUser);
		bms.put("inbox",inbox );
		model.addAttribute("bmsModel", bms);

		return "/home/home";
	}

	@RequestMapping(value = "/homeDetails")
	@ResponseBody
	public Map<String, Object> homeDetails(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.homeDetails(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}
	//客户数
	@RequestMapping(value = "/userCount")
	@ResponseBody
	public Map<String, Object> userCount(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.userCount(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}

	//余额
	@RequestMapping(value = "/balance")
	@ResponseBody
	public Map<String, Object> balance(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.balance(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}

	//月总消费
	@RequestMapping(value = "/MonthConsumption")
	@ResponseBody
	public Map<String, Object> MonthConsumption(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.MonthConsumption(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}
	//本日消费
	@RequestMapping(value = "/DayConsumption")
	@ResponseBody
	public Map<String, Object> DayConsumption(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.DayConsumption(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}
	//当前任务数
	@RequestMapping(value = "/billCount")
	@ResponseBody
	public Map<String, Object> billCount(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.billCount(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}
	//累计任务数
	@RequestMapping(value = "/AllbillCount")
	@ResponseBody
	public Map<String, Object> AllbillCount(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.AllbillCount(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}
	//达标数
	@RequestMapping(value = "/standardSum")
	@ResponseBody
	public Map<String, Object> standardSum(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.standardSum(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}
	//百度
	@RequestMapping(value = "/baiduCompleteness")
	@ResponseBody
	public Map<String, Object> baiduCompleteness(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.baiduCompleteness(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}
	//手机百度
	@RequestMapping(value = "/baiduWapCompleteness")
	@ResponseBody
	public Map<String, Object> baiduWapCompleteness(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.baiduWapCompleteness(loginUser);
		model.addAttribute("bmsModel", bms);
		return bms;
	}

	//360
	@RequestMapping(value = "/sanliulingCompleteness")
	@ResponseBody
	public Map<String, Object> sanliulingCompleteness(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.sanliulingCompleteness(loginUser);

		return bms;
	}
	//搜狗
	@RequestMapping(value = "/sougouCompleteness")
	@ResponseBody
	public Map<String, Object> sougouCompleteness(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.sougouCompleteness(loginUser);
		return bms;
	}
	//神马
	@RequestMapping(value = "/shenmaCompleteness")
	@ResponseBody
	public Map<String, Object> shenmaCompleteness(ModelMap model) throws Exception {
		LoginUser loginUser=this.getCurrentAccount();
		Map<String, Object> bms=homeService.shenmaCompleteness(loginUser);

		return bms;
	}
}
