package cn.mxsic.amap.entity.v3;

import lombok.Data;

/**
 * Function: DistanceResult <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-03-05 14:49:00
 */
@Data
public class DistanceResult {


    /**
     * 起点坐标，起点坐标序列号（从１开始）
     */
    private String origin_id;
    /**
     * 终点坐标，终点坐标序列号（从１开始）
     */
    private String dest_id;
    /**
     * 路径距离，单位：米
     */
    private String distance;
    /**
     * 预计行驶时间，单位：秒
     */
    private String duration;
    /**
     * 仅在出错的时候显示该字段。大部分显示“未知错误”
     *
     * 由于此接口支持批量请求，建议不论批量与否用此字段判断请求是否成功
     */
    private String info;

    /**
     * 仅在出错的时候显示此字段。
     *
     * 在驾车模式下：
     *
     *
     * 1，指定地点之间没有可以行车的道路
     *
     * 2，起点/终点 距离所有道路均距离过远（例如在海洋/矿业）
     *
     * 3，起点/终点不在中国境内
     */
    private String code;

}
