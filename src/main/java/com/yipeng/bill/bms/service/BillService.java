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
    String saveSameBill(Map<String, String[]>  params,User user);

    /**
     * 不同价导入
     * @param params
     * @param user
     * @return
     */
     String savaDiffrentBill(Map<String, String[]>  params,User user);
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

    int updateBillPrice(Map<String, String[]>  params,User user);

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
     * 优化调整
     * @param params
     * @param user
     * @return
     */
    int OptimizationUpdate(Map<String, String[]>  params,LoginUser user);

}
