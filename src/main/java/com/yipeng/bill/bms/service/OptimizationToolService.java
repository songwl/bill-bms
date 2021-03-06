package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.ForbiddenWords;
import com.yipeng.bill.bms.domain.KeywordsPrice;
import com.yipeng.bill.bms.model.KeywordToPrice;
import com.yipeng.bill.bms.vo.LoginUser;

import java.util.List;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/5/25.
 */
public interface OptimizationToolService {

    List<KeywordsPrice> forbiddenWordsList(String keywords, double rote);

    Boolean LoopAllKeywords();

    Boolean UpdateRote(LoginUser loginUser, double rote);

    List<KeywordToPrice> GetPriceList(List<String> list, double rote);

    String UpdateToken(LoginUser loginUser);

    Boolean setOffer(int type, String keywordNum, double rote, String dataUser);

    Boolean setWebsitePower(Map<String, Object> map);

    Boolean setAgentWebsitePower(Map<String, Object> map);

    Map<String, Object> GetKeywordsList(Map<String, Object> map);

    int UpToTotal(long dataUser);
}
