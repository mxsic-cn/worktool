package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 16:10:00
 */
@Data
public class DeleteGeoFenceRequest extends V4Request {

    /**
     * gid：围栏全局id，必填。创建或查询围栏后高德返回的结果。
     *
     * 必填
     *
     * 无
     */
    private String gid;


}
