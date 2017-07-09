package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.DictMapper;
import com.yipeng.bill.bms.domain.Dict;
import com.yipeng.bill.bms.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by song on 16/8/24.
 */
@Service("dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> queryDictListByDictGroupCode(String dictGroupCode) {
        return dictMapper.selectByDictGroupCode(dictGroupCode);
    }

}
