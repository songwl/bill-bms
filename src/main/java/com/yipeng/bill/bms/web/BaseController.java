package com.yipeng.bill.bms.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.yipeng.bill.bms.core.model.LoginAccount;
import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.core.spring.interceptor.BaseCommController;
import com.yipeng.bill.bms.core.spring.interceptor.HttpLocalThread;
import com.yipeng.bill.bms.core.utils.ServletUtil;
import com.yipeng.bill.bms.core.utils.WebUtils;

/**
 * 
 * Created by song on 2016/6/2.
 *
 */
public abstract class BaseController extends BaseCommController {

	//默认分页参数request 中的名称
	public static final String PAGE_NUM_PARAM = "page";
	public static final String PAGE_SIZE_PARAM = "pageSize";
	public static final String ORDER_FIELD_PARAM = "orderField";
	public static final String ORDER_DIRECTION_PARAM = "orderDirection";

	//默认查询请求参数前缀字符串
	public static final String SEARCH_PARAMETERS_STARTING_WITH_PARAM = "search_";

	//默认分页大小
	public static final int DEFAUTL_PAGE_SIZE = 20;

	protected LoginAccount getCurrentAccount() {
		return (LoginAccount) getSession(ServletUtil.SESSION_USER);
	}

	protected Integer getCurrentAccountId() {
		LoginAccount account = getCurrentAccount();
		if (account != null) {
			return account.getLoginUserId();
		}
		return null;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}

	protected void clearResponseCache() {
		//防止缓存
		HttpLocalThread.getResponse().setDateHeader("expires", 0);
		HttpLocalThread.getResponse().setHeader("Cache-Control", "no-cache");
		HttpLocalThread.getResponse().setHeader("pragma", "no-cache");
	}

	protected ResultMessage ajaxDone(int statusCode, String message, Object data) {
		ResultMessage mav = ResultMessage.create();
		mav.setCode(statusCode);
		mav.setMessage(message);
		if (data != null) {
			mav.setData(data);
		}
		return mav;
	}

	protected ResultMessage ajaxDoneSuccess(String message) {
		return ajaxDone(ResultMessage.SUCCESS_CODE, message, null);
	}

	protected ResultMessage ajaxDoneError(String message) {
		return ajaxDone(ResultMessage.ERROR_CODE, message, null);
	}

	protected ResultMessage ajaxDoneError(String message, Object data) {
		return ajaxDone(ResultMessage.ERROR_CODE, message, data);
	}

	protected ResultMessage ajaxDoneSuccess(String message, Object data) {
		return ajaxDone(ResultMessage.SUCCESS_CODE, message, data);
	}

	protected <T> Page<T> getPageRequest() {
		int pageNumber = WebUtils.getPage(PAGE_NUM_PARAM);
		int pagzSize = NumberUtils.toInt(WebUtils.getParameter(PAGE_SIZE_PARAM));
		pagzSize = pagzSize < 1 ? DEFAUTL_PAGE_SIZE : pagzSize;
		Page<T> page = new Page<T>(pageNumber, pagzSize);
		page.makeRequestUrl();

		String sortOrderBy = WebUtils.getParameter(ORDER_FIELD_PARAM);
		String sortOrderDesc = WebUtils.getParameter(ORDER_DIRECTION_PARAM);
		if (StringUtils.isNotBlank(sortOrderBy) && StringUtils.isNotBlank(sortOrderBy)) {
			String[] orderByArray = StringUtils.split(sortOrderBy, ',');
			String[] orderDescArray = StringUtils.split(sortOrderDesc, ',');
			Assert.isTrue(orderByArray.length == orderDescArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");
			List<String> orderBy = new ArrayList<>();
			for (int i = 0; i < orderByArray.length; i++) {
				orderBy.add(orderByArray[i] + " " + orderDescArray[i]);
			}
			page.setOrderBy(orderBy);
		}
		return page;
	}

	protected Map<String, Object> getSearchRequest() {
		return WebUtils.getParametersStartingWith(SEARCH_PARAMETERS_STARTING_WITH_PARAM);
	}

	/*@ModelAttribute
	public void loadBms(Model model, @RequestParam(defaultValue = "0") Integer navId) {
		Map<String, Object> bms = new HashMap<>();
		bms.put("navId", navId);
		LoginAccount account = this.getCurrentAccount();
		if (account != null) {
			bms.put("account", account);
			bms.put("bmsNavigationList", MapCache.getInstance().get(CacheConstants.CACHE_BMS_NAVIGATION_BY_USERTYPE_ + account.getLoginUserType()));
		}
		model.addAttribute("bmsModel", bms);
	}*/
}
