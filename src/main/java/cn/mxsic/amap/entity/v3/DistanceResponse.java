package cn.mxsic.amap.entity.v3;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Data;

/**
 * Function: DistanceRequest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-03-05 14:39:00
 */
@Data
public class DistanceResponse extends V3Response {

    /**
     * 距离信息列表
     */
    List<DistanceResult> results  = Lists.newArrayList();

}

