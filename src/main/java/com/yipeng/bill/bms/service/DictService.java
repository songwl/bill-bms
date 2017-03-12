package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.Dict;

import java.util.List;

/**
 * Created by song on 16/8/24.
 */
public interface DictService {

    List<Dict> queryDictListByDictGroupCode(String dictGroupCode);

}
