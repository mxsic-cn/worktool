package cn.mxsic.seata.impl;

import cn.mxsic.seata.BusinessService;
import cn.mxsic.seata.OrderService;
import cn.mxsic.seata.StorageService;

/**
 * Function: BusinessServiceImpl <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-23 14:44:00
 */
public class BusinessServiceImpl implements BusinessService {
    private StorageService storageService;

    private OrderService orderService;

    @Override
    public void purchase(String userId, String commodityCode, int orderCount) {

        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }


}
