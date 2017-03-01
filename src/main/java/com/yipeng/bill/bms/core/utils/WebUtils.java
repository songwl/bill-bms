package com.yipeng.bill.bms.core.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.yipeng.bill.bms.core.model.RequestKV;
import com.yipeng.bill.bms.core.spring.interceptor.HttpLocalThread;

/**
 * Created by lancey on 15/1/15.
 */
public abstract class WebUtils {

	/**
	 * 该方法只能在Controller当中使用，超出这个类使用取不到request
	 * @return
	 */
	public static Integer getPage() {
		return getPage("page");
	}

	public static Integer getPage(String pageKey) {
		int page = NumberUtils.toInt(HttpLocalThread.getRequest().getParameter(pageKey));
		return page < 1 ? 1 : page;
	}

	/**
	 * 获取一个参数
	 * @param key
	 * @return
	 */
	public static String getParameter(String key) {
		return HttpLocalThread.getRequest().getParameter(key);
	}

	/**
	 * 跳过分页的参数
	 *
	 * @param req
	 * @return
	 */
	public static String getPageUrl(HttpServletRequest req, Map<String, String> skipParam) {
		return initUrl(req, null, skipParam);
	}

	public static String getUrl(HttpServletRequest req, boolean skipPageParam, Map<String, String> parameter) {
		Map<String, String> skipParam = new HashMap<String, String>();
		if (skipPageParam) {
			skipParam.put("page", "page");
			skipParam.put("pageSize", "pageSize");
		}
		return initUrl(req, parameter, skipParam);
	}

	public static String getUrl(HttpServletRequest req, String requestUri, boolean skipPageParam, Map<String, String> parameter) {
		Map<String, String> skipParam = new HashMap<String, String>();
		if (skipPageParam) {
			skipParam.put("page", "page");
			skipParam.put("pageSize", "pageSize");
		}
		return initUrl(req, requestUri, parameter, skipParam);
	}

	/**
	 * 默认的数据加入query当中
	 *
	 * @param req
	 * @param defaultV
	 *            默认要加入的内容，如果之前已经存在需要替换
	 * @return
	 */
	public static String getUrl(HttpServletRequest req, Map<String, String> defaultV) {
		return getUrl(req, true, defaultV);
	}

	private static String initUrl(HttpServletRequest req, Map<String, String> parameter, Map<String, String> skipParam) {
		return initUrl(req, req.getRequestURI(), parameter, skipParam);
	}

	/**
	 * 从请求生成url地址
	 *
	 * @param req
	 * @param skipParam
	 *            是否跳过分页
	 * @param parameter
	 *            需要添加在后面的默认的参数列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String initUrl(HttpServletRequest req, String requestUri, Map<String, String> parameter, Map<String, String> skipParam) {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> it = req.getParameterNames();
		int pos = 0;
		Set<String> keys = new HashSet<String>();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			if ((skipParam != null) && skipParam.containsKey(key)) {
				continue;
			}
			if ((parameter != null) && parameter.containsKey(key)) {
				continue;
			}
			if (keys.contains(key)) {
				continue;
			}
			keys.add(key);
			String values[] = req.getParameterValues(key);
			if (!ArrayUtils.isEmpty(values)) {
				for (String v : values) {
					if (pos++ > 0) {
						sb.append("&");
					}
					sb.append(key);
					sb.append("=");
					sb.append(v);
				}
			}
		}
		if ((parameter != null) && !parameter.isEmpty()) {
			for (String key : parameter.keySet()) {
				if (pos++ > 0) {
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				sb.append(parameter.get(key));
			}
		}
		if (sb.length() > 0) {
			sb.append("&");
		}
		sb.append("page=");
		StringBuffer uri = new StringBuffer();
		uri.append(requestUri);
		if (sb.length() > 1) {
			uri.append("?");
			uri.append(sb.toString());
		}

		// 去掉url当中出现多次/的情况
		String url = uri.toString().replaceAll("/{1,}", "/");
		// if(url.startsWith("/")){
		// url=url.substring(1);
		// }

		return url;

	}

	public static List<RequestKV> getParameterMap(HttpServletRequest req, Map<String, String> skipParam) {
		@SuppressWarnings("unchecked")
		Enumeration<String> it = req.getParameterNames();
		Set<String> keys = new HashSet<String>();
		List<RequestKV> list = new ArrayList<RequestKV>();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			if ((skipParam != null) && skipParam.containsKey(key)) {
				continue;
			}
			if (keys.contains(key)) {
				continue;
			}
			keys.add(key);
			String values[] = req.getParameterValues(key);
			if (!ArrayUtils.isEmpty(values)) {
				for (String v : values) {
					RequestKV kv = new RequestKV(key, v);
					list.add(kv);
				}
			}
		}
		return list;
	}

	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(String prefix) {
		Enumeration paramNames = HttpLocalThread.getRequest().getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = HttpLocalThread.getRequest().getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else if (StringUtils.isNotBlank(values[0])) {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 跳过分页的参数
	 *
	 * @param req
	 * @return
	 */
	public static String getUrl(HttpServletRequest req) {
		return getUrl(req, true, null);
	}

	/**
	 *
	 * @param requestURI
	 * @param skipPageParam
	 * @param param
	 * @return
	 */
	public static String getUrlByParam(String requestURI, boolean skipPageParam, Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		int pos = 0;
		for (String key : param.keySet()) {
			if (skipPageParam && ("page".equals(key) || "perPageRecord".equals(key))) {
				continue;
			}
			if (pos++ > 0) {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(param.get(key).toString());
		}
		if (sb.length() > 0) {
			sb.insert(0, "?");
		}
		sb.insert(0, requestURI);
		return sb.toString();
	}

	/**
	 * 按key,value传入参数生成query字符串,数量不为偶数时去掉最后一个
	 *
	 * @param req
	 * @param fields
	 *            需要在query当中出现的kv值
	 * @return
	 */
	public static String getUrlByKeyValue(HttpServletRequest req, String... fields) {
		if ((fields == null) || (fields.length == 0)) {
			return getUrl(req);
		}

		Map<String, String> param = new HashMap<>();
		for (int i = 0; i < fields.length; i += 2) {
			try {
				param.put(fields[i], fields[i + 1]);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
		return getUrl(req, true, param);
	}

	public static Integer getInt(String key) {
		String str = getParameter(key);
		if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
			return null;
		}
		return NumberUtils.toInt(str);
	}
}
