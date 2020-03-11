package cn.mxsic.amap.entity.v3;

import com.google.common.collect.Maps;

import java.util.Map;

import lombok.Data;

/**
 * Function: GeoCodeResponse <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 10:51:00
 */
@Data
public class GeoCodeRequest extends V3Request {

    /**
     * 结构化地址信息
     *
     * 规则遵循：国家、省份、城市、区县、城镇、乡村、街道、门牌号码、屋邨、大厦，如：北京市朝阳区阜通东大街6号。如果需要解析
     * 多个地址的话，请用"|"进行间隔，并且将 batch 参数设置为 true，最多支持 10 个地址进进行"|"分割形式的请求。
     *
     * 必填
     *
     * 无
     */
    private String address;

    /**
     * 指定查询的城市
     *
     * 可选输入内容包括：指定城市的中文（如北京）、指定城市的中文全拼（beijing）、citycode（010）、adcode（110000），
     * 不支持县级市。当指定城市查询内容为空时，会进行全国范围内的地址转换检索。
     *
     * adcode信息可参考城市编码表获取
     *
     * 可选
     *
     * 无，会进行全国范围内搜索
     */
    private String city;

    public Map<String, String> getParameter() {
        Map<String, String> param = Maps.newHashMap();
        if (this.address != null) {
            param.put("address", this.address);
        }
        if (this.city != null) {
            param.put("city", this.city);
        }
        return param;
    }
}
