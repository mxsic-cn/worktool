package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 16:10:00
 */
@Data
public class QueryGeoFenceRequest extends V4Request {


    /**
     * 围栏id
     *
     * 数字
     *
     * 可选
     *
     * 无
     */
    private Long id;

    /**
     * 围栏全局id
     * 数字&字母&-
     *
     * 可选
     *
     * 无
     */
    private String gid;
    /**
     * 围栏名称
     *
     * 支持字母&数字&汉字，40个字符以内
     *
     * 可选
     *
     * 无
     */
    private String name;

    /**
     * 当前页码
     *
     * 数字
     *
     * 可选
     *
     * 1
     */
    private int page_no = 1;

    /**
     * 每页数量
     *
     * 数字
     *
     * 可选
     *
     * 20
     */
    private int page_size = 20;

    /**
     * 围栏激活状态
     *
     * 布尔类型
     *
     * 可选
     *
     * 无
     */
    private Boolean enable;

    /**
     * 按创建时间查询的起始时间
     *
     * yyyy-MM-dd HH:mm:ss
     *
     * 可选
     *
     * 无
     */
    private String start_time;

    /**
     * 按创建时间查询的结束时间
     *
     * yyyy-MM-dd HH:mm:ss
     *
     * 可选
     *
     * 无
     */
    private String end_time;


}
