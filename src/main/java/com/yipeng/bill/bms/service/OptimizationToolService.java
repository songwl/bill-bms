package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.ForbiddenWords;

import java.util.List;

/**
 * Created by 鱼在我这里。 on 2017/5/25.
 */
public interface OptimizationToolService {

    List<ForbiddenWords> forbiddenWordsList(String keywords);
}
