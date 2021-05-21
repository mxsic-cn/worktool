package cn.mxsic.seata;

import cn.mxsic.seata.entity.Order;

/**
 * Function: OrderService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-23 14:41:00
 */
public interface OrderService {
    /**
     * 创建订单
     */
    Order create(String userId, String commodityCode, int orderCount);
}
