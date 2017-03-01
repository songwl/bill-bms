/**
 * 
 */
package com.yipeng.bill.bms.core.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.yipeng.bill.bms.core.utils.ResourceUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * ftl配置构造器
 * @author yangbin
 *
 */
public class FreeMarkerConfiguration {

	private Configuration configuration;

	public FreeMarkerConfiguration(Class<?> clazz, String root) {
		init(clazz, root);
	}

	public FreeMarkerConfiguration(String clazzName, String root) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(clazzName);
		init(clazz, root);
	}

	private void init(Class<?> clazz, String root) {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setOutputEncoding("UTF-8");
		configuration.setNumberFormat("###");
		configuration.setClassicCompatible(true);
		try {
			configuration.setClassForTemplateLoading(clazz, root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getContent(String ftlName, Map<String, Object> rootMap) throws IOException, TemplateException {
		Template template = configuration.getTemplate(ftlName);
		StringWriter out = new StringWriter();
		template.process(rootMap, out);
		return out.toString();
	}

	public static String getRootPath() {
		return ResourceUtils.getRootPath("/WEB-INF/resources");
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public static void main(String[] args) {
		System.out.println(getRootPath());
		FreeMarkerConfiguration c = new FreeMarkerConfiguration(FreeMarkerConfiguration.class, "/ftl");
		try {
			System.out.println(c.getContent("header.ftl", null));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
