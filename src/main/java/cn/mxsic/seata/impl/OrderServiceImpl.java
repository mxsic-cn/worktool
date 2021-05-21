package cn.mxsic.seata.impl;

import cn.mxsic.seata.AccountService;
import cn.mxsic.seata.OrderService;
import cn.mxsic.seata.entity.Order;

/**
 * Function: OrderServiceImpl <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-23 14:45:00
 */
public class OrderServiceImpl implements OrderService {

//    private OrderDAO orderDAO;

    private AccountService accountService;

    @Override
    public Order create(String userId, String commodityCode, int orderCount) {

        int orderMoney = 0;//calculate(commodityCode, orderCount);

        accountService.debit(userId, orderMoney);

        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);

        // INSERT INTO orders ...
//        return orderDAO.insert(order);
        return null;
    }
}
