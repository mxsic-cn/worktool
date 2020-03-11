package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 16:10:00
 */
@Data
public class CreateGeoFenceRequest extends V4Request {

    /**
     * 围栏名称
     *
     * 字母&数字&汉字
     *
     * 必填
     *
     * 无
     */
    private String name;

    /**
     * 圆形围栏中心点
     *
     * longitude,latitude
     *
     * 圆形围栏必填与points互斥
     *
     * 无
     */
    private String center;

    /**
     * 圆形围栏半径
     *
     * 单位：米。范围0~5000。
     *
     * 圆形围栏必填与points互斥
     *
     * 无
     */
    private Double radius;

    /**
     * 多边形围栏坐标点
     *
     * lon1,lat1;lon2,lat2;lon3,lat3（3<=点个数<=5000）。多边形围栏外接圆半径最大为5000米。
     *
     * 多边形围栏必填
     *
     * 无
     */
    private String points;

    /**
     * 围栏监控状态
     *
     * 布尔类型
     *
     * 可选
     *
     * true
     */
    private String enable = Boolean.FALSE.toString();

    /**
     * 过期日期
     *
     * 围栏有效截止日期，过期后不对此围栏进行维护（围栏数据过期删除）；
     *
     *
     * 不能设定创建围栏时间点之前的日期；
     *
     * 格式yyyy-MM-dd； 请设置2055年之前的日期
     *
     * 可选
     * 创建时间后90天；
     */
    private String valid_time;

    /**
     * 一周内围栏监控日期的重复模式
     *
     * 星期缩写列表，用","或“;”隔开。
     *
     * 样例："Mon,Sat"
     *
     * 表示周一和周六有效。
     *
     * 星期简称如下（首字母大写）：
     * Mon,Tues,Wed,Thur,Fri,Sat,Sun
     *
     * repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系
     *
     * 可选，repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系
     *
     * 无
     */
    private String repeat = "Mon,Tues,Wed,Thur,Fri,Sat,Sun";
    /**
     * 指定日期列表
     *
     * 格式"date1;date2;date3"； date格式"yyyy-MM-dd"；
     *
     * /**    最大个数180，不能设定过去日期；
     *
     * repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系；
     *
     * 可选，repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系
     *
     * 无
     */
    private String fixed_date;


    /**
     * 一天内围栏监控时段
     *
     * 开始时间和结束时间定义一时间段，可设置多个时间段，时间段按照时间顺序排列，各时间段不可重叠；
     *
     * 拼接字符串格式如："startTime1,endTime1;startTime2,endTime2"；
     *
     * 最大个数24；
     *
     * 不可为空；
     *
     * 范围00:00-23:59；
     * 时间段不可重叠；
     *
     * 时间段长度>15min；
     *
     * 可选
     *
     * 默认为00:00,23:59；
     */
    private String time;

    /**
     * 围栏描述信息
     *
     *
     *
     * 可选
     *
     * 无
     */
    private String desc;

    /**
     * 配置触发围栏所需动作
     *
     * 触发动作分号分割
     *
     * enter;leave（进入、离开触发）
     *
     * 可选
     *
     * 无
     */
    private String alert_condition;


}
