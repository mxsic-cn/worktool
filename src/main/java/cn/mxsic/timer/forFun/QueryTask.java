package cn.mxsic.timer.forFun;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Function: QueryTask <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-08-25 16:16:00
 */
@Data
public class QueryTask implements Serializable{
    private String queryId;
    private Date createTime;
    private Date nextQueryTime;


    /**
     * milliseconds to compare
     * @return
     */
    boolean shouldWait() {
        return nextQueryTime.getTime() > DateUtil.getNow().getTime();
    }

    /**
     * milliseconds to wait
     *
     * @return milliseconds
     */
    long waitTime() {
        if (shouldWait()) {
            return 0;
        } else {
            return nextQueryTime.getTime() - DateUtil.getNow().getTime();
        }
    }
}
