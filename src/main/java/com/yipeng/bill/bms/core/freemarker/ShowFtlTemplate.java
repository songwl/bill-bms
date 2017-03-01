package com.yipeng.bill.bms.core.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yipeng.bill.bms.core.spring.interceptor.HttpLocalThread;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ShowFtlTemplate implements TemplateDirectiveModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShowFtlTemplate.class);

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		try {
			String type = MapUtils.getString(params, "type");
			String localName = HttpLocalThread.getRequest().getServerName();
			DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_19);
			LOGGER.info("localName:" + localName + ",type:" + type);
			if (localName != null && localName.endsWith("yueguo.com")) {
				if (StringUtils.equals(type, "header")) {
					env.setVariable("headerFtl", builder.build().wrap(env.getVariable("comm_template_yueGuoHeader")));
				}
				if (StringUtils.equals(type, "footer")) {
					env.setVariable("footerFtl", builder.build().wrap(env.getVariable("comm_template_yueGuoFooter")));
				}
			} else {
				if (StringUtils.equals(type, "header")) {
					env.setVariable("headerFtl", builder.build().wrap(env.getVariable("comm_template_header")));
				}
				if (StringUtils.equals(type, "footer")) {
					env.setVariable("footerFtl", builder.build().wrap(env.getVariable("comm_template_footer")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (body != null) {
			body.render(env.getOut());
		}
	}
}
