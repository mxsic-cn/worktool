package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: CheckGeoFenceRequest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-02-09 17:17:00
 */
@Data
public class CheckGeoFenceRequest extends V4Request {


    /**
     * 用户设备唯一标识符
     *
     * 设备唯一标识符，作为记录依据，不影响判断结果。Android设备一般使用imei号码，iOS一般使用idfv号，其余设备可根据业务自定义。
     *
     * 必填
     *
     * 无
     * 353777473369099  random for default server
     */
    private String diu = "353777473369099";
    /**
     * 设备在开发者自有系统中的id
     *
     *
     *
     * 可选
     *
     * 无
     */
    private String uid;
    /**
     * 设备位置坐标组
     *
     * 数据为坐标数据和坐标产生的时间戳数据，至少包含一个坐标对和时间戳。
     *
     * 1、传入1个点时，直接判断交互关系。
     *
     * 2、当传入多个点时，可以通过前后时间判断动态交互关系。
     *
     * 格式: x1,y1,unix_ts1;x2,y2,unix_ts2
     *
     * 动态交互判断方法：第一个点作为当前时间的点，然后从其余点中选出在当前点之前10s～1小时范围内的最早点，用这两个时间点的位置判断设备与围栏的动态交互关系。若数据无效交互关系默认返回leave。
     *
     * 必填
     *
     * 无
     */
    private String locations;

    /**
     * 签
     * 名
     *
     *
     *
     * 否
     *
     * 无
     */
    private String sig;

}
