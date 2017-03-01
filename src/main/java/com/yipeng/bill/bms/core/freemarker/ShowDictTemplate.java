package com.yipeng.bill.bms.core.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yipeng.bill.bms.core.model.LabelValue;
import com.yipeng.bill.bms.core.utils.SelectUtils;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * //接收前台传入的参数,向前端输出dictList的参数
 * Created by lancey on 15/3/6.
 */
public class ShowDictTemplate implements TemplateDirectiveModel {

	private Map<String, Class> mappingClazz;

	private static final Logger LOGGER = LoggerFactory.getLogger(ShowDictTemplate.class);

	/**
	 * 字典加载器
	 */
	private Map<String, IDictDataLoader> dataLoaderMap;

	@Override
	public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
		String dictType = MapUtils.getString(map, "dictType");
		String dictKey = MapUtils.getString(map, "dictKey");
		String selectValue = MapUtils.getString(map, "selectValue");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("showDictTemplate, dictType:{},dictKey:{}", dictType, dictKey);
		}
		if (StringUtils.equals(dictType, "EMNU")) {//枚举值
			String[] dictKeys = StringUtils.split(dictKey, "\\.");
			if (dictKeys == null || dictKeys.length != 2) {
				LOGGER.error("SHOW DICT dictKey format error.key:{}", dictKey);
				return;
			}
			Class clazz = mappingClazz.get(dictKeys[0]);
			String menuClazzName = clazz.getName() + "." + dictKeys[1];
			try {
				Class menuClazz = ClassUtils.getClass(menuClazzName);
				List<LabelValue> list = SelectUtils.makeList(menuClazz);
				if (StringUtils.isNotEmpty(selectValue)) {
					list = filterShowSelectValue(list, selectValue);
				} else {
					list = filterNoShowList(list);
				}
				DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_19);
				environment.setVariable("dictList", builder.build().wrap(list));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			if (dataLoaderMap.get(dictType) != null) {
				List<LabelValue> list = dataLoaderMap.get(dictType).loadDataList(dictKey);
				if (StringUtils.isNotEmpty(selectValue)) {
					list = filterShowSelectValue(list, selectValue);
				}
				DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_19);
				environment.setVariable("dictList", builder.build().wrap(list));
			}
		}
		if (templateDirectiveBody != null) {
			templateDirectiveBody.render(environment.getOut());
		}
	}

	private List<LabelValue> filterNoShowList(List<LabelValue> list) {
		List<LabelValue> result = new ArrayList<>();
		for (LabelValue lv : list) {
			if (lv.isListFlag()) {
				result.add(lv);
			}
		}
		return result;
	}

	private List<LabelValue> filterShowSelectValue(List<LabelValue> list, String value) {
		List<LabelValue> result = new ArrayList<>();
		for (LabelValue lv : list) {
			if (StringUtils.equalsIgnoreCase(value, lv.getValue())) {
				result.add(lv);
			}
		}
		return result;
	}

	//    public Map<String, Class> getMappingClazz() {
	//        return mappingClazz;
	//    }

	public void setDataLoaderMap(Map<String, IDictDataLoader> dataLoaderMap) {
		this.dataLoaderMap = dataLoaderMap;
	}

	public void setMappingClazz(Map<String, Class> mappingClazz) {
		this.mappingClazz = mappingClazz;
	}
}
