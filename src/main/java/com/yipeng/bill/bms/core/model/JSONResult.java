package com.yipeng.bill.bms.core.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yipeng.bill.bms.core.spring.interceptor.HttpLocalThread;
import com.yipeng.bill.bms.core.utils.JSONResultUtils;

/**
 * Created by lancey on 15/1/27.
 */
public class JSONResult extends ResultHandle {

	private Map<String, Object> map;

	public JSONResult() {
		map = new HashMap<>();
	}

	public void put(String key, Object obj) {
		map.put(key, obj);
	}

	/**
	 * 输出json
	 * @param res
	 */
	public void output(HttpServletResponse res) {
		map.put("success", isSuccess());
		map.put("message", getMessage());
		try {
			JSONResultUtils.output(res, JSON.toJSONString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输入json字符串
	 */
	public void output() {
		output(HttpLocalThread.getResponse());
	}
}
