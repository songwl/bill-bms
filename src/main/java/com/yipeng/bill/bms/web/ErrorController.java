package com.yipeng.bill.bms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lancey on 15/9/28.
 */
@Controller
public class ErrorController {

	@RequestMapping("/404.html")
	public String error404() {
		return "404";
	}

	@RequestMapping("/505.html")
	public String error500() {
		return "505";
	}
}
