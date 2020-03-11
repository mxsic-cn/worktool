package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 16:10:00
 */
@Data
public class CreateGeoFenceResponse extends V4Response {

    /**
     * 围栏全局id是一个地理
     * 围栏实体的全局id（gid），是围栏的唯一标识，可以用来查找该围栏实体本身的详细信息
     */
    private String gid;
    /**
     * 围栏id为预留字段，暂未使用 ，固定返回0。
     */
    private String id;
    /**
     * 状态说明信息
     */
    private String message;
    /**
     * 状态值
     */
    private int status;

    @Override
    public boolean isSucceed() {
        return 0 == status;
    }
}
