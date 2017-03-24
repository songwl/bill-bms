package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.FundAccount;
import com.yipeng.bill.bms.vo.CustomerListDetails;

import java.util.List;
import java.util.Map;

public interface FundAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundAccount record);

    int insertSelective(FundAccount record);

    FundAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundAccount record);

    int updateByPrimaryKey(FundAccount record);
    List<FundAccount>  selectByUserFund(Map<String,Object> params);
    int  selectByUserFundCount(Long userId);
    /**
     * 操作员对应的渠道商客户
     * @param userId
     * @return
     */
    List<FundAccount>  selectOperatorByUserFund(Map<String,Object> params);
    /**
     * 操作员对应的渠道商客户个数
     * @param userId
     * @return
     */
    int  selectOperatorByUserFundCount(Long userId);
}