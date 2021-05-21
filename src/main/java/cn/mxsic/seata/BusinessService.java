package cn.mxsic.seata;

/**
 * Function: BusinessService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-23 14:43:00
 */
public interface BusinessService {

    void purchase(String userId, String commodityCode, int orderCount);
}
