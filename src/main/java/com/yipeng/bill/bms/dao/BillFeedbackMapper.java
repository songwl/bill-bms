package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillFeedback;

public interface BillFeedbackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillFeedback record);

    int insertSelective(BillFeedback record);

    BillFeedback selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillFeedback record);

    int updateByPrimaryKeyWithBLOBs(BillFeedback record);

    int updateByPrimaryKey(BillFeedback record);
}