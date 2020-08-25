package cn.mxsic.timer.forFun;

/**
 * Function: QueryService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-08-25 16:18:00
 */
public interface QueryService {
    /**
     * 调用第三进行查询获取结果
     * @param queryId
     * @return
     */
    Object queryStatus(String queryId);


    /**
     * 过会再查，用于重试场景
     * @param task
     */
    void queryNextTime(QueryTask task);
}
