package cn.mxsic.seata.entity;

import lombok.Data;

/**
 * Function: Order <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-23 14:45:00
 */
@Data
public class Order {

    private String userId;
    private String commodityCode;
    private int count;
    private int money;
}
