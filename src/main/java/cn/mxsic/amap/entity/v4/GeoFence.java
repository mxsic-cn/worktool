package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-02-09 15:42:00
 */
@Data
public class GeoFence {


    /**
     * 围栏所在地区adcode
     */
    private String adcode;

    /**
     * 围栏监控触发条件
     */
    private String alert_condition;

    /**
     * 圆形围栏中心点
     */
    private String center;

    /**
     * 围栏创建时间
     */
    private String create_time;

    /**
     * 围栏激活状态
     */
    private String enable;

    /**
     * 指定日期
     */
    private String fixed_date;

    /**
     * 围栏全局id
     */
    private String gid;

    /**
     * 围栏id
     */
    private String id;

    /**
     * 开发者key
     */
    private String key;

    /**
     * 围栏名称
     */
    private String name;

    /**
     * 多边形围栏点
     */
    private String points;

    /**
     * 圆形围栏半径
     */
    private String radius;

    /**
     * 一周内围栏监控的重复星期
     */
    private String repeat;

    /**
     * 一天内监控的具体时段
     */
    private String time;

    /**
     * 过期日期
     * }
     */
    private String valid_time;
}
