package cn.mxsic.amap.entity.v4;

import java.util.Date;

import lombok.Data;

/**
 * Function: FencingEvent <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-02-09 17:20:00
 */
@Data
public class FencingEvent {


    /**
     * 设备行为。对应3种与围栏的动态交互关系，进/出/停留： enter|leave|stay
     */
    private String client_action;


    /**
     * 设备状态。当前与围栏的静态关系状态。是否在围栏里： in|out
     */
    private String client_status;


    /**
     * 用户进入围栏时间。格式： yyyy-MM-dd HH:mm:ss
     */
    private Date enter_time;


    /**
     * 围栏信息
     */
    private FenceInfo fence_info;


    /**
     * in the fence
     * @return
     */
    public boolean inTheFence() {
        return "in".equalsIgnoreCase(client_status);
    }

    /**
     * out the fence
     * @return
     */
    public boolean outTheFence() {
        return "out".equalsIgnoreCase(client_status);
    }
}
