package cn.mxsic.amap.entity.v4;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Data;

/**
 * Function: CheckGeoFenceRequest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-02-09 17:17:00
 */
@Data
public class CheckGeoFenceResponse extends V4Response {

    /**
     * 返回状态码
     */
    private int status;
    /**
     * 消息
     */
    private String message;
    /**
     * 围栏事件列表
     */
    private List<FencingEvent> fencing_event_list = Lists.newArrayList();

    /**
     * 围栏距离设备的距离，没有命中围栏时返回。单位：米
     *
     * 当设备不在围栏中时，会告知最近的围栏以及用户与这个围栏的距离，距离没有限制。
     */
    private String nearest_fence_distance;


    /**
     * 最近的围栏id
     */
    private String nearest_fence_gid;

    @Override
    public boolean isSucceed() {
        return status == 0;
    }
}
