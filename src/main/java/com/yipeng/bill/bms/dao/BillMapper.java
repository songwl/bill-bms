package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.Logs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BillMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Bill record);

    int insertSelective(Bill record);

    Bill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);

    List<Bill> selectList(Map<String, Object> params);

    List<Bill> selectAll(Map<String, Object> params);

    Long selectAllCount(int state);

    List<Bill> selectAgentBill(Map<String, Object> params);

    Long selectAgentBillCount(Map<String, Object> params);

    List<Bill> selectAllSelective(Map<String, Object> params);

    List<Bill> selectBill(Map<String, Object> params);

    /**
     * 通过收款方来查询对应的订单
     *
     * @param params
     * @return
     */
    List<Bill> selectByInMemberId(Map<String, Object> params);

    Long selectByInMemberIdCount(Map<String, Object> params);

    /**
     * 通过付款方来查询对应的订单
     *
     * @param params
     * @return
     */
    List<Bill> selectByOutMemberId(Map<String, Object> params);

    Long selectByOutMemberIdCount(Map<String, Object> params);

    /**
     * 根据角色来获取订单(渠道商直属客户)
     *
     * @param params
     * @return
     */
    List<Bill> selectByCustomerId(Map<String, Object> params);

    Long selectByCustomerIdCount(Map<String, Object> params);

    /**
     * 除客户之外其他角色的订单数
     *
     * @param params
     * @return
     */
    Long getBillListCount(Map<String, Object> params);

    /**
     * 客户的订单数
     *
     * @param params
     * @return
     */
    Long getBillListByCmmCount(Map<String, Object> params);

    /**
     * 客户的订单
     *
     * @param params
     * @return
     */
    List<Bill> getBillCountByCmm(Map<String, Object> params);

    /**
     * 按网址分组
     *
     * @param params
     * @return
     */
    List<Bill> getBillGroupByWebsite(Map<String, Object> params);

    List<Bill> getBillGroupByWebsiteTwo(Map<String, Object> params);

    /**
     * 本月新增订单数
     *
     * @param params
     * @return
     */
    int getBillMonthAdd(Map<String, Object> params);

    /**
     * 按网址分组
     *
     * @param params
     * @return
     */
    List<Bill> selectBillGroupByWebsite(Map<String, Object> params);

    List<Bill> selectByNewRanking(Map<String, Object> params);

    Long selectByNewRankingCount(Map<String, Object> params);

    //审核订单预览
    List<Bill> selectByBillAudit(Map<String, Object> params);

    //审核订单预览的个数
    Long selectByBillAuditCount(Map<String, Object> params);

    List<Bill> selectByBillByUpdateStandardDays(Map<String, Object> params);

    //达标数量
    int selectBillDabiaoCount(Map<String, Object> params);

    //通过TASKID查询
    List<Bill> selectByBillIdToGetTaskId(int taskId);

    //审核客户提交订单页面
    List<Bill> selectByPendingAuditView1List(Map<String, Object> params);

    Long selectByPendingAuditView1ListCount(Map<String, Object> params);

    //审核客户提交订单页面
    List<Bill> selectByAuditViewKeHuList(Map<String, Object> params);

    Long selectByAuditViewKeHuListCount(Map<String, Object> params);


    List<String> selectAllKeywords();

    //下滑排名
    List<Map<String, Object>> selectDeclineBillTable(Map<String, Object> params);

    Long selectDeclineBillTableCount(Map<String, Object> params);

    //更新排名
    int updateByWebAppId(Bill record);

    List<Map<String, Object>> selectBybillStandardCount(Map<String, Object> params);

    List<Map<String, Object>> selectBybillCount();

    List<Map<String, Object>> selectByWebsite();

    List<Map<String, Object>> selectByDayCost(Map<String, Object> params);

    List<Map<String, Object>> selectByallCost();

    List<Map<String, Object>> selectByWeekCount();

    List<Map<String, Object>> selectByMonthCount();

    List<Map<String, Object>> selectByWebsiteNoCost(List<String> arr);

    List<Long> selectByLeasePower(Map<String, Object> map);

    List<Long> selectAllByLeasePower();

    int selectByLeasePowerCount();

    List<Integer> selectKeywordNumByAscription(Map<String, Object> map);

    List<Map<String, Object>> selectKeywordNumByAscriptionRanking(Map<String, Object> map);

    int selectAllKeywordNumByAscription(Map<String, Object> map);

    List<Map<String,Object>> selectByGroupBillCount(Map<String,Object> sqlMap);
    List<Map<String,Object>> selectByGroupBillDabiaoCount(Map<String,Object> sqlMap);
}