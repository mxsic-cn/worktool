package cn.mxsic.timer.forFun;

import java.util.List;

/**
 * Function: PayService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-08-25 16:18:00
 */
public interface PayServiceService {

    /**
     * 处理查询结果，业务逻辑
     * @param queryId
     * @param result
     */
    void queryResult(String queryId, Object result);

    /**
     * 获取所有需要查询的数据集合
     * @return
     */
    List<QueryTask> queryTask();
}
