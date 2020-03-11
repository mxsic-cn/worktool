package cn.mxsic.amap.entity.v3;

import lombok.Data;

/**
 * Function: DistanceRequest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-03-05 14:39:00
 */
@Data
public class DistanceRequest extends V3Request {


    /**
     * 出发点
     *
     * 支持100个坐标对，坐标对见用“| ”分隔；经度和纬度用","分隔
     *
     * 必填
     *
     * 无
     */
    private String origins;
    /**
     * 目的地
     *
     * 规则： lon，lat（经度，纬度）， “,”分割
     *
     * 如117.500244, 40.417801     经纬度小数点不超过6位
     *
     * 必填
     *
     * 无
     */
    private String destination;


    /**
     * 路径计算的方式和方法
     *
     * 0：直线距离
     *
     * 1：驾车导航距离（仅支持国内坐标）。
     *
     * 必须指出，当为1时会考虑路况，故在不同时间请求返回结果可能不同。
     *
     * 此策略和驾车路径规划接口的 strategy=4策略基本一致，策略为“ 躲避拥堵的路线，但是可能会存在绕路的情况，耗时可能较长 ”
     *
     * 若需要实现高德地图客户端效果，可以考虑使用驾车路径规划接口
     *
     * 3：步行规划距离（仅支持5km之间的距离）
     *
     * 可选
     *
     * 缺省值:1
     */
    private int type = 1;


}

