package com.yipeng.bill.bms.service;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2017/5/10.
 */

public interface RankingUpdateService {
    int updateRanking(int TaskId, int RankLast, int RankFirst);

    int updateKeywords(JsonObject json1);
}
