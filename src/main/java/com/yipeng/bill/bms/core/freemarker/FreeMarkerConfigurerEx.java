package com.yipeng.bill.bms.core.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.yipeng.bill.bms.core.model.Constant;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

/**
 * 扩展ftl属性
 * 从const.properties当中输出变量到页面
 * Created by lancey on 15/7/31.
 */
public class FreeMarkerConfigurerEx extends FreeMarkerConfigurer {

	private Map<String, Object> variables;

	@Override
	public void setFreemarkerVariables(Map<String, Object> variables) {
		//super.setFreemarkerVariables(variables);
		this.variables = variables;

	}

	@Override
	protected void postProcessConfiguration(Configuration config) throws IOException, TemplateException {
		super.postProcessConfiguration(config);
		Set<String> keys = Constant.getInstance().getPropertyNames();
		boolean flag = false;
		for (String key : keys) {
			if (key.startsWith("freemarker_")) {
				flag = true;
				if (variables == null) {
					variables = new HashMap<>();
				}
				String newKey = key.replace("freemarker_", "");
				variables.put(newKey, Constant.getInstance().getProperty(key));
			}
		}
		loadCommFreeMaker();

		if (variables != null) {
			config.setAllSharedVariables(new SimpleHash(variables, config.getObjectWrapper()));
		}
	}

	private void loadCommFreeMaker() {
		//FreeMarkerConfiguration config = new FreeMarkerConfiguration(FreeMarkerConfiguration.class, "/template");

		try {
			//variables.put("comm_template_header", config.getContent("header.ftl", variables));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final Map<String, Object> getVariables() {
		return variables;
	}
}
