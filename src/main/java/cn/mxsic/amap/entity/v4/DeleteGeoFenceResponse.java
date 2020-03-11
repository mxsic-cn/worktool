package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 16:10:00
 */
@Data
public class DeleteGeoFenceResponse extends V4Response {

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
