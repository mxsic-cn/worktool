package cn.mxsic.seata;

/**
 * Function: StorageService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-23 14:41:00
 */
public interface StorageService {
    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
