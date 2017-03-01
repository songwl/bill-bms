package com.yipeng.bill.bms.core.utils;

import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.ReflectionUtils;

import com.yipeng.bill.bms.core.model.Page;

/**
 * 比较对象之间属性是否变化，并以指定标签返回字符串
 * Created by song on 15/1/29.
 */
public class CompareBeanUtils<T> {

	private T oldObject;
	private T newObject;
	private Class<T> clazz;
	private Map<Class, PropertyEditor> propEditorMap = new HashMap<Class, PropertyEditor>();
	private boolean isNew = true;

	private StringBuffer content;

	/**
	 * 构造
	 *
	 * @param oldObject 老对象
	 * @param newObject 新的对象
	 */
	public CompareBeanUtils(Class<T> clazz, T oldObject, T newObject) {
		this(clazz);
		this.oldObject = oldObject;
		this.newObject = newObject;
		this.isNew = false;
	}

	/**
	 * @param newObject
	 */
	public CompareBeanUtils(Class<T> clazz, T newObject) {
		this(clazz);
		this.newObject = newObject;
	}

	protected CompareBeanUtils(Class<T> clazz) {
		super();
		content = new StringBuffer();
		this.clazz = clazz;
		register(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));//注册日期类型
	}

	public static <T> CompareBeanUtils<T> newInstance(T newObj) {
		if (null == newObj) {
			return null;
		}
		Class clazz = newObj.getClass();
		return new CompareBeanUtils<T>(clazz, newObj);
	}

	public static <T> CompareBeanUtils<T> newInstance(T oldObj, T newObj) {
		if (null == oldObj && null == newObj) {
			return null;
		}
		Class clazz;
		clazz = (newObj == null ? oldObj.getClass() : newObj.getClass());
		return new CompareBeanUtils<T>(clazz, oldObj, newObj);
	}

	public static void main(String[] args) {
		Page s = new Page();
		s.setItemList(null);
		s.setPageSize(20);
		s.setTotalCount(1000);
		s.setUrl("http://www.baidu.com");
		Page s2 = new Page();
		s2.setPageSize(3000);
		s2.setUrl("http://www.90sflea.com");
		s2.setItemList(new ArrayList());
		CompareBeanUtils<Page> ssss = CompareBeanUtils.newInstance(null, null);
		ssss.compare("url", "路径");
		ssss.compare("totalCount", "总数");
		ssss.compare("pageSize", "页面大小");
		ssss.compare("itemList", "ssss");
		System.out.print(ssss.toResult());
	}

	public <CC> void register(Class<CC> clazz, PropertyEditor pe) {
		propEditorMap.put(clazz, pe);
	}

	public void compare(String prop, String propLabel) {

		try {
			Field field = ReflectionUtils.findField(clazz, prop);
			Method m = null;
			Object newValue = null;
			if (field != null) {
				ReflectionUtils.makeAccessible(field);
				newValue = field.get(newObject == null ? oldObject : newObject);
			} else {
				char c = prop.charAt(0);
				m = clazz.getDeclaredMethod("get" + StringUtils.upperCase(String.valueOf(c)) + prop.substring(1));
				newValue = m.invoke(newObject == null ? oldObject : newObject);
			}
			if (isNew) {
				if (!isNullOrEmpty(newValue)) {
					comparedIsAdd(propLabel, newValue);
				}
			} else {
				if (null == oldObject) {
					return;
				}
				if (null == newObject) {
					comparedIsdel(propLabel);
					return;
				}
				Object oldValue = null;
				if (field != null) {
					oldValue = field.get(oldObject);
				} else {
					oldValue = m.invoke(oldObject);
				}
				if (notEquals(oldValue, newValue)) {
					comparedIsEdit(propLabel, oldValue, newValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean notEquals(Object oldValue, Object newValue) {
		String tmpOld, tmpNew;
		if (isNullOrEmpty(oldValue)) {
			tmpOld = "";
		} else {
			tmpOld = oldValue.toString();
		}
		if (isNullOrEmpty(newValue)) {
			tmpNew = "";
		} else {
			tmpNew = newValue.toString();
		}
		return !StringUtils.equals(tmpNew, tmpOld);
	}

	/*
	记录新值
	 */
	private void comparedIsAdd(String propLabel, Object newValue) {
		content.append("创建了 [");
		content.append(propLabel);
		content.append("],新值为\"");
		content.append(format(newValue));
		content.append("\";");
	}

	private void comparedIsdel(String propLabel) {
		content.append("删除了 [");
		content.append(propLabel);
		content.append("]");
		content.append(";");
	}

	/*
	 记录变更数据
	 */
	private void comparedIsEdit(String propLabel, Object oldValue, Object newValue) {

		content.append("修改了[");
		content.append(propLabel);
		content.append("],");
		content.append("旧值为\"");
		content.append(format(oldValue));
		content.append("\",新值为\"");
		content.append(format(newValue));
		content.append("\";");
	}

	/**
	 * 格式化数据类型
	 *
	 * @param obj
	 * @return
	 */
	private Object format(Object obj) {
		if (isNullOrEmpty(obj)) {
			return "";
		}
		Class clz = obj.getClass();
		if (propEditorMap.containsKey(clz)) {
			PropertyEditor pe = propEditorMap.get(clz);
			pe.setValue(obj);
			return pe.getAsText();
		} else {
			return obj;
		}
	}

	private boolean isNullOrEmpty(Object val) {
		if (val instanceof String) {
			return (org.apache.commons.lang3.StringUtils.isEmpty(String.class.cast(val)));
		} else {
			return val == null;
		}
	}

	//拿回结果
	public String toResult() {
		return content.toString();
	}
}
