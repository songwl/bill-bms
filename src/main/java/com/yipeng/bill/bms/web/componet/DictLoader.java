package com.yipeng.bill.bms.web.componet;

import com.yipeng.bill.bms.core.freemarker.IDictDataLoader;
import com.yipeng.bill.bms.core.model.LabelValue;
import com.yipeng.bill.bms.domain.Dict;
import com.yipeng.bill.bms.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by song on 16/8/24.
 */
public class DictLoader implements IDictDataLoader {

    @Autowired
    private DictService dictService;
    /**
     * 通过数据字典拿取数据
     *
     * @param keyCode
     * @return
     */
    @Override
    public List<LabelValue> loadDataList(String keyCode) {
        if(StringUtils.isEmpty(keyCode)){
            return Collections.emptyList();
        }
        List<Dict> list = getConfDictProps(keyCode);
        List<LabelValue> result = new ArrayList<>();
        for(Dict item:list){
            LabelValue lv = makeLabelValue(item);
            result.add(lv);
        }
        return result;
    }

    private LabelValue makeLabelValue(Dict item) {
        LabelValue lv = new LabelValue(item.getDictName(),item.getDictCode());
        return lv;
    }

    //先从数据库库当中加载，后面再改成缓存加载
    private List<Dict> getConfDictProps(String keyCode) {
        return dictService.queryDictListByDictGroupCode(keyCode);
    }

}
