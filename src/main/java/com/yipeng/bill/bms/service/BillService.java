package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.vo.LoginUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
public interface BillService {
    /**
     * 新增订单主表(相同价)
     * @param bill
     * @return
     */
    String saveSameBill(Map<String, String[]>  params,LoginUser user);

    /**
     * 不同价导入
     * @param params
     * @param user
     * @return
     */
     String savaDiffrentBill(Map<String, String[]>  params,LoginUser user);
    /**
     * 通过ID查询订单
     * @param id
     * @return
     */
    Bill getBillById(Long id);

    /**
     * 根据参数查询待审核订单列表
     * @param params
     * @return
     */
    Map<String, Object> pendingAuditList(Map<String,Object> params, LoginUser user);

    int updateBillPrice(Map<String, String[]>  params,LoginUser user);

    /**
     *渠道商录入价格
     * @param params
     * @param user
     * @return
     */
    int distributorPrice(Map<String, String[]>  params,User user);

    /**
     * g管理员录入价格
     * @param params
     * @param user
     * @return
     */
    int adminPrice(Map<String, String[]>  params,User user);

    /**
     *  订单列表
     * @param params
     * @return
     */
    Map<String, Object> getBillDetails(Map<String,Object> params, LoginUser user);

    /**
     * 优化调整（主订单状态）
     * @param params
     * @param user
     * @return
     */
    int OptimizationUpdate(Map<String, String[]>  params,LoginUser user);

    /**
     * 订单切换
     * @param params
     * @param user
     * @return
     */
    int billChangeCmt(Map<String, String[]>  params,LoginUser user);

    /**
     * 优化停止(主订单状态)
     * @param params
     * @param user
     * @return
     */
    int optimizationStop(Map<String, String[]>  params,LoginUser user);
    /**
     * 优化启动
     * @param params
     * @param user
     * @return
     */
    int optimizationStart(Map<String, String[]>  params,LoginUser user);

    int deleteBill(Map<String, String[]>  params,LoginUser user);

    Map<String,Object> getPriceDetails( int limit,int offset,String billId,LoginUser user,String way);

    Map<String,Object> billFeedback(String website);


    /**
     * 调整优帮云上线离线状态
     * @param params
     * @param user
     * @return
     */
    int updateYBYstate(Map<String, String[]>  params,LoginUser user);
    /**
     * 申请停单
     * @param params
     * @param user
     * @return
     */
    int applyStopBillConfirm(Map<String, String[]>  params,LoginUser user);

    /**
     * 申请停单审核
     * @param params
     * @param user
     * @return
     */
    int applyStopBillPass(Map<String, String[]>  params,LoginUser user);



    /**
     * 批量修改价格（云客订单）
     * @param bill
     * @param loginUser
     * @return
     */
    String uploadPrice(List<String[]> fileList,LoginUser loginUser);

    /**
     * 优化结算
     * @param
     * @return
     */
    Map<String,Object> billOptimizationSettlement(LoginUser user);

    Map<String,Object> userBalance(LoginUser user);//优化结算(用户余额)

    Map<String,Object> yearConsumption(LoginUser user);//优化结算(年度消费)
    Map<String,Object> lastMonthConsumption(LoginUser user);//优化结算(上月消费)

    Map<String,Object> billClientSideSettlementTable(LoginUser loginUser);//客户方结算页面table
}
