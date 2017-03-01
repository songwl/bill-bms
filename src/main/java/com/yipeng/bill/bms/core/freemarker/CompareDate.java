package com.yipeng.bill.bms.core.freemarker;

import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yipeng.bill.bms.core.utils.DateUtils;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * Created by lancey on 15/3/14.
 */
public class CompareDate implements TemplateMethodModel {

	private Date date;

	@Override
	public Object exec(List list) throws TemplateModelException {
		if (CollectionUtils.isEmpty(list)) {
			throw new TemplateModelException("param is empty");
		}

		String specDate = (String) list.get(0);
		Date dt = DateUtils.parseDate(specDate);
		if (specDate == null) {
			throw new TemplateModelException("date type error");
		}

		return new SimpleNumber(dt.compareTo(date));
	}

	public CompareDate(Date date) {
		this.date = date;
	}
}
