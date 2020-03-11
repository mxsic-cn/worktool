package cn.mxsic.amap.entity.v4;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-02-09 15:42:00
 */
@Data
public class FenceInfo {

    /**
     * 全局围栏id
     */
    private String fence_gid;

    /**
     * local
     */
    private String fence_center;
    /**
     * 围栏名称
     */
    private String fence_name;
}
