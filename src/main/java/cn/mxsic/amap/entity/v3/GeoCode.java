package cn.mxsic.amap.entity.v3;

/**
 * Function: GeoCode <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 15:24:00
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.mxsic.amap.Coordinate;
import lombok.Data;

/**
 * Function: GeoCode <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 10:50:00
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCode {

    /**
     * 结构化地址信息
     * 省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
     */
    private String formatted_address;

    /**
     * 国家
     * 国内地址默认返回中国
     */
    private String country;

    /**
     * 地址所在的省份名
     * 例如：北京市。此处需要注意的是，中国的四大直辖市也算作省级单位。
     */
    private String province;

    /**
     * 地址所在的城市名
     * 例如：北京市
     */
    private String city;

    /**
     * 城市编码
     * 例如：010
     */
    private String citycode;

    /**
     * 地址所在的区
     * 例如：朝阳区
     */
    private String district;

    /**
     * 区域编码
     * 例如：110101
     */
    private String adcode;

    /**
     * 坐标点
     * 经度，纬度
     */
    private String location;

    /**
     * 匹配级别
     * 参见下方的地理编码匹配级别列表
     */
    private String level;

    /**
     * 地理坐标
     */
    public Coordinate getCoordinate() {
        return new Coordinate(location);
    }
}