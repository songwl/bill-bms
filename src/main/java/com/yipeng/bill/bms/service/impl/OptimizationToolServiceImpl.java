package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.ForbiddenWordsMapper;
import com.yipeng.bill.bms.domain.ForbiddenWords;
import com.yipeng.bill.bms.service.OptimizationToolService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/5/25.
 */
@Service
public class OptimizationToolServiceImpl implements OptimizationToolService {

    @Autowired
    private ForbiddenWordsMapper forbiddenWordsMapper;
    @Override
    public List<ForbiddenWords> forbiddenWordsList(String keywords) {
       String[]  arr=keywords.split("\n");
       String where="";
       for (int i=0;i<arr.length;i++)
       {
           String aa=arr[i];
           where+=" or words LIKE '"+arr[i]+"'";
       }
        Map<String,Object> params =new HashMap<>();
       params.put("words",where);
        List<ForbiddenWords> forbiddenWordsList=  forbiddenWordsMapper.selectByWords(params);

        return null;
    }
}
