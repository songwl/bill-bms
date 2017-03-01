package com.yipeng.bill.bms.core.freemarker;

import com.yipeng.bill.bms.core.model.LabelValue;

/**
 * Created by lancey on 15/7/27.
 */
public interface IDictDataSpecLoader extends IDictDataLoader {

	/**
	 * 提取单个值
	 * @param keyCode
	 * @param propCode
	 * @return
	 */
	LabelValue loadData(final String keyCode, final String propCode);

}
