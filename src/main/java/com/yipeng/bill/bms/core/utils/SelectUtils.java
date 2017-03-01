package com.yipeng.bill.bms.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.util.ReflectionUtils;

import com.yipeng.bill.bms.core.model.LabelValue;

/**
 * 初始化下拉数据列表，
 * Created by lancey on 15/1/9.
 */
public abstract class SelectUtils {

	private static ConcurrentHashMap<String, List<LabelValue>> cacheLabelValue = new ConcurrentHashMap<String, List<LabelValue>>();

	/**
	 * 通过枚举值当中提取下拉菜单列表
	 * @param clazz
	 * @return
	 */
	public static List<LabelValue> makeList(Class<? extends Enum> clazz) {
		if (cacheLabelValue.containsKey(clazz.getName())) {
			return cacheLabelValue.get(clazz.getName());
		} else {
			List<LabelValue> list = makeList2(clazz);
			if (!list.isEmpty()) {
				cacheLabelValue.put(clazz.getName(), list);
			}
			return list;
		}
	}

	public static List<LabelValue> makeListBlank(Class<? extends Enum> clazz) {
		ArrayList<LabelValue> list = (ArrayList<LabelValue>) makeList(clazz);
		List<LabelValue> result = SerializationUtils.clone(list);
		result.add(0, new LabelValue("请选择", ""));
		return result;
	}

	private static List<LabelValue> makeList2(Class<? extends Enum> clazz) {
		List<LabelValue> list = new ArrayList<LabelValue>();
		Method method = ReflectionUtils.findMethod(clazz, "values");
		try {
			Object[] array = (Object[]) method.invoke(null);
			for (Object obj : array) {
				Method m = ReflectionUtils.findMethod(clazz, "getLabel");
				String cnName = (String) m.invoke(obj);
				m = ReflectionUtils.findMethod(clazz, "getValue");
				String name = (String) m.invoke(obj);
				LabelValue lv = new LabelValue(cnName, name);

				m = ReflectionUtils.findMethod(clazz, "isSelectList");
				if (m != null) {
					boolean flag = (boolean) m.invoke(obj);
					lv.setListFlag(flag);
				}
				list.add(lv);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}

}
